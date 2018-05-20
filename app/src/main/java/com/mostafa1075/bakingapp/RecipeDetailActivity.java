package com.mostafa1075.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mostafa1075.bakingapp.pojo.Recipe;
import com.mostafa1075.bakingapp.pojo.Step;

public class RecipeDetailActivity extends AppCompatActivity implements StepsAdapter.onStepClickListener  {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipe = getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE_KEY, mRecipe);

        detailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add( R.id.recipe_detail_fragment, detailFragment)
                .commit();

    }

    @Override
    public void onStepSelected(int position) {
        Step step =  mRecipe.getSteps().get(position);
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtra(StepDetailActivity.STEP_KEY, step);
        startActivity(intent);
    }
}
