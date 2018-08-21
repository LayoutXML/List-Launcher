package com.layoutxml.listlauncher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Homescreen extends Fragment implements View.OnClickListener{

    public Homescreen() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homescreen, container, false);
    }

    @Override
    public void onClick(View v) {

    }
}
