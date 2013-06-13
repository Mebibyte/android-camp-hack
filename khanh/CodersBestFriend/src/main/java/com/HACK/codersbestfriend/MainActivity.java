
package com.HACK.codersbestfriend;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_list);
        fillData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    ArrayAdapter<String> adapter;

    public void fillData() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.filter_list, R.layout.tasks_filter_list);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);


        String[] someStringArray = new String[20];
        for (int i = 0; i < 20; i++) someStringArray[i] = "Item " + ((Integer)(i)).toString();
        adapter = new ArrayAdapter<String>(this,
                R.layout.tasks_row, new ArrayList<String>(Arrays.asList(someStringArray)));
        setListAdapter(adapter);
        ListView listView = getListView();


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
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        Toast.makeText(this,
                "Clicked " + getListAdapter().getItem(position).toString(),
                Toast.LENGTH_SHORT).show();
    }
}
