package com.mostafa1075.bakingapp.ui;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdpaterOnClickHandler{

    //DONE: Create Tablet layout
    //DONE: Add next and previous in recipe details
    //KINDA: Polish layout and ui elements
    //TODO: Fix Exoplayer lag
    //DONE: Add a button for ingredients widget
    //NEARLY DONE: Handle unexpected JSON Data
    //TODO: Comment new code
    //TODO: Add Espresso testing

    public static final String RECIPE_KEY = "recipe";
    private static final int RECYCLERVIEW_ITEM_MIN_WIDTH = 500;

    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private GridLayoutManager mLayoutManager;

    private Call<List<Recipe>> mRecipeResponse;

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

    private void loadData(){
        mRecipeResponse = RetrofitClient.getInstance().getRecipes();
        mRecipeResponse.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipesAdapter.swapRecipes(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("FAILLLLLLLLLLLLLLL", "onFailure: " + t.getMessage());
                showErrorMessage();
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }

    void showErrorMessage(){
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
}
