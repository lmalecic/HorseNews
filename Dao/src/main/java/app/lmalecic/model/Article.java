/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.model;

import app.lmalecic.dal.annotations.ColumnName;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author lmalecic
 */
@XmlRootElement(name = "article")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "title", "description", "datePublished", "creator", "link", "categories", "guid", "image" })
public class Article implements Entity {
    
    @XmlTransient
    @ColumnName("Id")
    private int id;
    
    @ColumnName("Title")
    private String title;
    
    @ColumnName("Link")
    private String link;
    
    @ColumnName("Description")
    private String description;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @ColumnName("DatePublished")
    private LocalDateTime datePublished;
    
    @ColumnName("CreatorId")
    private Creator creator;
    
    @ColumnName("Guid")
    private String guid;
    
    @ColumnName("Image")
    private byte[] image;
    
    @XmlElementWrapper
    @XmlElement(name = "category")
    private List<Category> categories = new ArrayList<Category>();
    
    public Article() {
    }
    
    public Article(String title, String link, Creator creator, LocalDateTime datePublished, String description, String guid) {
        this.setTitle(title);
        this.setLink(link);
        this.setCreator(creator);
        this.setDatePublished(datePublished);
        this.setDescription(description);
        this.setGuid(guid);
    }
    
    public Article(int id, String title, String link, Creator creator, LocalDateTime datePublished, String description, String guid) {
        this(title, link, creator, datePublished, description, guid);
        this.setId(id);
    }
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }

    public Creator getCreator() {
        return this.creator;
    }
    
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public LocalDateTime getDatePublished() {
        return this.datePublished;
    }
    
    public void setDatePublished(LocalDateTime datePublished) {
        this.datePublished = datePublished;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<Category> getCategories() {
        return this.categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }
    
    public String getGuid() {
        return this.guid;
    }
    
    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public String getCategoriesString() {
        return this.getCategories().stream()
            .map(Category::getName)
            .collect(Collectors.joining(", "));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.getId()).append("; ")
            .append(this.getTitle()).append("; ");
        
        String joined = this.getCategoriesString();
        
        sb.append(joined).append("; ")
            .append(this.getDatePublished()).append("; ")
            .append(this.getLink()).append("; ")
            .append(this.getDescription());
        
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }
}
