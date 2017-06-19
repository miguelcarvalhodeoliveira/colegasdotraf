/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EventoDAO;
import controller.UsuarioDAO;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import model.Evento;
import model.Usuario;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author miguel
 */
@ManagedBean
@ViewScoped
public class NovoEventoBean implements Serializable {

    Evento evento = new Evento();
    private EventoDAO edao = new EventoDAO();
    private MapModel emptyModel;
    private Marker markerAntEncontro;
    private Marker markerAntEvento;
    private String title;
    UsuarioDAO udao = new UsuarioDAO();
    Usuario user = new Usuario();
    private double lat;
    private boolean editarMapa;
    private boolean marcarEncontro;
    private boolean marcarEvento;

    private double lng;
    private Date data = new Date();
    private HtmlInputHidden hidenid = new HtmlInputHidden();

    private HtmlInputHidden hidenLat = new HtmlInputHidden();
    private HtmlInputHidden hidenLgn = new HtmlInputHidden();
    private HtmlInputText txDescricao = new HtmlInputText();

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public void salvarEvento() {
        Integer iduser = (Integer) hidenid.getValue();
        evento.getAdministrador().setId(iduser);
        
        edao.salvar(evento);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "evento salvo com sucesso", "data da realização " + evento.getLocalDoEvento().getData().toString()));

    }

    public void marcarLocalDeEncontro() {
        editarMapa = true;
        marcarEncontro = true;
        marcarEvento = false;
    }

    public void marcarLocalDoEvento() {
        editarMapa = true;
        marcarEncontro = false;
        marcarEvento = true;
    }

    public HtmlInputHidden getHidenid() {
        return hidenid;
    }

    public void setHidenid(HtmlInputHidden hidenid) {
        this.hidenid = hidenid;
    }

    public boolean isEditarMapa() {
        return editarMapa;
    }

    public void setEditarMapa(boolean editarMapa) {
        this.editarMapa = editarMapa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void addMarker() {

        Marker marker = new Marker(new LatLng(lat, lng), title);
        if (marcarEncontro && markerAntEncontro == null) {
            evento.getLocalDeEncontro().setLocal(marker);
            evento.getLocalDeEncontro().setData(data);
            evento.getLocalDeEncontro().setDescricao(title);

            markerAntEncontro = marker;
        }
        if (marcarEncontro && markerAntEncontro != null) {
            evento.getLocalDeEncontro().setLocal(marker);
            evento.getLocalDeEncontro().setData(data);
            evento.getLocalDeEncontro().setDescricao(title);
            emptyModel.getMarkers().remove(markerAntEncontro);

            markerAntEncontro = marker;
        }

        if (marcarEvento && markerAntEvento == null) {
            evento.getLocalDoEvento().setLocal(marker);
            evento.getLocalDoEvento().setData(data);
            evento.getLocalDoEvento().setDescricao(title);
            markerAntEvento = marker;
        }
        if (marcarEvento && markerAntEvento == null) {
            evento.getLocalDoEvento().setLocal(marker);
            evento.getLocalDoEvento().setData(data);
            evento.getLocalDoEvento().setDescricao(title);
            emptyModel.getMarkers().remove(markerAntEvento);

            markerAntEvento = marker;
        }

        emptyModel.addOverlay(marker);
        editarMapa = false;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "local salvo com sucesso", "Latitude:" + lat + ", Longitude:" + lng));
    }

    //GETTERS E SETTERS
    public HtmlInputText getTxDescricao() {
        return txDescricao;
    }

    public void setTxDescricao(HtmlInputText txDescricao) {
        this.txDescricao = txDescricao;
    }

    public HtmlInputHidden getHidenLat() {
        return hidenLat;
    }

    public void setHidenLat(HtmlInputHidden hidenLat) {
        this.hidenLat = hidenLat;
    }

    public HtmlInputHidden getHidenLgn() {
        return hidenLgn;
    }

    public void setHidenLgn(HtmlInputHidden hidenLgn) {
        this.hidenLgn = hidenLgn;
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;

    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
