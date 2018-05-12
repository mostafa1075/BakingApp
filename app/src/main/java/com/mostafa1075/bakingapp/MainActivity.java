package com.mostafa1075.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mostafa1075.bakingapp.pojo.Recipe;
import com.mostafa1075.bakingapp.retrofit.RetrofitClient;
import com.mostafa1075.bakingapp.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdpaterOnClickHandler{

    public static final String RECIPE_KEY = "recipe";

    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private GridLayoutManager mLayoutManager;

    private Call<List<Recipe>> mRecipeResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipes_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mRecipesAdapter = new RecipesAdapter(this, this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        mLayoutManager = new GridLayoutManager(this, 1);
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
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }
}
