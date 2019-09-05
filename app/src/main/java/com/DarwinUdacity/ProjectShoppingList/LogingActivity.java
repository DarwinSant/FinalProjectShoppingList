package com.DarwinUdacity.ProjectShoppingList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.DarwinUdacity.ProjectShoppingList.ui.list.ShoppingListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogingActivity extends AppCompatActivity {


    @BindView(R.id.button) Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void openMainScreen() {
        //Intent
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);

         // animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


}
