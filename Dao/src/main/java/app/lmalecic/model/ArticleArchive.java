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
@XmlRootElement(name = "articlesArchive")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ArticleArchive {

    public static final String ARCHIVE_NAME = "articlesArchive";
    
    @XmlElementWrapper
    @XmlElement(name = "article")
    private List<Article> articles;
    
    public ArticleArchive() {
    }
    
    public ArticleArchive(List<Article> articles) {
        this.articles = articles;
    }
    
}
