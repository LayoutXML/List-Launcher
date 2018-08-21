package com.layoutxml.listlauncher.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.layoutxml.listlauncher.R;
import com.layoutxml.listlauncher.adapters.AppListAdapter;
import com.layoutxml.listlauncher.objects.AppData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LayoutXML on 21/08/2018
 */
public class AppDrawer extends Activity {

    private static final String TAG = "AppDrawer";
    private AppListAdapter appListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_drawer);

        appListAdapter = new AppListAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(appListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new newThread().execute();
    }

    public void updateStuff() {
        appListAdapter.notifyItemInserted(appListAdapter.getItemCount()-1);

    }

    public class newThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            PackageManager packageManager = getApplicationContext().getPackageManager();
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
