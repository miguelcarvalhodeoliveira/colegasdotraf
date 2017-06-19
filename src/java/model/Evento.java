/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MensagemDAO;
import controller.SolicitacoesDAO;
import controller.Usuario_has_EventoDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author miguel
 */
public class Evento implements Serializable {
private Integer id; 
    private List<Usuario> usuariosSolicitantes;
    private String descricao;
    private Local localDeEncontro=new Local();
    private Local localDoEvento=new Local();
    private Usuario administrador=new Usuario();
    private List<Usuario> partipantes;
    private List<Mensagem> mensagens;
    private Boolean privacidadeData ;
    private Boolean privacidadeLocalEncontro ;
    private Boolean privacidadeLocalEvento ;
    private Boolean privacidaMensagem ;
   MensagemDAO mdao= new MensagemDAO();
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Local getLocalDeEncontro() {
        return localDeEncontro;
    }

    public void setLocalDeEncontro(Local localDeEncontro) {
        this.localDeEncontro = localDeEncontro;
    }

    public Local getLocalDoEvento() {
        return localDoEvento;
    }

    public void setLocalDoEvento(Local localDoEvento) {
        this.localDoEvento = localDoEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Usuario> getUsuariosSolicitantes() {
        SolicitacoesDAO sdao=new SolicitacoesDAO();
        usuariosSolicitantes=sdao.listaUsuariosPorIdEvento(id);
        
        return usuariosSolicitantes;
    }

    public void setUsuariosSolicitantes(List<Usuario> usuariosSolicitantes) {
        this.usuariosSolicitantes = usuariosSolicitantes;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
    }

    public List<Usuario> getPartipantes() {
        partipantes= new Usuario_has_EventoDAO().listaUsuariosPorIdEvento(id);
        return partipantes;
    }

    public void setPartipantes(List<Usuario> partipantes) {
        this.partipantes = partipantes;
    }


    public List<Mensagem> getMensagens() {
        mensagens= mdao.listar(id);
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public Boolean getPrivacidadeData() {
        return privacidadeData;
    }

    public void setPrivacidadeData(Boolean privacidadeData) {
        this.privacidadeData = privacidadeData;
    }

    public Boolean getPrivacidadeLocalEncontro() {
        return privacidadeLocalEncontro;
    }

    public void setPrivacidadeLocalEncontro(Boolean privacidadeLocalEncontro) {
        this.privacidadeLocalEncontro = privacidadeLocalEncontro;
    }

    public Boolean getPrivacidadeLocalEvento() {
        return privacidadeLocalEvento;
    }

    public void setPrivacidadeLocalEvento(Boolean privacidadeLocalEvento) {
        this.privacidadeLocalEvento = privacidadeLocalEvento;
    }

    public Boolean getPrivacidaMensagem() {
        return privacidaMensagem;
    }

    public void setPrivacidaMensagem(Boolean privacidaMensagem) {
        this.privacidaMensagem = privacidaMensagem;
    }

    
}
