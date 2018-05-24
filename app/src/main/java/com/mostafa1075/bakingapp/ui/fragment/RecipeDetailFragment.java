package com.mostafa1075.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mostafa1075.bakingapp.IngredientsUpdateService;
import com.mostafa1075.bakingapp.R;
import com.mostafa1075.bakingapp.adapter.StepsAdapter;
import com.mostafa1075.bakingapp.pojo.Ingredient;
import com.mostafa1075.bakingapp.pojo.Recipe;
import com.mostafa1075.bakingapp.ui.MainActivity;

import java.util.List;

/**
 * Created by mosta on 18-May-18.
 */

public class RecipeDetailFragment extends Fragment {

    private Recipe mRecipe;
    private StepsAdapter.onStepClickListener mCallback;

    public RecipeDetailFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        Bundle bundle = getArguments();
        if(bundle == null)
            throw new UnsupportedOperationException("A bundle must be passed containing the recipe");

        mRecipe = bundle.getParcelable(MainActivity.RECIPE_KEY);

        List<Ingredient> ingredients = mRecipe.getIngredients();

        final StringBuilder ingredientsStringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++){
            ingredientsStringBuilder.append(ingredients.get(i).getQuantity());
            ingredientsStringBuilder.append(" ");
            ingredientsStringBuilder.append(ingredients.get(i).getMeasure());
            ingredientsStringBuilder.append(" ");
            ingredientsStringBuilder.append(ingredients.get(i).getIngredient());
            ingredientsStringBuilder.append("\n");
        }

        TextView ingredientsTV = rootView.findViewById(R.id.ingredients);
        ingredientsTV.setText(ingredientsStringBuilder.toString());

        RecyclerView recyclerView =  rootView.findViewById(R.id.steps_recyclerview);
        recyclerView.setHasFixedSize(true);
        // DividerItemDecoration adapted from: https://stackoverflow.com/a/24872169
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        StepsAdapter adapter = new StepsAdapter(getContext(),
                mRecipe.getSteps(),
                (StepsAdapter.onStepClickListener) getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false); // https://stackoverflow.com/a/21235114

        Button addWidgetBtn = rootView.findViewById(R.id.add_widget_btn);
        addWidgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientsUpdateService.startActionUpdateIngredients(getContext(), ingredientsStringBuilder.toString());
            }
        });


        return rootView;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            mCallback = (StepsAdapter.onStepClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement onStepClickListener");
//        }
//    }
}
