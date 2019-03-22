/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemainventario;

import BusinessLibrary.ProductosDALC;
import com.placeholder.PlaceHolder;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jovany
 */
public class JframeAgregar extends JFrame implements ActionListener {

    JTextField jtfNombre = new JTextField();
    JTextField jtfDepartamento = new JTextField();
    JTextField jtfCodigo = new JTextField();
    JTextField jtfCantidad = new JTextField();
    JTextField jtfPrecio = new JTextField();

    public JframeAgregar(JFrameInventario parent, String modo) throws SQLException {
        this.setLocation(805, 0);
        setSize(400, 600);
        agregarFrame();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void agregarFrame() {
        setTitle("Agregar producto");

        this.setLayout(new BorderLayout());

        JPanel borde = new JPanel(new FlowLayout());
        this.add(borde, BorderLayout.NORTH);
        borde = new JPanel(new FlowLayout());
        this.add(borde, BorderLayout.SOUTH);
        borde = new JPanel(new FlowLayout());
        this.add(borde, BorderLayout.WEST);
        borde = new JPanel(new FlowLayout());
        this.add(borde, BorderLayout.EAST);

        JPanel pnlCentral = new JPanel(new GridLayout(7, 0));

        PlaceHolder placeholder = new PlaceHolder(jtfNombre, "Nombre producto");
        pnlCentral.add(jtfNombre);

        placeholder = new PlaceHolder(jtfDepartamento, "Departamento");
        pnlCentral.add(jtfDepartamento);

        placeholder = new PlaceHolder(jtfCodigo, "Código de barras");
        pnlCentral.add(jtfCodigo);

        placeholder = new PlaceHolder(jtfCantidad, "Cantidad");
        pnlCentral.add(jtfCantidad);

        placeholder = new PlaceHolder(jtfPrecio, "Precio");
        pnlCentral.add(jtfPrecio);

        JButton button = new JButton("Agregar");
        button.addActionListener(this);
        pnlCentral.add(button);

        button = new JButton("Cancelar");
        button.addActionListener(this);
        pnlCentral.add(button);

        this.add(pnlCentral, BorderLayout.CENTER);

    }

    private void modificarFrame() {
        setTitle("Modificar producto");

        this.setLayout(new BorderLayout());

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String ac = ae.getActionCommand();

        ProductosDALC service = new ProductosDALC();

        switch (ac) {
            case "Agregar":
                boolean correcto = true;
                
                char[] texto = jtfCodigo.getText().toCharArray();
                for (int i = 0; i < texto.length; i++) {
                    char caracter = texto[i];
                    if (caracter < 48 || caracter > 57) {
                        correcto = false;
                    }
                }

                texto = jtfCantidad.getText().toCharArray();
                for (int i = 0; i < texto.length; i++) {
                    char caracter = texto[i];
                    if (caracter < 48 || caracter > 57) {
                        correcto = false;
                    }
                }

                texto = jtfPrecio.getText().toCharArray();
                for (int i = 0; i < texto.length; i++) {
                    char caracter = texto[i];
                    if (caracter < 48 || caracter > 57) {
                        correcto = false;
                    }
                }

                if (correcto) {
                    try {
                        // (nombre, departamento, codigo, cantidad, precio)
                        if (service.productoDisponible(jtfCodigo.getText())) {
                            ArrayList producto = new ArrayList();
                            producto.add(jtfNombre.getText());
                            producto.add(jtfDepartamento.getText());
                            producto.add(jtfCodigo.getText());
                            producto.add(jtfCantidad.getText());
                            producto.add(jtfPrecio.getText());
                            {
                                try {
                                    service.InsertarProducto(producto);
                                } catch (SQLException ex) {
                                    Logger.getLogger(JframeAgregar.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Ya existe un producto con el mismo código de barras");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(JframeAgregar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: Parámetros incorrectos");
                }
                break;
        }
    }

}
