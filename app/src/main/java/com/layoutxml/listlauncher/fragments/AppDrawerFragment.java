package com.layoutxml.listlauncher.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.layoutxml.applistmanagerlibrary.objects.AppData;
import com.layoutxml.listlauncher.MainActivity;
import com.layoutxml.listlauncher.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.List;

/**
 * Created by LayoutXML on 21/08/2018
 */
public class AppDrawerFragment extends Fragment {

    private static final String TAG = "AppDrawerFragment";
    public static AppListAdapter appListAdapter;
    private RecyclerView recyclerView;

    public AppDrawerFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_drawer, container, false);

        appListAdapter = new AppListAdapter(MainActivity.appDataList);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(appListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

        private List<AppData> appList;

        @NonNull
        @Override
        public String getSectionName(int position) {
            AppData app = appList.get(position);
            return app.getName().substring(0,1);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView appName, packageName;
            ImageView appIcon;

            ViewHolder(View itemView) {
                super(itemView);
                appName = itemView.findViewById(R.id.itemName);
                packageName = itemView.findViewById(R.id.itemPackageName);
                appIcon = itemView.findViewById(R.id.itemLogo);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                ComponentName name = new ComponentName(appList.get(getAdapterPosition()).getPackageName(), appList.get(getAdapterPosition()).getActivityName());
                Intent i = new Intent(Intent.ACTION_MAIN);

                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(name);

                startActivity(i);
            }
        }

        AppListAdapter(List<AppData> appDataList) {
            appList = appDataList;
        }

        @Override
        public void onBindViewHolder(@NonNull AppListAdapter.ViewHolder viewHolder, int integer) {
            AppData app = appList.get(integer);
            String appName = app.getName();
            String appPackageName = app.getPackageName();
            Drawable appIcon = app.getIcon();

            viewHolder.appName.setText(appName);
            viewHolder.packageName.setText(appPackageName);
            viewHolder.appIcon.setImageDrawable(appIcon);
            viewHolder.appIcon.setContentDescription(appName);
        }

        @Override
        public int getItemCount() {
            return appList.size();
        }

        @NonNull
        @Override
        public AppListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.app_item_view, parent, false);
            return new ViewHolder(view);
        }

    }
}
