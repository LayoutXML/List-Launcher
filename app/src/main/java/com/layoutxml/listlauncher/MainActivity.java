package com.layoutxml.listlauncher;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.layoutxml.applistmanagerlibrary.AppList;
import com.layoutxml.applistmanagerlibrary.interfaces.ActivityListener;
import com.layoutxml.applistmanagerlibrary.interfaces.NewActivityListener;
import com.layoutxml.applistmanagerlibrary.interfaces.SortListener;
import com.layoutxml.applistmanagerlibrary.interfaces.UninstalledActivityListener;
import com.layoutxml.applistmanagerlibrary.objects.AppData;
import com.layoutxml.listlauncher.fragments.AppDrawerFragment;
import com.layoutxml.listlauncher.fragments.HomescreenFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityListener, NewActivityListener, UninstalledActivityListener, SortListener {

    private static final String TAG = "MainActivity";
    public static List<AppData> appDataList = new ArrayList<>();
    private Intent mainIntent = new Intent();
    private Boolean waitingForList = true;

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

        AppList.registerListeners(null,MainActivity.this,null,MainActivity.this,null,MainActivity.this,MainActivity.this);

        mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        AppList.getAllActivities(getApplicationContext(),mainIntent,0);

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
    protected void onDestroy() {
        super.onDestroy();
        AppList.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (appDataList!=null && appDataList.size()!=0) {
            AppList.getAllNewActivities(getApplicationContext(), appDataList, mainIntent, 1);
            AppList.getAllUninstalledActivities(getApplicationContext(), appDataList, mainIntent, 2);
        }
    }

    @Override
    public void activityListener(List<AppData> list, Intent intent, Integer integer, Integer integer1, Boolean aBoolean, Integer integer2) {
        AppList.sort(list,AppList.BY_APPNAME,AppList.IN_ASCENDING,integer2);
    }

    @Override
    public void newActivityListener(List<AppData> list, Intent intent, Integer integer, Integer integer1, Boolean aBoolean, Boolean aBoolean1, Integer integer2) {
        list.addAll(appDataList);
        AppList.sort(list, AppList.BY_APPNAME,AppList.IN_ASCENDING,integer2);
    }

    @Override
    public void uninstalledActivityListener(List<AppData> list, Intent intent, Integer integer, Integer integer1, Boolean aBoolean, Boolean aBoolean1, Integer integer2) {
        appDataList.removeAll(list);
    }

    @Override
    public void sortListener(List<AppData> list, Integer integer, Integer integer1, Integer integer2) {
        appDataList.clear();
        appDataList.addAll(list);
        if (AppDrawerFragment.appListAdapter!=null)
            AppDrawerFragment.appListAdapter.notifyDataSetChanged();
    }
}
