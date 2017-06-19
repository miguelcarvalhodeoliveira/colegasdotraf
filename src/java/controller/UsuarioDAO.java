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
import java.util.List;
import model.Evento;
import model.Usuario;

/**
 *
 * @author miguel
 */
public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    ResultSet rs;
    PreparedStatement pstm;
    Connection con;

    public void salvar(Usuario user) {

        String sql = "insert into cgd.usuario (nome,email,senha) values (?,?,?)";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, user.getNome());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getSenha());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    public void alterar(Usuario user) {
        con = null;
        pstm = null;
        rs = null;

        String sql = "update cgd.usuario set nome=?, email=? ,senha=?  where idusuario=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, user.getNome());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getSenha());
            pstm.setInt(4, user.getId());
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Usuario buscarPorIdSoUsuario(Integer id) {
        Usuario usuario = null;
        con = null;
        pstm = null;
        rs = null;

        String sql = "select * from  cgd.usuario where  idusuario=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(id);
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usuario;
    }

    public Usuario buscarPorId(Integer id) {
        Usuario usuario = buscarPorIdSoUsuario(id);
        Usuario_has_EventoDAO uedao = new Usuario_has_EventoDAO();
        EventoDAO edao = new EventoDAO();
        if (usuario != null) {
            List<Evento> idsEventos = uedao.listarEventosPorUsuario(id);
            if (!idsEventos.isEmpty()) {
                List<Evento> eventosQueParticipo = new ArrayList<>();
                for (Evento i : idsEventos) {
                    eventosQueParticipo.add(i);
                }
                usuario.setEventosQueParticipo(eventosQueParticipo);
            }
            List<Evento> eventosQueAdministro = edao.buscarPorIdProPrietario(usuario.getId());
            if (!eventosQueAdministro.isEmpty()) {
                usuario.setEventosQueSouProprietario(eventosQueAdministro);
            }

        }

        return usuario;
    }

    public void excluir(Integer id) {
        Usuario_has_EventoDAO ueDAO = new Usuario_has_EventoDAO();
        String sql = "delete from cgd.usuario where idusuario=?";
        ueDAO.apagarUsuario(id);

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    public Integer logar(String usuario, String senha) {
        Integer id = null;
        con = null;
        pstm = null;
        rs = null;

        String sql = "select * from cgd.usuario where email=? and senha=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, senha);
            rs = pstm.executeQuery();

            if (rs.next()) {
                id = rs.getInt("idusuario");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }
}
