package com.mostafa1075.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mostafa1075.bakingapp.pojo.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);

        TextView textView = (TextView) findViewById(R.id.test);
        textView.setText(recipe.getName());
    }
}
