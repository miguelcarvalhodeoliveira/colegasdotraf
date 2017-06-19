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
import model.Local;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

/**
 *
 * @author miguel
 */
public class LocalDAO implements Serializable {

    ResultSet rs;
    PreparedStatement pstm;
    Connection con;

    public Integer salvar(Local ev) {

        String sql = "insert into cgd.local (descricao,data,latitude,longitude) values (?,?,?,?)";
        pstm = null;
        con = null;
        rs = null;
        Integer chavePrimaria = null;
        ResultSet idGerada = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, ev.getDescricao());
            pstm.setTimestamp(2, new java.sql.Timestamp(ev.getData().getTime()));

            pstm.setDouble(3, ev.getLocal().getLatlng().getLat());
            pstm.setDouble(4, ev.getLocal().getLatlng().getLng());

            pstm.execute();
            idGerada = pstm.getGeneratedKeys();

            idGerada.next();
            pstm = null;
            chavePrimaria = idGerada.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return chavePrimaria;
    }

    public void alterar(Local ev) {

        String sql = "update cgd.local set descricao=?, data=?, latitude=?, longitude=? where idlocal=?";
        pstm = null;
        con = null;
        rs = null;

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, ev.getDescricao());
            pstm.setTimestamp(2, new java.sql.Timestamp(ev.getData().getTime()));
            pstm.setDouble(3, ev.getLocal().getLatlng().getLat());
            pstm.setDouble(4, ev.getLocal().getLatlng().getLng());
            pstm.setInt(5, ev.getId());
            pstm.execute();
            pstm = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public Local buscarPorId(Integer id) {
        Local l = new Local();
        con = null;
        pstm = null;
        rs = null;

        String sql = "select * from  cgd.local where  idlocal=?";
        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();

            if (rs.next()) {
                l.setId(rs.getInt("idlocal"));
                l.setData(rs.getTimestamp("data"));
                l.setDescricao(rs.getString("descricao"));
                l.setLocal(new Marker(new LatLng(rs.getDouble("latitude"), rs.getDouble("longitude"))));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return l;
    }

    public void apagarLocal(Integer idLocal) {

        String sql = "delete from cgd.local where idlocal=?";

        try (Connection conexao = FabricaDeConexao.getConnection();) {
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, idLocal);

            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }
}
