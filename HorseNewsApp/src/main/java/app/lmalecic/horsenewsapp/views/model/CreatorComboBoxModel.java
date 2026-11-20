/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.horsenewsapp.views.model;

import app.lmalecic.model.Creator;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author lmalecic
 */
public class CreatorComboBoxModel implements ComboBoxModel {
    
    private List<Creator> creators;
    private Creator selectedCreator;

    public CreatorComboBoxModel(List<Creator> creators) {
        this.creators = creators;
        if (!creators.isEmpty()) {
            this.selectedCreator = creators.get(0);
        }
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof Creator) {
            this.selectedCreator = (Creator) anItem;
        }
    }

    @Override
    public Creator getSelectedItem() {
        return this.selectedCreator;
    }

    @Override
    public int getSize() {
        return this.creators.size();
    }

    @Override
    public Creator getElementAt(int index) {
        return this.creators.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
    
}
