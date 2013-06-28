package com.HACK.codersbestfriend;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class TaskManager {
    public static ArrayList<LinkedHashMap<String, String>> filter(ArrayList<LinkedHashMap<String, String>> tasks, String tag) {
        //Log.i("FILTER", tag);
        ArrayList<LinkedHashMap<String, String>> ans = new ArrayList<LinkedHashMap<String, String>>();
        if (tasks == null) return ans;
        //Log.i("FILTER", "" + tasks.size());
        for (LinkedHashMap<String, String> item : tasks) {
            //Log.i("TASK", "" + "Title: " + item.get("Title") + "Tags: " + item.get("Tag"));
            if (!item.get("Title").equals("") && !item.get("Tags").equals("")) {
                Task task = new Task(item.get("Title"), item.get("Tags"));
                Log.i("FILTER", item.get("Tags"));
                if (task.hasTag(tag)||tag.equals("All")) ans.add(item);
            }
        }
        //Log.i("FILTER", "" + ans.size());
        return ans;
    }
}
