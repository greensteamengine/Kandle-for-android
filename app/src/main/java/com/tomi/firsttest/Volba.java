package com.tomi.firsttest;

/**
 * Created by Vicko on 27. 12. 2016.
 */

public class Volba {

    String type = null;
    String name = null;
    boolean selected = false;

    public Volba(String type, String name, boolean selected) {
        super();
        this.type = type;
        this.name = name;
        this.selected = selected;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
