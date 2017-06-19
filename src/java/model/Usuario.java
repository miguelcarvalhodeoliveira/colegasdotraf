/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.EventoDAO;
import controller.Usuario_has_EventoDAO;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author miguel
 */

public class Usuario implements Serializable{

   
    private Integer id;
    private String nome;
    private String senha;
    private String email;
    private List<Evento> eventosQueSouProprietario;
    private List<Evento> eventosQueParticipo;

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Evento> getEventosQueSouProprietario() {
   if(id!=null){
        eventosQueSouProprietario=new EventoDAO().buscarPorIdProPrietario(id);
   }
        return eventosQueSouProprietario;
    }

    public void setEventosQueSouProprietario(List<Evento> eventosQueSouProprietario) {
        this.eventosQueSouProprietario = eventosQueSouProprietario;
    }

    public List<Evento> getEventosQueParticipo() {
    if(id!=null){
        eventosQueParticipo = new Usuario_has_EventoDAO().listarEventosPorUsuario(id);
    }
    return eventosQueParticipo;
    }

    public void setEventosQueParticipo(List<Evento> eventosQueParticipo) {
        this.eventosQueParticipo = eventosQueParticipo;
    }

}
