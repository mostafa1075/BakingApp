package com.mostafa1075.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class IngredientsUpdateService extends IntentService {

    private static final String ACTION_UPDATE_INGREDIENTS = "com.mostafa1075.bakingapp.action.update_ingredients";
    private static final String EXTRA_INGREDIENTS_KEY  = "com.mostafa1075.bakingapp.extra.ingredients_key";

    public IngredientsUpdateService() {
        super("IngredientsUpdateService");
    }

    public static void startActionUpdateIngredients(Context context, String ingredients){
        Intent intent = new Intent(context, IngredientsUpdateService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putExtra(EXTRA_INGREDIENTS_KEY, ingredients);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        assert intent != null;
        if(intent.getAction().equals(ACTION_UPDATE_INGREDIENTS)){
            String ingredients = intent.getStringExtra(EXTRA_INGREDIENTS_KEY);
            handleActionUpdateIngredients(ingredients);
        }
    }

    private void handleActionUpdateIngredients(String ingredients){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        IngredientWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds, ingredients);
    }
}
