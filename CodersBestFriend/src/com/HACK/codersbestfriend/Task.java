package com.HACK.codersbestfriend;

import java.util.Collection;

/**
 * Created by demouser on 6/13/13.
 */
public class Task {
    private String _title;
    private Collection<Tag> _tags;
    private int _rowID;

    public Task(String title, Collection<Tag> tags) {
        _title = title;
        _tags = tags;
    }

    public String getTitle() {
        return _title;
    }

    public Collection<Tag> getTags() {
        return _tags;
    }

    public int getRowID() {
        return _rowID;
    }

    public void setRowID(int row) {
        _rowID = row;
    }
}
