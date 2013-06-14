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
        if (_resource == R.layout.fragment_timer) {
            ((EditText) rootView.findViewById(R.id.timerEditText)).requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
        return rootView;
    }

    public void onClick(String string) {
        //Blah
    }
}

