package com.mostafa1075.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mostafa1075.bakingapp.pojo.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosta on 12-May-18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    List<Recipe> mRecipes;
    Context mContext;
    RecipesAdpaterOnClickHandler mClickHandler;

    public RecipesAdapter(Context context, RecipesAdpaterOnClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
        mRecipes = new ArrayList<Recipe>();
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_list__item, parent, false);
        RecipesAdapterViewHolder recipeViewHolder = new RecipesAdapterViewHolder(view);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        String recipeName = mRecipes.get(position).getName();
        holder.mRecipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void swapRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public interface RecipesAdpaterOnClickHandler{void onRecipeClick(Recipe recipe);}

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mRecipeName;

        public RecipesAdapterViewHolder(View itemView) {
            super(itemView);
            mRecipeName = (TextView) itemView.findViewById(R.id.recipe_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onRecipeClick(mRecipes.get(position));
        }
    }
}
