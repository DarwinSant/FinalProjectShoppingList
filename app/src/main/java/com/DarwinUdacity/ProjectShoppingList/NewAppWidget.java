package com.DarwinUdacity.ProjectShoppingList;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.DarwinUdacity.ProjectShoppingList.ui.list.ShoppingListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        //Button funtionality in the Widget(Go App)
        Intent intent= new Intent(context, ShoppingListActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.button_widget,pendingIntent);



                 setRemoteAdapter(context,views);

                 // Instruct the widget manager to update the widget
                 appWidgetManager.updateAppWidget(appWidgetId, views);




    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list, new Intent(context, WidgetService.class));
    }
}

