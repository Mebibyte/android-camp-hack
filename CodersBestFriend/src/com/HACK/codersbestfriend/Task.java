package com.HACK.codersbestfriend;

import java.util.Collection;

/**
 * Created by demouser on 6/13/13.
 */
public class Task {
    private String _title;
    private Collection<Tag> _tags;

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
}
