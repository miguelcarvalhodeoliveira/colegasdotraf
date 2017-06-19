/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author miguel
 */
public class Administrador implements Serializable{
    private Integer idAdministrador;
    
    private Usuario usuario=new Usuario();

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
