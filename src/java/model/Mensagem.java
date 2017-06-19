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

/**
 *
 * @author miguel
 */
public class Mensagem implements Serializable{
private Integer idMensagem;
    
    private Usuario usuario=new Usuario();
private String conteudo;
    private Evento evento=new Evento();
    private LocalDateTime data;
    public Mensagem() {
    }

    public Mensagem(Usuario usuario, String conteudo) {
        this.usuario = usuario;
        this.conteudo = conteudo;
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

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Integer idMensagem) {
        this.idMensagem = idMensagem;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

}
