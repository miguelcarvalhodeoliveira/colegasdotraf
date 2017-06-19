/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EventoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Evento;

/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class BuscaEventoBean implements Serializable{
   
   String termoBusca= new String("");
   List<Evento> lista=new ArrayList<>();

    public String getTermoBusca() {
        return termoBusca;
    }

    public void setTermoBusca(String termoBusca) {
        this.termoBusca = termoBusca;
    }

    public List<Evento> getLista() {
        return lista;
    }

    public void setLista(List<Evento> lista) {
        this.lista = lista;
    }
    public String buscar(){
        EventoDAO edao= new EventoDAO();
    String retorno="telaInicialUsuario";
    if(!termoBusca.isEmpty()){
        lista.clear();
        lista=edao.buscarPorNomeEvento(termoBusca);
        if(!lista.isEmpty()){
            retorno="paginaDeBusca";
        }
    }
    
    
    
        retorno+="?faces-redirect=true";
    return retorno;}
   
}
