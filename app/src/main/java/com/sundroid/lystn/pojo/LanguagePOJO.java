package com.sundroid.lystn.pojo;

public class LanguagePOJO {
    int drawable_id;
    String name;
    boolean selected;

    public LanguagePOJO(int drawable_id, String name, boolean selected) {
        this.drawable_id = drawable_id;
        this.name = name;
        this.selected = selected;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
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
