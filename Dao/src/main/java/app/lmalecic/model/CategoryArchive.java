/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lmalecic
 */
@XmlRootElement(name = "categoriesArchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryArchive {
    
    public static final String ARCHIVE_NAME = "categoriesArchive";
    
    @XmlElementWrapper
    @XmlElement(name = "category")
    private List<Category> categories;
    
    public CategoryArchive() {
    }
    
    public CategoryArchive(List<Category> categories) {
        this.categories = categories;
    }
    
}
