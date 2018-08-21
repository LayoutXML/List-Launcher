package com.layoutxml.listlauncher.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.layoutxml.listlauncher.R;

public class HomescreenFragment extends Fragment implements View.OnClickListener{

    public HomescreenFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homescreen, container, false);
    }

    @Override
    public void onClick(View v) {

    }
}
