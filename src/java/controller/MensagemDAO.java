/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Mensagem;

/**
 *
 * @author miguel
 */
public class MensagemDAO implements Serializable {

    ResultSet rs;
    PreparedStatement pstm;
    Connection con;

    public void salvar(Mensagem msn) {
        String sql = "insert into cgd.mensagem (mensagem,data,idusuario,evento) values (?,?,?,?)";
        pstm = null;
        con = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, msn.getConteudo());
            pstm.setTimestamp(2, new java.sql.Timestamp(msn.getData().getTime()));
            pstm.setInt(3, msn.getUsuario().getId());
            pstm.setInt(4, msn.getEvento().getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    public List<Mensagem> listar(Integer idevento) {
        List<Mensagem> mensagens = new ArrayList<>();
        con = null;
        pstm = null;
        rs = null;
        EventoDAO edao = new EventoDAO();
        UsuarioDAO udao = new UsuarioDAO();

        String sql = "select * from  cgd.mensagem where evento=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idevento);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Mensagem msn = new Mensagem();
                msn.setIdMensagem(rs.getInt("idmensagem"));
                msn.setConteudo(rs.getString("mensagem"));
                msn.setData(rs.getTimestamp("data"));
                msn.setEvento(edao.buscarPorIdSoEvento(idevento));
                msn.setUsuario(udao.buscarPorIdSoUsuario(rs.getInt("idusuario")));
                mensagens.add(msn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return mensagens;
    }

    public void excluir_mensagens(Integer idevento) {
        con = null;
        pstm = null;
        rs = null;
        String sql = "delete from cgd.mensagem where evento=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idevento);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void excluir_mensagem(Integer idmensagem) {
        con = null;
        pstm = null;
        rs = null;

        String sql = "delete from cgd.mensagem where idmensagem=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idmensagem);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
