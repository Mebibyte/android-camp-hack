package com.HACK.codersbestfriend;

public enum Tag {
    MAJOR("Major"), MINOR("Minor"), BUG("Bug"), SPEC("Spec"), FRONT_END("Front end"), BACK_END("Back end");

    private String tag;

    Tag(String tag) {
        this.tag = tag;
    }

    public String toString() {
        return tag;
    }
    
    public static Tag toTag(String tag) {
        if (tag.equals("Bug")) return Tag.BUG;
        if (tag.equals("Spec")) return Tag.SPEC;
        if (tag.equals("Front end")) return Tag.FRONT_END;
        if (tag.equals("Back end")) return Tag.BACK_END;
        if (tag.equals("Minor")) return Tag.MINOR;
        return Tag.MAJOR;
    }
}



