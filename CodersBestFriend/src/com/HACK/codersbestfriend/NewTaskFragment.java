package com.HACK.codersbestfriend;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by demouser on 6/12/13.
 */
public class NewTaskFragment extends CodersBestFragment {
    public static final Tag[][] TAGS = {{Tag.MINOR, Tag.MAJOR}, {Tag.BUG, Tag.SPEC}, {Tag.FRONT_END, Tag.BACK_END}};
    private CodersBFDatabaseAdapter mDbAdapter;
    private EditText mTitle;

    public NewTaskFragment() {
        super(R.layout.fragment_new_task);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mDbAdapter = ((MainActivity) getActivity()).getAdapter();
        mTitle = (EditText) view.findViewById(R.id.task_title);
        mTitle.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        //Log.d("Soft input", b + "");
        return view;
    }

    /*
     * Called when submit button is pressed
     */
    public void onClick(String string) {
        String title = mTitle.getText().toString();
        if (title.length() == 0) {
            ((TextView) getView().findViewById(R.id.error_message)).setText("Please enter a title");
            return;
        }
        ((TextView) getView().findViewById(R.id.error_message)).setText("");
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
        Task task = new Task(title, Arrays.asList(tags));
        mDbAdapter.createTask(task);
        ((MainActivity) getActivity()).newTaskFinish(getView());
    }
}