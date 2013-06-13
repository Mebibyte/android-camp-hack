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
    public static final String ARG_VIEW_NUMBER = "view_number";
    private int _resource;

    public CodersBestFragment(int resource) {
        super();
        _resource = resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(_resource, container, false);
        //Log.d("FRAGMENT", (rootView.findViewById(R.id.drawPanel) == null) + "");
        return rootView;
    }

    public void onClick() {
        //Blah
    }
}
