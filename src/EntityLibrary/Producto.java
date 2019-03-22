/*
 *******************************************************************************
 * PROGRAMACIÓN ORIENTADA A OBJETOS AVANZADA.
 * PROYECTO: SISTEMA DE CONTROL DE INVENTARIO.
 * ALUMNO: OMAR JOVANY HERNÁNDEZ SÁNCHEZ.
 * MATRÍCULA: 344187.
 *******************************************************************************
 */

package EntityLibrary;

import java.util.Arrays;
import java.util.Objects;

public class Producto implements Comparable<Producto>{

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    String nombre;
    double codigo;
    int cantidad;
    int precio;
    String departamento;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hashCode(this.codigo);
        hash = 67 * hash + Objects.hashCode(this.cantidad);
        hash = 67 * hash + Objects.hashCode(this.precio);
        hash = 67 * hash + Objects.hashCode(this.departamento);
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Producto){
            Producto producto = (Producto)obj;
            if(this.nombre==producto.nombre)
                return true;
            if(this.codigo==producto.codigo)
                return true;
        }
        return false;
    }

    @Override
    public int compareTo(Producto otroProducto) {
        if (this.codigo > otroProducto.codigo) {
            return 1;
        }

        if (this.codigo == otroProducto.codigo) {
            return 1;
        }

        return -1;
    }

}
