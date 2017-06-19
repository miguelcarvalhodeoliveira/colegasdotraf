/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.UsuarioDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Evento;
import model.Usuario;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

    private String termoDeBusca;
    private Usuario usuarioLogado = new Usuario();
    private String usuario;
    private String senha;
    private UsuarioDAO udao = new UsuarioDAO();
    private Usuario cad = new Usuario();
    private String confSenha;
    private MapModel emptyModel;

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public MapModel getEmptyModel() {
        List<Evento> lista = usuarioLogado.getEventosQueParticipo();
        if (!lista.isEmpty()) {
            for (Evento e : lista) {
                e.getLocalDeEncontro().getLocal().setTitle(e.getLocalDeEncontro().getDescricao());
                e.getLocalDoEvento().getLocal().setTitle(e.getLocalDoEvento().getDescricao());
                emptyModel.addOverlay(e.getLocalDeEncontro().getLocal());
                emptyModel.addOverlay(e.getLocalDoEvento().getLocal());
            }
        }
        return emptyModel;
    }

    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    public String getTermoDeBusca() {
        return termoDeBusca;
    }

    public void setTermoDeBusca(String termoDeBusca) {
        this.termoDeBusca = termoDeBusca;
    }

    public String getUsuario() {
        return usuario;
    }

    public void salvar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msn;
        String mensagem = "todos os campos são obrigatorios";
        if (cad.getNome() != null && cad.getEmail() != null && cad.getSenha() != null) {
            if (!cad.getNome().isEmpty() && !cad.getEmail().isEmpty() && !cad.getSenha().isEmpty()) {
                if (cad.getSenha().equals(confSenha)) {
                    udao.salvar(cad);
                    mensagem = "cadastro realizado com sucesso";
                } else {
                    mensagem = "confirmação de senha incorreta";
                }
            } else {
                mensagem = "todos os campos são obrigatorios";
                //mensagem quee falta preencher todos os campos
            }
        }
        msn = new FacesMessage(mensagem);
        fc.addMessage("sucessoCadastro", msn);
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public Usuario getCad() {
        return cad;
    }

    public void setCad(Usuario cad) {
        this.cad = cad;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String logar() {
        Integer idusuario = udao.logar(usuario, senha);
        if (idusuario != null) {
            usuarioLogado = udao.buscarPorId(idusuario);
            //   ExternalContextFactory factory = (ExternalContextFactory)
            // FactoryFinder.getFactory(FactoryFinder.EXTERNAL_CONTEXT_FACTORY);
            //   ExternalContext externalContext=factory.getExternalContext(FacesContext.getCurrentInstance(), this, );
            return "telaInicialUsuario?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage("sucessoCdastro", new FacesMessage("usuario e/ou senha incorreto(s)"));

            return "login?faces-redirect=true";
        }
    }
}
