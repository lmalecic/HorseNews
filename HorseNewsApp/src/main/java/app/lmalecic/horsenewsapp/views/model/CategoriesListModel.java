/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.Category;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author lmalecic
 */
public class CategoriesListModel extends AbstractListModel<Category> {

    private List<Category> categories;
    
    public CategoriesListModel() {
        this.categories = new ArrayList<>();
    }
    
    public CategoriesListModel(List<Category> categories) {
        this.categories = new ArrayList<>();
        this.set(categories);
    }
    
    public List<Category> getAll() {
        return this.categories;
    }
    
    public void set(List<Category> categories) {
        int oldSize = this.categories.size();
        
        // Clear old data
        if (oldSize > 0) {
            this.categories.clear();
            fireIntervalRemoved(this, 0, oldSize - 1);
        }
        
        // Add new data
        if (categories != null && !categories.isEmpty()) {
            this.categories.addAll(categories);
            fireIntervalAdded(this, 0, this.categories.size() - 1);
        }
    }
    
    public void clear() {
        int oldSize = this.categories.size();
        if (oldSize > 0) {
            this.categories.clear();
            fireIntervalRemoved(this, 0, oldSize - 1);
        }
    }
    
    public void add(Category category) {
        this.categories.add(category);
        int index = this.categories.size() - 1;
        fireIntervalAdded(this, index, index);
    }
    
    public void remove(Category category) {
        int index = this.categories.indexOf(category);
        if (index >= 0) {
            this.categories.remove(index);
            fireIntervalRemoved(this, index, index);
        }
    }
    
    @Override
    public int getSize() {
        return this.categories.size();
    }

    @Override
    public Category getElementAt(int index) {
        return this.categories.get(index);
    }
}
