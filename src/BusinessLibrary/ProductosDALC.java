/*
 *******************************************************************************

 * PROGRAMACIÓN ORIENTADA A OBJETOS AVANZADA.
 * PROYECTO: SISTEMA DE CONTROL DE INVENTARIO.

 * INTEGRANTES: 
    OMAR JOVANY HERNÁNDEZ SÁNCHEZ (344187).
    DANIEL MARTINEZ TORRES (33850).

 *******************************************************************************
 */

package BusinessLibrary;

import DataAccess.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jovany
 */
public class ProductosDALC{
    Conexion conexion = null;

    public ProductosDALC() {
        conexion = new Conexion();
    }
    
    public ArrayList<Object[]> MostrarProductos() throws SQLException{
        String query = "SELECT * FROM PRODUCTOS";
        ArrayList<Object[]> arrayData = new ArrayList();
        Statement stmt = null;
        ResultSet rs  = null;
        try {
            stmt = conexion.getConexion().createStatement();
            rs  = stmt.executeQuery(query);
            
            for(int i=0; rs.next(); i++){
                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                Object[] row = { 
                                 rs.getString("id"),
                                 rs.getString("nombre"), 
                                 rs.getString("departamento"), 
                                 rs.getString("codigo"),
                                 rs.getString("cantidad"),
                                 rs.getString("precio")
                               };
                arrayData.add(row);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(stmt!=null)
                stmt.close();
            if(rs!=null)
                rs.close();
        }
        return arrayData;
    }
    
       public boolean productoDisponible(String codigo) throws SQLException{
        boolean success = true;
        String query = "SELECT * FROM PRODUCTOS WHERE codigo='"+codigo+"'";
        Statement stmt = null;
        ResultSet rs  = null;
        try {
            stmt = conexion.getConexion().createStatement();
            rs  = stmt.executeQuery(query);
           
            for(int i=0; rs.next(); i++){
                success=false;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(stmt!=null)
                stmt.close();
            if(rs!=null)
                rs.close();
        }
        return success;
    }
    
    public boolean InsertarProducto(ArrayList producto) throws SQLException
    {
        boolean success = false;
        PreparedStatement result = null;
        String query = "INSERT INTO PRODUCTOS (nombre, departamento, codigo, cantidad, precio) "
                + "VALUES "
                + "('"+producto.get(0)+"','" +producto.get(1)+"','"+ producto.get(2)+"','"+ producto.get(3)+"','"+  producto.get(4)+"')";
        
        try (Statement stmt = conexion.getConexion().createStatement()) {
            result = (PreparedStatement)stmt.executeQuery(query);
            if(result.getUpdateCount()>0)
                success=true;     
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(result!=null)
                result.close();
        }
        return success;
    }
    
    // Actualiza
    public boolean UpdateProducto(ArrayList producto) throws SQLException{
        boolean success = false;
        PreparedStatement result = null;
        
        String query = String.format("UPDATE PRODUCTOS SET nombre = {0}, departamento = {1}, codigo = {2}, cantidad = {3}, precio = {4} "
                + "WHERE codigo= {5}", producto.get(0), producto.get(1), producto.get(2), producto.get(3), producto.get(4), producto.get(2));
            
        try (Statement stmt = conexion.getConexion().createStatement()) {
            result = (PreparedStatement) stmt.executeQuery(query);
            if(result.getUpdateCount()>0)
                success=true;
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(result!=null)
                result.close();
        }
        return success;
    }
    
    public boolean UpdateProducto(int cantidad, int id) throws SQLException{
        boolean success = false;
        PreparedStatement result = null;
        
        String query = "UPDATE PRODUCTOS SET cantidad ='"+cantidad+"' WHERE id='"+id+"'";
            
        try (Statement stmt = conexion.getConexion().createStatement()) {
            result = (PreparedStatement) stmt.executeQuery(query);
            if(result.getUpdateCount()>0)
                success=true;
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(result!=null)
                result.close();
        }
        return success;
    }
    
     // Actualiza
    public boolean EliminaProducto(int id) throws SQLException{
        boolean success = false;
        PreparedStatement result = null;
        
        String query = "DELETE FROM PRODUCTOS WHERE id="+id;
            
        try (Statement stmt = conexion.getConexion().createStatement()) {
            result = (PreparedStatement) stmt.executeQuery(query);
            if(result.getUpdateCount()>0)
                success=true;
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            if(result!=null)
                result.close();
        }
        return success;
    }
}
