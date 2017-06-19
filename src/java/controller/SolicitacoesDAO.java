/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Evento;
import model.Usuario;

/**
 *
 * @author miguel
 */
public class SolicitacoesDAO {

    ResultSet rs;
    PreparedStatement pstm;
    Connection con;

    public void salvar(Integer idUser, Integer idEvento) {
        String sql = "insert into cgd.solicitacoes (idusuario,idevento) values (?,?)";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idUser);
            pstm.setInt(2, idEvento);

            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    public List<Integer> listaIdUsuariosPorIdEvento(Integer idEvento) {
        List<Integer> listaIDUsuarios = new ArrayList<>();
        String sql = "select * from cgd.solicitacoes where idevento=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idEvento);

            rs = pstm.executeQuery();
            while (rs.next()) {
                listaIDUsuarios.add(rs.getInt("idusuario"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return listaIDUsuarios;
    }

    public List<Usuario> listaUsuariosPorIdEvento(Integer idEvento) {
        List<Usuario> listaDeUsuarios = new ArrayList<>();

        List<Integer> listaIDUsuarios = listaIdUsuariosPorIdEvento(idEvento);
        if (!listaIDUsuarios.isEmpty()) {
            for (Integer id : listaIDUsuarios) {
                listaDeUsuarios.add(new UsuarioDAO().buscarPorIdSoUsuario(id));
            }
        }
        return listaDeUsuarios;
    }

    public void apagarRelacao(Integer idEvento, Integer idUsuario) {
        String sql = "delete from cgd.solicitacoes where idevento=? and idusuario=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idEvento);
            pstm.setInt(2, idUsuario);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

}
