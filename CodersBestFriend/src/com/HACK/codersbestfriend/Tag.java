package com.HACK.codersbestfriend;

/**
 * Created by demouser on 6/11/13.
 */
public enum Tag {
    MAJOR("Major"), MINOR("Minor"), BUG("Bug"), SPEC("Spec"), FRONT_END("Front end"), BACK_END("Back end");

    private String tag;

    Tag(String tag) {
        this.tag = tag;
    }

    public String toString() {
        return tag;
    }

}



