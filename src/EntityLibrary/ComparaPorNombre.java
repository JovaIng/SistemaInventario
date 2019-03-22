/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityLibrary;

import java.util.Comparator;

/**
 *
 * @author Jovany
 */
public class ComparaPorNombre implements Comparator<Producto> {

    public int compare(Producto producto1, Producto producto2) {
        return producto1.getNombre().compareTo(producto2.getNombre());
    }
}
