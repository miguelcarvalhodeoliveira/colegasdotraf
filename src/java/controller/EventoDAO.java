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

/**
 *
 * @author miguel
 */
public class EventoDAO implements Serializable {

    ResultSet rs;
    PreparedStatement pstm;
    Connection con;
    //retorna id para cadastrar na tabela controladora

    public Integer salvar(Evento ev) {
        pstm = null;
        con = null;
        rs = null;
        Integer chavePrimaria = null;
        ResultSet idGerada = null;
        Usuario_has_EventoDAO ueDAO = new Usuario_has_EventoDAO();
        LocalDAO ldao = new LocalDAO();
        Integer idLEncontro = ldao.salvar(ev.getLocalDeEncontro());
        Integer idLEvento = ldao.salvar(ev.getLocalDoEvento());

        ev.getLocalDeEncontro().setId(idLEncontro);
        ev.getLocalDoEvento().setId(idLEvento);
        String sql = "insert into cgd.evento (descricao,localDeEncontro,"
                + "localDeEvento,administrador,privacidadeData,privacidadeLocalDeEncontro"
                + ",privacidadeLocalDeEvento,privacidadeMensagens) values (?,?,?,?,?,?,?,?)";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, ev.getDescricao());
            pstm.setInt(2, ev.getLocalDeEncontro().getId());
            pstm.setInt(3, ev.getLocalDoEvento().getId());
            pstm.setInt(4, ev.getAdministrador().getId());
            pstm.setBoolean(5, ev.getPrivacidadeData());
            pstm.setBoolean(6, ev.getPrivacidadeLocalEncontro());
            pstm.setBoolean(7, ev.getPrivacidadeLocalEvento());
            pstm.setBoolean(8, ev.getPrivacidaMensagem());
            pstm.execute();
            idGerada = pstm.getGeneratedKeys();
            idGerada.next();
            pstm = null;

            chavePrimaria = idGerada.getInt(1);

            ueDAO.salvar(ev.getAdministrador().getId(), chavePrimaria);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return chavePrimaria;
    }

    public void alterar(Evento ev) {
        con = null;
        pstm = null;
        rs = null;

        String sql = "update cgd.evento set descricao=?, localDeEncontro=?, localDeEvento=?, administrador=? "
                + ",privacidadeData =?, privacidadeLocalDeEncontro =?"
                + ", privacidadeLocalDeEvento=?, privacidadeMensagens=? where idevento=?";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, ev.getDescricao());
            pstm.setInt(2, ev.getLocalDeEncontro().getId());
            pstm.setInt(3, ev.getLocalDoEvento().getId());
            pstm.setInt(4, ev.getAdministrador().getId());
            pstm.setBoolean(5, ev.getPrivacidadeData());
            pstm.setBoolean(6, ev.getPrivacidadeLocalEncontro());
            pstm.setBoolean(7, ev.getPrivacidadeLocalEvento());
            pstm.setBoolean(8, ev.getPrivacidaMensagem());
            pstm.setInt(9, ev.getId());
            pstm.execute();
            pstm = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public Evento buscarPorId(Integer id) {
        Evento e = null;
        LocalDAO ldao = new LocalDAO();
        UsuarioDAO udao = new UsuarioDAO();
        con = null;
        pstm = null;
        rs = null;
        MensagemDAO mdao = new MensagemDAO();
        String sql = "select * from cgd.evento where idevento=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                e = new Evento();
                e.setId(rs.getInt("idevento"));
                e.getAdministrador().setId(rs.getInt("administrador"));
                e.setDescricao(rs.getString("descricao"));
                e.getLocalDeEncontro().setId(rs.getInt("localDeEncontro"));
                e.getLocalDoEvento().setId(rs.getInt("localDeEvento"));
                e.setPrivacidadeData(rs.getBoolean("privacidadeData"));
                e.setPrivacidadeLocalEncontro(rs.getBoolean("privacidadeLocalDeEncontro"));
                e.setPrivacidadeLocalEvento(rs.getBoolean("privacidadeLocalDeEvento"));
                e.setPrivacidaMensagem(rs.getBoolean("privacidadeMensagens"));
                e.setMensagens(mdao.listar(e.getId()));
                 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (e.getAdministrador().getId() != null) {
            e.setAdministrador(udao.buscarPorIdSoUsuario(e.getAdministrador().getId()));
        }
        if (null != e.getLocalDeEncontro().getId()) {
            e.setLocalDeEncontro(ldao.buscarPorId(e.getLocalDeEncontro().getId()));
        }
        if (null != e.getLocalDoEvento().getId()) {
            e.setLocalDoEvento(ldao.buscarPorId(e.getLocalDoEvento().getId()));
        }

        return e;
    }
    public Evento buscarPorIdSoEvento(Integer id) {
        Evento e = null;
        LocalDAO ldao = new LocalDAO();
        UsuarioDAO udao = new UsuarioDAO();
        con = null;
        pstm = null;
        rs = null;
        String sql = "select * from cgd.evento where idevento=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                e = new Evento();
                e.setId(rs.getInt("idevento"));
                e.getAdministrador().setId(rs.getInt("administrador"));
                e.setDescricao(rs.getString("descricao"));
                e.getLocalDeEncontro().setId(rs.getInt("localDeEncontro"));
                e.getLocalDoEvento().setId(rs.getInt("localDeEvento"));
                e.setPrivacidadeData(rs.getBoolean("privacidadeData"));
                e.setPrivacidadeLocalEncontro(rs.getBoolean("privacidadeLocalDeEncontro"));
                e.setPrivacidadeLocalEvento(rs.getBoolean("privacidadeLocalDeEvento"));
                e.setPrivacidaMensagem(rs.getBoolean("privacidadeMensagens"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    public List<Evento> buscarPorIdProPrietario(Integer id) {
        MensagemDAO mdao = new MensagemDAO();
        List<Evento> lista = new ArrayList<>();
        LocalDAO ldao = new LocalDAO();
        UsuarioDAO udao = new UsuarioDAO();
        con = null;
        pstm = null;
        rs = null;

        String sql = "select * from cgd.evento where administrador=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("idevento"));
                e.getAdministrador().setId(rs.getInt("administrador"));
                e.setDescricao(rs.getString("descricao"));
                e.getLocalDeEncontro().setId(rs.getInt("localDeEncontro"));
                e.getLocalDoEvento().setId(rs.getInt("localDeEvento"));
                e.setPrivacidadeData(rs.getBoolean("privacidadeData"));
                e.setPrivacidadeLocalEncontro(rs.getBoolean("privacidadeLocalDeEncontro"));
                e.setPrivacidadeLocalEvento(rs.getBoolean("privacidadeLocalDeEvento"));
                e.setPrivacidaMensagem(rs.getBoolean("privacidadeMensagens"));
                e.setMensagens(mdao.listar(e.getId()));
                if (e.getAdministrador().getId() != null) {
                    e.setAdministrador(udao.buscarPorIdSoUsuario(e.getAdministrador().getId()));
                }
                if ( e.getLocalDeEncontro().getId()!=null) {
                    e.setLocalDeEncontro(ldao.buscarPorId(e.getLocalDeEncontro().getId()));
                }
                if (e.getLocalDoEvento().getId() != null) {
                    e.setLocalDoEvento(ldao.buscarPorId(e.getLocalDoEvento().getId()));
                }
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }
public List<Evento> buscarPorNomeEvento(String nome) {
        MensagemDAO mdao = new MensagemDAO();
        List<Evento> lista = new ArrayList<>();
        LocalDAO ldao = new LocalDAO();
        UsuarioDAO udao = new UsuarioDAO();
        con = null;
        pstm = null;
        rs = null;

        String sql = "select * from cgd.evento where descricao like ?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, "%"+nome+"%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("idevento"));
                e.getAdministrador().setId(rs.getInt("administrador"));
                e.setDescricao(rs.getString("descricao"));
                e.getLocalDeEncontro().setId(rs.getInt("localDeEncontro"));
                e.getLocalDoEvento().setId(rs.getInt("localDeEvento"));
                e.setPrivacidadeData(rs.getBoolean("privacidadeData"));
                e.setPrivacidadeLocalEncontro(rs.getBoolean("privacidadeLocalDeEncontro"));
                e.setPrivacidadeLocalEvento(rs.getBoolean("privacidadeLocalDeEvento"));
                e.setPrivacidaMensagem(rs.getBoolean("privacidadeMensagens"));
                e.setMensagens(mdao.listar(e.getId()));
                if (e.getAdministrador().getId() != null) {
                    e.setAdministrador(udao.buscarPorIdSoUsuario(e.getAdministrador().getId()));
                }
                if ( e.getLocalDeEncontro().getId()!=null) {
                    e.setLocalDeEncontro(ldao.buscarPorId(e.getLocalDeEncontro().getId()));
                }
                if (e.getLocalDoEvento().getId() != null) {
                    e.setLocalDoEvento(ldao.buscarPorId(e.getLocalDoEvento().getId()));
                }
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public void apagarEvento(Evento evento) {
        LocalDAO ldao = new LocalDAO();
        MensagemDAO mdao = new MensagemDAO();
        Usuario_has_EventoDAO uedao = new Usuario_has_EventoDAO();
        uedao.apagarEvento(evento.getId());
        String sql = "delete from cgd.evento where idevento=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, evento.getId());

            pstm.execute();
            pstm.close();
            conexao.close();
            ldao.apagarLocal(evento.getLocalDeEncontro().getId());
            ldao.apagarLocal(evento.getLocalDoEvento().getId());
            mdao.excluir_mensagens(evento.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

}
