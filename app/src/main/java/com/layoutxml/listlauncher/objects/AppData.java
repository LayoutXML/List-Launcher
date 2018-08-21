package com.layoutxml.listlauncher.objects;

import android.graphics.drawable.Drawable;

/**
 * Created by LayoutXML on 21/08/2018
 */
public class AppData {
    private String name;
    private String packageName;
    private Drawable icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
