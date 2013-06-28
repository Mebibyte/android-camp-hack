/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.HACK.codersbestfriend;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment mCurrentFragment;
    private CodersBFDatabaseAdapter mDbAdapter;

    private DrawPanel draw;
    private MenuItem m_timerMenuItem;
    private boolean timerRunning;
    public static final String ARG_VIEW_NUMBER = "view_number";

    public List<DrawPath> paths = new ArrayList<DrawPath>();
    public List<DrawPath> backupPaths = new ArrayList<DrawPath>();

    public static int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Fix changing orientation switching reverting current fragment to tasks.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, getResources().getStringArray(R.array.nav_items)));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            mCurrentFragment = new CodersBestFragment(R.layout.home);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, mCurrentFragment).commit();

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    selectItem(0);
                }
            }, 1000);
        }

        mDbAdapter = new CodersBFDatabaseAdapter(this);
        mDbAdapter.open();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        this.m_timerMenuItem = menu.getItem(0);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.timer:
                if (timerRunning) mCurrentFragment = new CodersBestFragment(R.layout.fragment_timer_stop);
                else mCurrentFragment = new CodersBestFragment(R.layout.fragment_timer);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, mCurrentFragment).commit();

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(2, true);
                mDrawerLayout.closeDrawer(mDrawerList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void newTaskSpawn(View view) {
        mCurrentFragment = new NewTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIEW_NUMBER, 0);
        mCurrentFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, mCurrentFragment).commit();
    }

    // We can cast this because this is the onClick method of the NewTask view
    public void newTaskCreate(View view) {
        ((CodersBestFragment) mCurrentFragment).onClick("CREATE");
    }

    public void newTaskFinish(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.task_title).getWindowToken(), 0);
        selectItem(0);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Log.i("ITEM", position + "");
        if (mCurrentFragment instanceof CodersBestFragment) {
            if (((CodersBestFragment) mCurrentFragment).getResource() == R.layout.activity_design) {
                ((DrawPanel)(findViewById(R.id.drawPanel))).closing(this);
            }
        }
        switch(position) {
            case 0: // Tasks
                mCurrentFragment = new TaskViewFragment();
                break;
            case 1: // Design
                mCurrentFragment = new CodersBestFragment(R.layout.activity_design);
                //((CodersBestFragment)mCurrentFragment).setPaths(paths);
                //((CodersBestFragment)mCurrentFragment).setBackupPaths(paths);
                break;
            case 2: // Timer
                if (timerRunning) {
                    mCurrentFragment = new CodersBestFragment(R.layout.fragment_timer_stop);
                    break;
                }
                mCurrentFragment = new CodersBestFragment(R.layout.fragment_timer);
                break;
            default:
                mCurrentFragment = new CodersBestFragment(R.layout.activity_main);
        }
        Bundle args = new Bundle();
        args.putInt(ARG_VIEW_NUMBER, position);
        mCurrentFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(com.HACK.codersbestfriend.R.id.content_frame, mCurrentFragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void startTimer(View view) {
        final Handler h = new Handler();
        final Context c = this;
        String timeText = ((EditText) findViewById(R.id.timerEditText)).getText().toString();
        if (!timerRunning) {
            if (!timeText.equals("")) {
                timerRunning = true;
                ((TextView) findViewById(R.id.timer_error_text)).setText("");
                final int time = Integer.parseInt(timeText);
                m_timerMenuItem.setTitle("" + time);
                Runnable r = new Runnable() {
                    long m_startTime = System.currentTimeMillis();
                    long m_endTime = m_startTime + 1000 * time;

                    @Override
                    public void run() {
                        if ( System.currentTimeMillis() < m_endTime && timerRunning) {
                            // if the time hasn't elapsed
                            h.postDelayed(this, 1000);
                            // update the actionBar
                            m_timerMenuItem.setTitle(Long.toString(time + ((m_startTime - System.currentTimeMillis())/1000)));
                        } else {
                            if (timerRunning) {
                                Toast.makeText(c, "Timer Done!", Toast.LENGTH_LONG).show();
                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                v.vibrate(500);
                            }
                            timerRunning = false;
                            m_timerMenuItem.setTitle("Set Timer");
                        }
                    }
                };
                h.postDelayed(r, 1000);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(R.id.timerEditText).getWindowToken(), 0);

                mCurrentFragment = new CodersBestFragment(R.layout.fragment_timer_stop);
                Bundle args = new Bundle();
                mCurrentFragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(com.HACK.codersbestfriend.R.id.content_frame, mCurrentFragment).commit();
            } else {
               ((TextView) findViewById(R.id.timer_error_text)).setText("Enter a time!");
            }
        }
    }

    public void stopTimer(View view) {
        m_timerMenuItem.setTitle("Set Timer");
        timerRunning = false;
        mCurrentFragment = new CodersBestFragment(R.layout.fragment_timer);
        Bundle args = new Bundle();
        mCurrentFragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(com.HACK.codersbestfriend.R.id.content_frame, mCurrentFragment).commit();
    }

    public static class ViewFragment extends Fragment {
        public static final String ARG_VIEW_NUMBER = "view_number";

        public ViewFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            int i = getArguments().getInt(ARG_VIEW_NUMBER);
            String newView = getResources().getStringArray(R.array.nav_items)[i];
            ((TextView) rootView.findViewById(R.id.textView)).setText(newView);
            getActivity().setTitle(newView);
            return rootView;
        }
    }

    public static class DesignFragment extends Fragment {
        public static final String ARG_VIEW_NUMBER = "view_number";

        public DesignFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_design, container, false);
            int i = getArguments().getInt(ARG_VIEW_NUMBER);
            String newView = getResources().getStringArray(R.array.nav_items)[i];
            getActivity().setTitle(newView);
            return rootView;
        }
    }

    //Methods for changing design state
    public void changeToPencil(View view) {
        draw = (DrawPanel) mCurrentFragment.getView().findViewById(R.id.drawPanel);
        draw.toDraw();
    }

    public void changeToEraser(View view) {
        draw = (DrawPanel) mCurrentFragment.getView().findViewById(R.id.drawPanel);
        draw.toErase();
    }

    public void changeToRectangle(View view) {
        draw = (DrawPanel) mCurrentFragment.getView().findViewById(R.id.drawPanel);
        draw.toRect();
    }

    public void undo(View view) {
        draw = (DrawPanel) mCurrentFragment.getView().findViewById(R.id.drawPanel);
        draw.undo();
    }

    public void clear(View view) {
        draw = (DrawPanel) mCurrentFragment.getView().findViewById(R.id.drawPanel);
        draw.clear();
    }

    public void saveImage(Bitmap bmp, String title, String description)  {
        MediaStore.Images.Media.insertImage(getContentResolver(), bmp, title, description);
    }

    public CodersBFDatabaseAdapter getAdapter() {
        return mDbAdapter;
    }

    public void setPaths(List<DrawPath> paths) {
        this.paths = paths;
    }

    public List<DrawPath> getPaths() {
        return paths;
    }

    public void setBackupPaths(List<DrawPath> paths) {
        this.backupPaths = paths;
    }

    public List<DrawPath> getBackupPaths() {
        return backupPaths;
    }
}