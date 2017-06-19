/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EventoDAO;
import controller.MensagemDAO;
import controller.SolicitacoesDAO;
import controller.UsuarioDAO;
import controller.Usuario_has_EventoDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import model.Evento;
import model.Mensagem;
import model.Usuario;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class EventoBean implements Serializable{
    private MapModel emptyModel;
    private Mensagem mensagem=  new Mensagem();
    private MensagemDAO mdao= new MensagemDAO();
    private String texto= new String();
    private UsuarioDAO udao = new UsuarioDAO();
    private EventoDAO edao = new EventoDAO();
    private Usuario_has_EventoDAO uedao = new Usuario_has_EventoDAO();
    private SolicitacoesDAO sdao=new SolicitacoesDAO();
    private Evento evento = new Evento();
    private HtmlInputHidden hidenid = new HtmlInputHidden();
    public Boolean participante;
    
    public void sair(Integer idEvento){
    Integer idUser=(Integer) hidenid.getValue();
        uedao.sairDoEvento(idUser, idEvento);
    }
    public void solicitarParticipacao(Integer idUser){
       participante=true;
        sdao.salvar(idUser, evento.getId());
        FacesContext.getCurrentInstance().addMessage("messagesPagEvent", new FacesMessage("solicitação feita com sucesso"));

    }
    public void excluirUsuario(Integer id){
        uedao.sairDoEvento(id, evento.getId());
    }
    public void excluir(Integer idEvento){
    evento=edao.buscarPorId(idEvento);
        edao.apagarEvento(evento);
        evento= new Evento();
    }
    public String verSolicitacoes(Integer idEvento){
    evento=edao.buscarPorId(idEvento);
        
    return "solicitacoes?faces-redirect=true";}
    
    public void excluirComentario(Integer idMensagem){
         mdao.excluir_mensagem(idMensagem);
        
    }
    public String visualizarListaDeParticipantes(){
        
    return "listaDeParticipantes?faces-redirect=true";}
    public void aceitarSolictacao(Integer idUser){
        Integer id=idUser;
        sdao.apagarRelacao(evento.getId(), id);
        uedao.salvar( id,evento.getId());
    }
    public void negarSolictacao(Integer idUser){
        Integer id=idUser;
        sdao.apagarRelacao(evento.getId(), id);
    }
    
    public Mensagem getMensagem() {

        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public HtmlInputHidden getHidenid() {
        return hidenid;
    }

    public void setHidenid(HtmlInputHidden hidenid) {
        this.hidenid = hidenid;
    }

    public MapModel getEmptyModel() {
    if(emptyModel==null){
        emptyModel= new DefaultMapModel();
    }
        evento.getLocalDeEncontro().getLocal().setTitle(evento.getLocalDeEncontro().getDescricao());
               evento.getLocalDoEvento().getLocal().setTitle(evento.getLocalDoEvento().getDescricao());
               emptyModel.addOverlay(evento.getLocalDeEncontro().getLocal());
               emptyModel.addOverlay(evento.getLocalDoEvento().getLocal());

        return emptyModel;
    }

    public Boolean getParticipante() {
    Integer idUser=(Integer) hidenid.getValue();
        participante=uedao.eParticipanteDoEvento(idUser, evento.getId());
   
        return participante;
    }

    public void seteParticipante(Boolean participante) {
        this.participante = participante;
    }
    
    
    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    public List<Evento> evePartUsuario() {
        Integer id = (Integer) hidenid.getValue();
        return uedao.listarEventosPorUsuario(id);
    }

    public List<Evento> evePropUsuario() {
        Integer id = (Integer) hidenid.getValue();
        return edao.buscarPorIdProPrietario(id);
    }

    public String visualizar(Integer id) {
        evento = edao.buscarPorId(id);
        return "paginaDoEvento?faces-redirect=true";
    }
    public void publicarMensagem(){
        
        Integer idUser=(Integer) hidenid.getValue();
        Usuario u= new Usuario();
        u.setId(idUser);
        mensagem.setEvento(evento);
        Date dataAtual= new Date(System.currentTimeMillis());
        mensagem.setData(dataAtual);
        mensagem.setUsuario(u);
        mensagem.setConteudo(texto);
        mdao.salvar(mensagem);
    }
}
