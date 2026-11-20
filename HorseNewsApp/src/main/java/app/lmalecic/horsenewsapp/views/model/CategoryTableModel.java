/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.Category;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lmalecic
 */
public class CategoryTableModel extends AbstractTableModel {
    
    private static final String[] COLUMNS = { "Id", "Name" };
    private List<Category> categories;
    
    public CategoryTableModel(List<Category> categories) {
        this.categories = categories;
    }
    
    public void set(List<Category> categories) {
        this.categories = categories;
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category category = categories.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return category.getId();
            case 1:
                return category.getName();
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    public List<Category> getItems() {
        return this.categories;
    }
}
