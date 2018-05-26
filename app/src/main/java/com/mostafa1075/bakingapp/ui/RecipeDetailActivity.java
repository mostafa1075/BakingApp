package com.mostafa1075.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mostafa1075.bakingapp.R;
import com.mostafa1075.bakingapp.adapter.StepsAdapter;
import com.mostafa1075.bakingapp.pojo.Recipe;
import com.mostafa1075.bakingapp.pojo.Step;
import com.mostafa1075.bakingapp.ui.fragment.RecipeDetailFragment;
import com.mostafa1075.bakingapp.ui.fragment.StepDetailFragment;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements StepsAdapter.onStepClickListener {

    public static final String RECIPE_KEY = "recipe";

    public Recipe mRecipe;
    // Used to differentiate between phone and tablet layouts
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mTwoPane = findViewById(R.id.recipe_detail_layout) != null;

        mRecipe = getIntent().getParcelableExtra(RECIPE_KEY);

        setTitle(mRecipe.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailFragment.RECIPE_ARGUMENT, mRecipe);

        detailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_fragment, detailFragment)
                .commit();
    }

    // Show the step details based on the layout used
    @Override
    public void onStepClicked(int position) {

        StepDetailFragment.deleteSavedPlaybackState(this);

        // For tablet layout replace the step fragment with the clicked step
        if (mTwoPane) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(StepDetailFragment.STEP_ARGUMENT, mRecipe.getSteps().get(position));
            stepDetailFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        }
        // For the phone layout launch StepDetailActivity to show the step details
        else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putParcelableArrayListExtra(StepDetailActivity.STEPS_KEY, (ArrayList<Step>) mRecipe.getSteps());
            intent.putExtra(StepDetailActivity.STEP_ID_KEY, position);
            startActivity(intent);
        }
    }
}
