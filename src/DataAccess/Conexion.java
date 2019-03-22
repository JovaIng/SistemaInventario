/*
 *******************************************************************************

 * PROGRAMACIÓN ORIENTADA A OBJETOS AVANZADA.
 * PROYECTO: SISTEMA DE CONTROL DE INVENTARIO.

 * INTEGRANTES: 
    OMAR JOVANY HERNÁNDEZ SÁNCHEZ (344187).
    DANIEL MARTINEZ TORRES (33850).

 *******************************************************************************
 */

package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jovany
 */
public class Conexion {
    private Connection conexion = null;
    private String connectionUrl;

    public Conexion() {
        connectionUrl = "jdbc:sqlserver://localhost;databaseName=SistemaInventario;instanceName=SQLEXPRESS; integratedSecurity=true;";
        // Data Source="
        tryConnection();
    }
    
    private final boolean tryConnection(){
        
        try {
            conexion = DriverManager.getConnection(connectionUrl);
            System.out.println("Conectado!");
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error.");
        }
        return false;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }
    
    
}
