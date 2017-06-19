/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
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
 */@FacesConverter("localDateFacesConverter")
public class LocalDateFacesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
 if (null == string || string.isEmpty()) {
return null;
}

LocalDate localDate;

try {
localDate = LocalDate.parse(
string,
DateTimeFormatter.ofPattern("dd/MM/yyyy"));

} catch (DateTimeParseException e) {

throw new ConverterException("O ano deve conter 4 dígitos. Exemplo: 13/11/2015.");
}

return localDate;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
    

if (null == o) {

return "";
}

return ((LocalDate) o).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));}

}
