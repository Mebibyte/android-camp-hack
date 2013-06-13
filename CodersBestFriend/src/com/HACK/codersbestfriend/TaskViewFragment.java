
package com.HACK.codersbestfriend;

import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskViewFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.tasks_list, container, false);
        fillData(rootView);
        return rootView;
    }

    ArrayAdapter<String> adapter;

    public void fillData(View view) {

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.filter_list, R.layout.tasks_filter_list);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        String[] someStringArray = new String[20];
        for (int i = 0; i < 20; i++) someStringArray[i] = "Item " + ((Integer)(i)).toString();
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.tasks_row, new ArrayList<String>(Arrays.asList(someStringArray)));
        setListAdapter(adapter);
        ListView listView = (ListView) view.findViewById(android.R.id.list);


        com.HACK.codersbestfriend.SwipeDismissListViewTouchListener touchListener =
                new com.HACK.codersbestfriend.SwipeDismissListViewTouchListener(
                        listView,
                        new com.HACK.codersbestfriend.SwipeDismissListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    adapter.remove(adapter.getItem(position));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Toast.makeText(getActivity(),
                "Clicked " + getListAdapter().getItem(position).toString(),
                Toast.LENGTH_SHORT).show();
    }
}