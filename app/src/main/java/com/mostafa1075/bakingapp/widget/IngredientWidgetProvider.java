package com.mostafa1075.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.mostafa1075.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String ingredients) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);

        Intent intent = new Intent(context, ListWidgetService.class);
        // Adapted from: https://stackoverflow.com/a/11387266
        intent.setData(Uri.fromParts("content", ingredients, null));
        views.setRemoteAdapter(R.id.ingredients_listview, intent);
        views.setEmptyView(R.id.ingredients_listview, R.id.ingredients_placeholder);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds, String ingredients) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

