package com.HACK.codersbestfriend;

import java.util.Collection;

/**
 * Created by demouser on 6/13/13.
 */
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
        String[] tag = tags.split(",");
        for (String t : tag) mTags.add(Tag.toTag(t));
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
        return mTags.contains(Tag.toTag(tag));
    }
}
