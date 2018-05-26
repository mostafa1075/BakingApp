package com.mostafa1075.bakingapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.mostafa1075.bakingapp.R;
import com.mostafa1075.bakingapp.adapter.RecipesAdapter;
import com.mostafa1075.bakingapp.pojo.Recipe;
import com.mostafa1075.bakingapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapaterOnClickHandler {

    // Used to calculate span count to fit bigger layouts
    private static final int RECYCLERVIEW_ITEM_MIN_WIDTH = 600;

    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private GridLayoutManager mLayoutManager;
    private Call<List<Recipe>> mRecipeResponse;
    // Used for halting the test until the Recipes is loaded using Retrofit
    @Nullable
    private CountingIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipes_recyclerview);
        // DividerItemDecoration adapted from: https://stackoverflow.com/a/24872169
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setHasFixedSize(true);

        mRecipesAdapter = new RecipesAdapter(this, this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mLayoutManager = new GridLayoutManager(this, getSpanCount());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecipeResponse != null)
            mRecipeResponse.cancel();
    }

    private void loadData() {
        getIdlingResource().increment();
        mRecipeResponse = RetrofitClient.getInstance().getRecipes();
        mRecipeResponse.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipesAdapter.swapRecipes(response.body());
                getIdlingResource().decrement();
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d(this.getClass().getSimpleName(), "onFailure: " + t.getMessage());
                showErrorMessage();
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }

    private void showErrorMessage() {
        findViewById(R.id.error_message).setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    /**
     * Returns the amount of items that can fit horizontally in the RecyclerView
     * Used for Automatic spanning of items within the RecyclerView
     * This method is adapted from this answer: https://stackoverflow.com/a/28077579
     */
    private int getSpanCount() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        return Math.round(outMetrics.widthPixels / RECYCLERVIEW_ITEM_MIN_WIDTH);
    }

    public CountingIdlingResource getIdlingResource() {
        if (mIdlingResource == null)
            mIdlingResource = new CountingIdlingResource(this.getClass().getName());
        return mIdlingResource;
    }
}
