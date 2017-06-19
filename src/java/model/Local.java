/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import org.primefaces.model.map.Marker;

/**
 *
 * @author miguel
 */
public class Local implements Serializable{
    private Integer id;
    private LocalDateTime data;
    private Marker local;
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Marker getLocal() {
        return local;
    }

    public void setLocal(Marker local) {
        this.local = local;
    }

    public Date getData() {
              if(data==null){
            data=LocalDateTime.now();
        }
        return new Date(data.getYear()-1900, data.getMonthValue()-1, data.getDayOfMonth(), data.getHour(), data.getMinute());
  
        
    }

    public void setData(Date data) {
      int ajuste=0;
        if(data.getYear()<200){
            ajuste=1900;
        }
        this.data =LocalDateTime.of( LocalDate.of(data.getYear()+ajuste, data.getMonth()+1, data.getDate()),
                    LocalTime.of(data.getHours(), data.getMinutes()));
      
    
    }

}
