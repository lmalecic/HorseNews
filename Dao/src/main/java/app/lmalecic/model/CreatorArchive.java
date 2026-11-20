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
@XmlRootElement(name = "creatorsArchive")
@XmlAccessorType(XmlAccessType.NONE)
public class CreatorArchive {

    public static final String ARCHIVE_NAME = "creatorsArchive";
    
    @XmlElementWrapper
    @XmlElement(name = "creator")
    private List<Creator> creators;

    public CreatorArchive() {
    }

    public CreatorArchive(List<Creator> creators) {
        this.creators = creators;
    }
    
}
