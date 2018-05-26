package com.mostafa1075.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mostafa1075.bakingapp.R;

/**
 * Created by mosta on 26-May-18.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private String[] mIngredients;

    public ListRemoteViewsFactory(Context context, String ingredients){
        mContext = context;
        mIngredients = ingredients.split("\n");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients == null) return 0;
        return mIngredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        String ingredient = mIngredients[position];

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);
        //String ingredientString = ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient();
        views.setTextViewText(R.id.ingredient_text, ingredient);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
