
package com.HACK.codersbestfriend;

import android.app.ListActivity;
import android.app.ListFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskViewFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.tasks_list, container, false);
        fillData(rootView);
        return rootView;
    }

    SimpleAdapter adapter;
    List data;
    CodersBFDatabaseAdapter dbApdater;

    public void fillData(View view) {


        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.filter_list, R.layout.tasks_filter_list);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data = TaskManager.filter(dbApdater.fetchAllTasks(), (String)adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        data = new ArrayList<LinkedHashMap<String, String>>();
        for (int i = 0; i < 20; i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("Title", "Title" + ((Integer)i).toString());
            datum.put("Tag", "Tag" + ((Integer)i).toString());
            data.add(datum);
        }

        /*dbApdater = new CodersBFDatabaseAdapter(getActivity());
        data = dbApdater.fetchAllTasks();*/

        adapter = new SimpleAdapter(getActivity(), data,
                R.layout.tasks_row,
                new String[] {"Title", "Tag"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
        setListAdapter(adapter);

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        com.HACK.codersbestfriend.SwipeDismissListViewTouchListener touchListener =
                new com.HACK.codersbestfriend.SwipeDismissListViewTouchListener(
                        listView,
                        new com.HACK.codersbestfriend.SwipeDismissListViewTouchListener.OnDismissCallback() {
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    data.remove(adapter.getItem(position));
                                    adapter.notifyDataSetChanged();
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
