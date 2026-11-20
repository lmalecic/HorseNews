/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.Role;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author lmalecic
 */
public class RoleComboBoxModel implements ComboBoxModel {
    
    private List<Role> roles;
    private Role selectedRole;

    public RoleComboBoxModel(List<Role> roles) {
        this.roles = roles;
        if (!roles.isEmpty()) {
            this.selectedRole = roles.get(0);
        }
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof Role) {
            this.selectedRole = (Role) anItem;
        }
    }

    @Override
    public Role getSelectedItem() {
        return this.selectedRole;
    }

    @Override
    public int getSize() {
        return this.roles.size();
    }

    @Override
    public Role getElementAt(int index) {
        return this.roles.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
    
}
