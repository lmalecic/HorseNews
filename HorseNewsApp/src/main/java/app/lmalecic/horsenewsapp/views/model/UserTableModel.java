/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.User;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lmalecic
 */
public class UserTableModel extends AbstractTableModel {
    
    private static final String[] COLUMNS = { "Id", "Username", "Role" };
    private List<User> users;
    
    public UserTableModel(List<User> users) {
        this.users = users;
    }
    
    public void set(List<User> users) {
        this.users = users;
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getUsername();
            case 2:
                return user.getRole();
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}
