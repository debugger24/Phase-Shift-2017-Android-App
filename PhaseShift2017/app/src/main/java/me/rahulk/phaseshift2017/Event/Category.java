package me.rahulk.phaseshift2017.Event;

/**
 * Created by debugger24 on 12/08/17.
 */

public class Category {
    String categoryTitle;
    String categoryDesc;
    int categoryImage; // drawable reference id

    public Category(String categoryTitle, String categoryDesc, int categoryImage) {
        this.categoryTitle = categoryTitle;
        this.categoryDesc = categoryDesc;
        this.categoryImage = categoryImage;
    }
}
