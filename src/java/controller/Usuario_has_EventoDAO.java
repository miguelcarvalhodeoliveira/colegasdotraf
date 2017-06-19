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
public class Usuario_has_EventoDAO implements Serializable {

    ResultSet rs;
    PreparedStatement pstm;
    Connection con;

    public void salvar(Integer idUser, Integer idEvento) {

        String sql = "insert into cgd.usuario_has_evento (usuario_idusuario,evento_idevento) values (?,?)";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idUser);
            pstm.setInt(2, idEvento);
            pstm.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    public List<Integer> listaDeEventosPorUsuario(Integer idUser) {
        List<Integer> listaDeIdsEvento = new ArrayList<>();
        String sql = "select * from cgd.usuario_has_evento where usuario_idusuario=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idUser);

            rs = pstm.executeQuery();
            System.err.println("salvou relação particpante");
            while (rs.next()) {
                listaDeIdsEvento.add(rs.getInt("evento_idevento"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return listaDeIdsEvento;
    }

    public List<Integer> listaIdUsuariosPorIdEvento(Integer idEvento) {
        List<Integer> listaIDUsuarios = new ArrayList<>();
        String sql = "select * from cgd.usuario_has_evento where evento_idevento=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idEvento);

            rs = pstm.executeQuery();
            while (rs.next()) {
                listaIDUsuarios.add(rs.getInt("usuario_idusuario"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

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

    public void apagarRelacao(Integer idEvento) {
        String sql = "delete from cgd.usuario_has_evento where evento_idevento=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idEvento);

            pstm.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void sairDoEvento(Integer idUser, Integer idEvento) {
            
        String sql = "delete from cgd.usuario_has_evento where usuario_idusuario=? and evento_idevento=?";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idUser);
            pstm.setInt(2, idEvento);

            pstm.execute();
            pstm.close();
            conexao.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    
    public void apagarEvento(Integer idEvento) {
        EventoDAO edao = new EventoDAO();
        List<Integer> listaIdsUsers=listaIdUsuariosPorIdEvento(idEvento);
        for (Integer idUser:listaIdsUsers) {
            sairDoEvento(idUser, idEvento);
        }
    }

    public void apagarUsuario(Integer id) {
        EventoDAO edao = new EventoDAO();
        for (Evento e : listarEventosPorUsuario(id)) {
            sairDoEvento(id, e.getId());
            edao.apagarEvento(e);
        }

    }

    public List<Evento> listarEventosPorUsuario(Integer idUser) {
        List<Evento> listaDeIdsEvento = new ArrayList<>();

        EventoDAO edao = new EventoDAO();
        String sql = "select * from cgd.usuario_has_evento where usuario_idusuario=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idUser);

            rs = pstm.executeQuery();
            while (rs.next()) {

                listaDeIdsEvento.add(edao.buscarPorId(rs.getInt("evento_idevento")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return listaDeIdsEvento;
    }

    public boolean eParticipanteDoEvento(Integer idUser, Integer idEvento) {
        boolean resposta = false;
        String sql = "select * from cgd.usuario_has_evento where usuario_idusuario=? and evento_idevento=?";
        con = null;
        pstm = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idUser);
            pstm.setInt(2, idEvento);

            rs = pstm.executeQuery();
            while (rs.next()) {
                resposta = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return resposta;
    }

}
