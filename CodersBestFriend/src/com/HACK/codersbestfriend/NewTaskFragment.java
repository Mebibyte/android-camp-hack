package com.HACK.codersbestfriend;

import com.HACK.codersbestfriend.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Arrays;

/**
 * Created by demouser on 6/12/13.
 */
public class NewTaskFragment extends Fragment{
    public static final String ARG_VIEW_NUMBER = "view_number";

    public NewTaskFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_task, container, false);
        return rootView;
    }


}
