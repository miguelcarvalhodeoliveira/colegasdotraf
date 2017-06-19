/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.UsuarioDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Usuario;

/**
 *
 * @author miguel
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{
    private Usuario usuario=new Usuario();
    private String senha=new String("");
    private String senhaConfimada=new String("");
    private String senhaAlterada=new String("");
    private UsuarioDAO udao= new UsuarioDAO();

    public void alterarSenha(Integer idUser){
        usuario=udao.buscarPorIdSoUsuario(idUser);
        if(senha.equals(usuario.getSenha()) && senhaConfimada.equals(senhaAlterada)){
            usuario.setSenha(senhaAlterada);
            udao.alterar(usuario);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "alteração feita sucesso", " senha alterada"));

        }else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "senhas não coincidem", " verifique os dados fornecidos"));

        }
    }

    public String getSenhaAlterada() {
        return senhaAlterada;
    }

    public void setSenhaAlterada(String senhaAlterada) {
        this.senhaAlterada = senhaAlterada;
    }
    
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaConfimada() {
        return senhaConfimada;
    }

    public void setSenhaConfimada(String senhaConfimada) {
        this.senhaConfimada = senhaConfimada;
    }

    public UsuarioDAO getUdao() {
        return udao;
    }

    public void setUdao(UsuarioDAO udao) {
        this.udao = udao;
    }
    
    
    
}
