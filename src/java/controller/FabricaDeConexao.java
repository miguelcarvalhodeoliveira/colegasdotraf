/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class FabricaDeConexao {
    
    public static Connection getConnection(){
       Connection con=null;
        try{
           try {
               Class.forName("com.mysql.jdbc.Driver");
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(FabricaDeConexao.class.getName()).log(Level.SEVERE, null, ex);
           }
          con= DriverManager.getConnection("jdbc:mysql://localhost:3306/cgd", "root","1234");
        }catch(SQLException ex){
            ex.printStackTrace();;
            System.err.println("errrrrrrrrooooooooooo"+ex.getMessage());
        }
    return con;}
    
}
