package com.layoutxml.listlauncher.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.layoutxml.listlauncher.R;
import com.layoutxml.listlauncher.adapters.AppListAdapter;
import com.layoutxml.listlauncher.objects.AppData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LayoutXML on 21/08/2018
 */
public class AppDrawerFragment extends Fragment {

    private static final String TAG = "AppDrawerFragment";
    private AppListAdapter appListAdapter;
    private PackageManager packageManager;

    public AppDrawerFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_drawer, container, false);
        appListAdapter = new AppListAdapter(view.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(appListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        packageManager = view.getContext().getPackageManager();

        new newThread().execute();

        return view;
    }

    public void updateStuff() {
        appListAdapter.notifyDataSetChanged();

    }

    public class newThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {


            AppListAdapter.appList = new ArrayList<>();

            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);
            for (ResolveInfo app:apps) {
                AppData newApp = new AppData();
                newApp.setName(app.loadLabel(packageManager).toString());
                newApp.setPackageName(app.activityInfo.packageName);
                newApp.setIcon(app.activityInfo.loadIcon(packageManager));
                AppListAdapter.appList.add(newApp);
            }

            Collections.sort(AppListAdapter.appList, new Comparator<AppData>(){
                public int compare(AppData obj1, AppData obj2) {
                    //Ascending order
                    return obj1.getName().compareToIgnoreCase(obj2.getName()); // To compare string values

                    // Descending order
                    // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                }
            });

            return "Success";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateStuff();
        }

    }
}
