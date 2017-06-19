/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author miguel
 */
@FacesConverter("localDateTimeFacesConverter")
public class LocalDateTimeFacesConverter implements Converter {



    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

if (null == string || string.isEmpty()) {
return null;
}

LocalDateTime localDateTime = null;

try {
  
localDateTime = LocalDateTime.parse(string.trim(),DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm").withZone(ZoneId.systemDefault()));


} catch (DateTimeParseException e) {

throw new ConverterException("O formato da data e hora deve ser 13/11/2015 12:00:00.");
}

return localDateTime;    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
if (null == o) {

return "";
}

return ((LocalDateTime) o)
.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")
.withZone(ZoneId.systemDefault()));
    }
}

