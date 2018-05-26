package com.mostafa1075.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by mosta on 26-May-18.
 */

public class ListWidgetService extends RemoteViewsService {

    public static final String INGREDIENTS_EXTRA = "ingredients";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // Adapted from: https://stackoverflow.com/a/11387266
        String ingredients = intent.getData().getSchemeSpecificPart();
        return new ListRemoteViewsFactory(getApplicationContext(), ingredients);
    }
}
