package com.layoutxml.listlauncher.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.layoutxml.listlauncher.R;
import com.layoutxml.listlauncher.objects.AppData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LayoutXML on 21/08/2018
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    public static List<AppData> appList;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView appName;
        ImageView appIcon;

        ViewHolder(View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.appNameTextView);
            appIcon = itemView.findViewById(R.id.appIconImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Context context = view.getContext();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appList.get(position).getPackageName());
            context.startActivity(launchIntent);
        }
    }

    public AppListAdapter(Context context) {
        appList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull AppListAdapter.ViewHolder viewHolder, int integer) {
        String appName = appList.get(integer).getName();
        String appPackageName = appList.get(integer).getPackageName();
        Drawable appIcon = appList.get(integer).getIcon();

        TextView textView = viewHolder.appName;
        textView.setText(appName);

        ImageView imageView = viewHolder.appIcon;
        imageView.setImageDrawable(appIcon);
        imageView.setContentDescription(appName);
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