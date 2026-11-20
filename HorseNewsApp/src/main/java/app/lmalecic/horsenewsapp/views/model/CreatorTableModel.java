/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.Creator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lmalecic
 */
public class CreatorTableModel extends AbstractTableModel {
    
    private static final String[] COLUMNS = { "Id", "Name" };
    private List<Creator> creators;
    
    public CreatorTableModel(List<Creator> creators) {
        this.creators = creators;
    }
    
    public void set(List<Creator> creators) {
        this.creators = creators;
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return creators.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Creator creator = creators.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return creator.getId();
            case 1:
                return creator.getName();
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    public List<Creator> getItems() {
        return this.creators;
    }
}
