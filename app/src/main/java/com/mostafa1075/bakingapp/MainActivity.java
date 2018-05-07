package com.mostafa1075.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private Call<List<Recipe>> mRecipeResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.test);

        mRecipeResponse = RetrofitClient.getInstance().getRecipes();
        mRecipeResponse.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                String s = "";
                for (Recipe recipe:
                     response.body()) {
                    s += recipe.getImage();
                }
                textView.setText(s);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("FAILLLLLLLLLLLLLLL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
