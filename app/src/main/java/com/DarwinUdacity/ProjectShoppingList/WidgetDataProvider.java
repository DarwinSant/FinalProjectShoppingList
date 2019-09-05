package com.DarwinUdacity.ProjectShoppingList;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.DarwinUdacity.ProjectShoppingList.model.ClassList;
import com.DarwinUdacity.ProjectShoppingList.model.ShoppingListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.DarwinUdacity.ProjectShoppingList.ui.list.ShoppingListActivity.TAG;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<ClassList> clName = new ArrayList<>();
    List<ClassList> clCreateBy = new ArrayList<>();
    List<ClassList> clDescriptions = new ArrayList<>();
    private Context context;


    private void initializeData() {
        DatabaseReference mainNode = FirebaseDatabase.getInstance().getReference().child("items");
        mainNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clName.clear();
                clCreateBy.clear();
                clDescriptions.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassList cl1 = new ClassList();
                    ClassList cl2 = new ClassList();
                    ClassList cl3 = new ClassList();

                    ShoppingListItem data = snapshot.getValue(ShoppingListItem.class);
                    Log.d(TAG, "onDataChange: " + data);
                    cl1.setClassName(data.name);
                    cl2.setClassName(data.createdBy);
                    cl3.setClassName(data.description);
                    clName.add(cl1);
                    clCreateBy.add(cl2);
                    clDescriptions.add(cl3);
                }
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        initializeData();

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        Log.d("TAG", "Total count is " + clName.size());
        return clName.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_enrolled_list_item);
        remoteViews.setTextViewText(R.id.widget_name, "Product: " + clName.get(i).getClassName());
        remoteViews.setTextViewText(R.id.widget_createby, "createBy: " + clCreateBy.get(i).getClassName());
        remoteViews.setTextViewText(R.id.widget_description, "Description: " + clDescriptions.get(i).getClassName());
        return remoteViews;
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}