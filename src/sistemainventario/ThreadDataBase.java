/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemainventario;

import BusinessLibrary.ProductosDALC;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jovany
 */
public class ThreadDataBase extends Thread {

    DefaultTableModel modeloTabla = null;
    ArrayList<Object[]> arrayData = null;
    int num_registros;

    public ThreadDataBase(DefaultTableModel model) {
        this.modeloTabla = model;
        num_registros = model.getRowCount();
    }

    @Override
    public void run() {
        ProductosDALC productosDALC = new ProductosDALC();
        while (true) {
            try {

                arrayData = productosDALC.MostrarProductos();
            } catch (SQLException ex) {
                Logger.getLogger(ThreadDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (arrayData != null) {

                if (arrayData.size() != num_registros) {
                    if (modeloTabla.getRowCount() > 0) {
                        int limit = modeloTabla.getRowCount()-1;
                        for (int i = limit; i>=0; i--) {
                            modeloTabla.removeRow(i);
                        }
                    }

                    for (Object[] objects : arrayData) {
                        modeloTabla.addRow(objects);
                    }
                    num_registros = arrayData.size();
                }
            }
        }
    }
}
