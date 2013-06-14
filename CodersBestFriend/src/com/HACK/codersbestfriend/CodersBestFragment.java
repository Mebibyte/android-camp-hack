package com.HACK.codersbestfriend;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by demouser on 6/12/13.
 */
public class CodersBestFragment extends Fragment {

    private int _resource;

    public CodersBestFragment(int resource) {
        super();
        _resource = resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(_resource, container, false);
        return rootView;
    }

    public void onClick(String string) {
        //Blah
    }
}

