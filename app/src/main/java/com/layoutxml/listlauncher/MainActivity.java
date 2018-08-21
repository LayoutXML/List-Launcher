package com.layoutxml.listlauncher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.layoutxml.listlauncher.activities.AppDrawer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemReselectedListener(
                new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.drawerItem:
                                Intent intent = new Intent(MainActivity.this,AppDrawer.class);
                                MainActivity.this.startActivity(intent);
                                break;
                        }
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
    }
}
