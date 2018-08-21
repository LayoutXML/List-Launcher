package com.layoutxml.listlauncher;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.layoutxml.listlauncher.fragments.AppDrawerFragment;
import com.layoutxml.listlauncher.fragments.HomescreenFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            HomescreenFragment homescreenFragmentFragment = new HomescreenFragment();
            homescreenFragmentFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homescreenFragmentFragment).commit();

        }

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        switch (menuItem.getItemId()) {
                            case R.id.drawerItem:
                                AppDrawerFragment appdrawerFragment = new AppDrawerFragment();
                                appdrawerFragment.setArguments(getIntent().getExtras());
                                transaction.replace(R.id.fragment_container,appdrawerFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                return true;
                            case R.id.homeItem:
                                HomescreenFragment homescreenFragmentFragment = new HomescreenFragment();
                                homescreenFragmentFragment.setArguments(getIntent().getExtras());
                                transaction.replace(R.id.fragment_container, homescreenFragmentFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                return true;
                        }
                        return false;
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
    }
}
