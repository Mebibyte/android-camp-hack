package com.HACK.codersbestfriend;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by demouser on 6/12/13.
 */
public class NewTaskFragment extends CodersBestFragment{
    public static final Tag[][] TAGS = {{Tag.MINOR, Tag.MAJOR}, {Tag.BUG, Tag.SPEC}, {Tag.FRONT_END, Tag.BACK_END}};
    private CodersBFDatabaseAdapter mDbAdapter;

    public NewTaskFragment() {
        super(R.layout.fragment_new_task);
        mDbAdapter = new CodersBFDatabaseAdapter(getActivity());
        mDbAdapter.open();
    }

    /*
     * Called when submit button is pressed
     */
    public void onClick(View view) {
        String title = ((EditText) getView().findViewById(R.id.task_title)).getText().toString();
        Tag[] tags = new Tag[3];
        RadioGroup[] groups = {(RadioGroup) getView().findViewById(R.id.group1),
                (RadioGroup) getView().findViewById(R.id.group2),
                (RadioGroup) getView().findViewById(R.id.group3)};

        for (int i = 0; i < 3; i++) {
            RadioGroup group = groups[i];
            int id = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(id);
            int clicked = group.indexOfChild(radioButton);
            tags[i] = TAGS[i][clicked];
        }
        Log.i("Tag", Arrays.toString(tags));
        Task task = new Task(title, Arrays.asList(tags));
    }
}
