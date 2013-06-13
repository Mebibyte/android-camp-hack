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
    public static final Tags[][] TAGS = {{Tags.MINOR, Tags.MAJOR}, {Tags.BUG, Tags.SPEC}, {Tags.FRONT_END, Tags.BACK_END}};

    public NewTaskFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_task, container, false);
        return rootView;
    }

    /*
     * Called when submit button is pressed
     */
    public void submitTask(View view) {
        String title = ((EditText) getView().findViewById(R.id.task_title)).getText().toString();
        Tags[] tags = new Tags[3];
        RadioGroup[] groups = {(RadioGroup) getView().findViewById(R.id.group1),
                (RadioGroup) getView().findViewById(R.id.group2),
                (RadioGroup) getView().findViewById(R.id.group3)};

        for (int i = 0; i < 3; i++) {
            RadioGroup group = groups[i];
            int id = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(id);
            int clicked = group.indexOfChild(radioButton);
            tags[i] = TAGS[i][id];
        }
        Log.i("Tags", Arrays.toString(tags));
    }
}
