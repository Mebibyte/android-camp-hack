package com.HACK.codersbestfriend;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by demouser on 6/12/13.
 */
public class CodersBestFragment extends Fragment {

    private int _resource;
    private DrawPanel panel;

    public CodersBestFragment(int resource) {
        super();
        _resource = resource;
    }

    public int getResource() {
        return _resource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(_resource, container, false);
        if (_resource == R.layout.fragment_timer) {
            ((EditText) rootView.findViewById(R.id.timerEditText)).requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        } else if (_resource == R.layout.activity_design) {
            panel = (DrawPanel) rootView.findViewById(R.id.drawPanel);
            panel.setFragment(this);
            panel.setPath(((MainActivity)getActivity()).getPaths());
            panel.setBackupPaths(((MainActivity)getActivity()).getBackupPaths());

        }
        return rootView;
    }

    public void onClick(String string) {
        //Blah
    }

    public void setPaths(List<DrawPath> path) {
        panel.setPath(path);
    }

    public void setBackupPaths(List<DrawPath> path) {
        panel.setBackupPaths(path);
    }


}

