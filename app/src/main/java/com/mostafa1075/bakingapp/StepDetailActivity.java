package com.mostafa1075.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mostafa1075.bakingapp.pojo.Step;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEP_KEY  = "step_key";

    private Step mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mStep = getIntent().getParcelableExtra(STEP_KEY);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(StepDetailFragment.STEP_ARGUMENT, mStep);

        stepDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.step_detail_fragment, stepDetailFragment)
                .commit();


    }



}
