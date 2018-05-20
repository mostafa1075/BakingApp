package com.mostafa1075.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mostafa1075.bakingapp.pojo.Ingredient;
import com.mostafa1075.bakingapp.pojo.Recipe;

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

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++){
            stringBuilder.append(ingredients.get(i).getQuantity());
            stringBuilder.append(" ");
            stringBuilder.append(ingredients.get(i).getMeasure());
            stringBuilder.append(" ");
            stringBuilder.append(ingredients.get(i).getIngredient());
            stringBuilder.append("\n");
        }

        TextView ingredientsTV = rootView.findViewById(R.id.ingredients);
        ingredientsTV.setText(stringBuilder.toString());

        RecyclerView recyclerView =  rootView.findViewById(R.id.steps_recyclerview);
        recyclerView.setHasFixedSize(true);

        StepsAdapter adapter = new StepsAdapter(getContext(),
                mRecipe.getSteps(),
                (StepsAdapter.onStepClickListener) getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

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
