package com.HACK.codersbestfriend;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

public class Task {
    private String mTitle;
    private Collection<Tag> mTags;
    private long mID;

    public Task(String title, Collection<Tag> tags) {
        mTitle = title;
        mTags = tags;
    }

    public Task(String title, String tags) {
        mTitle = title;
        mTags = new ArrayList<Tag>();
        String[] tag = tags.split(",");
        for (String t : tag) {
            //Log.i("CREATE", "-" + t + "-");
            mTags.add(Tag.toTag(t));
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public Collection<Tag> getTags() {
        return mTags;
    }

    public long getRowID() {
        return mID;
    }

    public void setRowID(long row) {
        mID = row;
    }

    public boolean hasTag(String tag) {
        Log.i("SOOO", "-" + tag + "-" + mTags.contains(Tag.toTag(tag)));
        return mTags.contains(Tag.toTag(tag));
    }
}
