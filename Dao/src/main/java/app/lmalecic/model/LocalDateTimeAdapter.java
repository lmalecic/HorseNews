/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author lmalecic
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public LocalDateTime unmarshal(String vt) throws Exception {
        return (vt == null || vt.isEmpty()) ? null : LocalDateTime.parse(vt, FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime bt) throws Exception {
        return (bt == null) ? null : bt.format(FORMATTER);
    }
    
}
