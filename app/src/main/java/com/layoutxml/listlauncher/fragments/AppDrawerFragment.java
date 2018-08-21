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
        recyclerView.setAdapter(appListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        packageManager = view.getContext().getPackageManager();

        new newThread().execute();

        return view;
    }

    public void updateStuff() {
        appListAdapter.notifyItemInserted(appListAdapter.getItemCount()-1);

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

            return "Success";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateStuff();
        }

    }
}
