package com.mostafa1075.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mostafa1075.bakingapp.R;
import com.mostafa1075.bakingapp.pojo.Step;
import com.mostafa1075.bakingapp.ui.fragment.StepDetailFragment;

import java.util.List;

public class StepDetailActivity extends AppCompatActivity {

    public static final String STEPS_KEY = "steps_key";
    public static final String STEP_ID_KEY = "step_id";

    private List<Step> mSteps;
    private int mCurrStepId;
    private ImageButton mNextBtn;
    private ImageButton mPrevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mNextBtn = findViewById(R.id.next_step_btn);
        mPrevBtn = findViewById(R.id.prev_step_btn);

        mSteps = getIntent().getParcelableArrayListExtra(STEPS_KEY);

        if (savedInstanceState == null)
            mCurrStepId = getIntent().getIntExtra(STEP_ID_KEY, 0);
        else
            mCurrStepId = savedInstanceState.getInt(STEPS_KEY);

        setTitle("Step " + mSteps.get(mCurrStepId).getId());

        if (mCurrStepId == 0)
            mPrevBtn.setVisibility(View.INVISIBLE);
        else if (mCurrStepId == mSteps.size() - 1)
            mNextBtn.setVisibility(View.INVISIBLE);

        updateStepFragment();

    }

    // Update the step fragment after incrementing or decrementing the step ID
    private void updateStepFragment() {

        setTitle("Step " + mSteps.get(mCurrStepId).getId());

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepDetailFragment.STEP_ARGUMENT, mSteps.get(mCurrStepId));

        stepDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_detail_fragment, stepDetailFragment)
                .commit();
    }

    // Increment the step ID and show the next step
    public void onNextPressed(View view) {
        if (mCurrStepId == 0)
            mPrevBtn.setVisibility(View.VISIBLE);

        mCurrStepId++;

        if (mCurrStepId == mSteps.size() - 1)
            mNextBtn.setVisibility(View.INVISIBLE);
        updateStepFragment();
    }

    // Decrement the step ID and show the previous step
    public void onPrevPressed(View view) {
        if (mCurrStepId == mSteps.size() - 1)
            mNextBtn.setVisibility(View.VISIBLE);

        mCurrStepId--;

        if (mCurrStepId == 0)
            mPrevBtn.setVisibility(View.INVISIBLE);
        updateStepFragment();
    }

    // Save current step ID
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STEPS_KEY, mCurrStepId);
        super.onSaveInstanceState(outState);
    }
}
