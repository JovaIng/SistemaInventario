/*
 *******************************************************************************

 * PROGRAMACIÓN ORIENTADA A OBJETOS AVANZADA.
 * PROYECTO: PROGRAMA DE CONTROL DE INVENTARIO.

 * INTEGRANTES: 
    OMAR JOVANY HERNÁNDEZ SÁNCHEZ (344187).
    DANIEL MARTINEZ TORRES (33850).

 *******************************************************************************
 */
package sistemainventario;

import BusinessLibrary.ProductosDALC;
import EntityLibrary.ComparaPorCantidad;
import EntityLibrary.ComparaPorDepartamento;
import EntityLibrary.ComparaPorId;
import EntityLibrary.ComparaPorNombre;
import EntityLibrary.ComparaPorPrecio;
import EntityLibrary.Producto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jovany
 */
public class JFrameInventario extends JFrame implements ActionListener {

    DefaultTableModel modeloTabla = null;
    JTable tblProductos = null;
    JTextField jtfCantidad = new JTextField();

    public JFrameInventario() throws SQLException {
        super("Sistema de inventario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        modeloTabla = new DefaultTableModel();
        initComponents();
        setVisible(true);
    }

    private void initComponents() throws SQLException {

        this.setLayout(new BorderLayout());

        JPanel panel_menubar = new JPanel(new FlowLayout());
        panel_menubar.setBackground(Color.GRAY);
        JMenuBar menubar = new JMenuBar();
        JMenuItem item = new JMenuItem("-");
        item.addActionListener(this);
        menubar.add(item);
        item = new JMenuItem("+");
        item.addActionListener(this);
        menubar.add(item);

        jtfCantidad.addActionListener(this);
        jtfCantidad.setText("1");
        menubar.add(jtfCantidad);

        panel_menubar.add(menubar);

        this.add(panel_menubar, BorderLayout.NORTH);

        JPanel panel_central = new JPanel(new BorderLayout());
        JPanel cabecera = new JPanel(new GridLayout(1, 5));
        JButton ca = new JButton("");
        ca.setFocusable(false);
        ca.addActionListener(this);
        cabecera.add(ca);
        ca = new JButton("NOMBRE");
        ca.setFocusable(false);
        ca.addActionListener(this);
        cabecera.add(ca);
        ca = new JButton("DEPARTAMENTO");
        ca.setFocusable(false);
        ca.addActionListener(this);
        cabecera.add(ca);
        ca = new JButton("CÓDIGO");
        ca.setFocusable(false);
        ca.addActionListener(this);
        cabecera.add(ca);
        ca = new JButton("CANTIDAD");
        ca.setFocusable(false);
        ca.addActionListener(this);
        cabecera.add(ca);
        ca = new JButton("PRECIO");
        ca.setFocusable(false);
        ca.addActionListener(this);
        cabecera.add(ca);
        JPanel cabecera_borde = new JPanel(new BorderLayout());
        cabecera_borde.add(new JPanel(), BorderLayout.EAST);
        cabecera_borde.add(new JPanel(), BorderLayout.WEST);
        cabecera_borde.add(new JPanel(), BorderLayout.NORTH);
        cabecera_borde.add(new JPanel(), BorderLayout.SOUTH);
        cabecera_borde.add(cabecera, BorderLayout.CENTER);
        panel_central.add(cabecera_borde, BorderLayout.NORTH);
        panel_central.add(new JPanel(), BorderLayout.SOUTH);
        panel_central.add(new JPanel(), BorderLayout.EAST);
        panel_central.add(new JPanel(), BorderLayout.WEST);
        panel_central.setBackground(Color.GREEN);
        JPanel panel_tabla = new JPanel(new BorderLayout());
        panel_tabla.setBackground(Color.BLUE);

        for (int i = 0; i < 6; i++) {
            modeloTabla.addColumn("columnas");
        }

        tblProductos = new JTable();
        ThreadDataBase revisaDB = new ThreadDataBase(modeloTabla);
        revisaDB.start();

        tblProductos.setModel(modeloTabla);
        panel_tabla.add(tblProductos, BorderLayout.CENTER);

        JScrollPane scrollTabla = new JScrollPane(panel_tabla);
        panel_central.add(scrollTabla, BorderLayout.CENTER);

        this.add(panel_central, BorderLayout.CENTER);

        JPanel panel_inferior = new JPanel(new FlowLayout());
        JButton boton = new JButton("Agregar");
        boton.addActionListener(this);
        panel_inferior.add(boton);
        boton = new JButton("Eliminar");
        boton.addActionListener(this);
        panel_inferior.add(boton);

        this.add(panel_inferior, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String actionCommand = ae.getActionCommand();
        ProductosDALC productosDALC = new ProductosDALC();
        switch (actionCommand) {

            case "Agregar":
                System.out.println("Agregar");
                try {
                    JframeAgregar agregar = new JframeAgregar(this, "AGREGAR");
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "Eliminar":
                int index = tblProductos.getSelectedRow();
                int idProducto = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(tblProductos.getSelectedRow(), 0)));

                try {
                    productosDALC.EliminaProducto(idProducto);
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Eliminar");
                break;

            case "CÓDIGO":
                try {
                    ArrayList<Object[]> lstProductos = productosDALC.MostrarProductos();
                    if (lstProductos != null) {

                        if (modeloTabla.getRowCount() > 0) {
                            int limit = modeloTabla.getRowCount() - 1;
                            for (int i = limit; i >= 0; i--) {
                                modeloTabla.removeRow(i);
                            }

                            ArrayList arrayData = new ArrayList();
                            for (Object[] objects : lstProductos) {
                                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                                Producto producto = new Producto();
                                producto.setId(Integer.parseInt((String) objects[0]));
                                producto.setNombre((String) objects[1]);
                                producto.setDepartamento((String) objects[2]);
                                producto.setCodigo(Double.parseDouble((String) objects[3]));
                                producto.setCantidad(Integer.parseInt((String) objects[4]));
                                producto.setPrecio(Integer.parseInt((String) objects[5]));
                                arrayData.add(producto);
                            }

                            Collections.sort(arrayData);

                            for (Object object : arrayData) {
                                Producto producto = new Producto();
                                producto = (Producto) object;
                                Object[] row = {
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getDepartamento(),
                                    producto.getCodigo(),
                                    producto.getCantidad(),
                                    producto.getPrecio()
                                };
                                modeloTabla.addRow(row);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "CANTIDAD":
                try {
                    ArrayList<Object[]> lstProductos = productosDALC.MostrarProductos();
                    if (lstProductos != null) {

                        if (modeloTabla.getRowCount() > 0) {
                            int limit = modeloTabla.getRowCount() - 1;
                            for (int i = limit; i >= 0; i--) {
                                modeloTabla.removeRow(i);
                            }

                            ArrayList arrayData = new ArrayList();
                            for (Object[] objects : lstProductos) {
                                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                                Producto producto = new Producto();
                                producto.setId(Integer.parseInt((String) objects[0]));
                                producto.setNombre((String) objects[1]);
                                producto.setDepartamento((String) objects[2]);
                                producto.setCodigo(Double.parseDouble((String) objects[3]));
                                producto.setCantidad(Integer.parseInt((String) objects[4]));
                                producto.setPrecio(Integer.parseInt((String) objects[5]));
                                arrayData.add(producto);
                            }

                            Collections.sort(arrayData, new ComparaPorCantidad());

                            for (Object object : arrayData) {
                                Producto producto = new Producto();
                                producto = (Producto) object;
                                Object[] row = {
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getDepartamento(),
                                    producto.getCodigo(),
                                    producto.getCantidad(),
                                    producto.getPrecio()
                                };
                                modeloTabla.addRow(row);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "":
                try {
                    ArrayList<Object[]> lstProductos = productosDALC.MostrarProductos();
                    if (lstProductos != null) {

                        if (modeloTabla.getRowCount() > 0) {
                            int limit = modeloTabla.getRowCount() - 1;
                            for (int i = limit; i >= 0; i--) {
                                modeloTabla.removeRow(i);
                            }

                            ArrayList arrayData = new ArrayList();
                            for (Object[] objects : lstProductos) {
                                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                                Producto producto = new Producto();
                                producto.setId(Integer.parseInt((String) objects[0]));
                                producto.setNombre((String) objects[1]);
                                producto.setDepartamento((String) objects[2]);
                                producto.setCodigo(Double.parseDouble((String) objects[3]));
                                producto.setCantidad(Integer.parseInt((String) objects[4]));
                                producto.setPrecio(Integer.parseInt((String) objects[5]));
                                arrayData.add(producto);
                            }

                            Collections.sort(arrayData, new ComparaPorId());

                            for (Object object : arrayData) {
                                Producto producto = new Producto();
                                producto = (Producto) object;
                                Object[] row = {
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getDepartamento(),
                                    producto.getCodigo(),
                                    producto.getCantidad(),
                                    producto.getPrecio()
                                };
                                modeloTabla.addRow(row);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "PRECIO":
                try {
                    ArrayList<Object[]> lstProductos = productosDALC.MostrarProductos();
                    if (lstProductos != null) {

                        if (modeloTabla.getRowCount() > 0) {
                            int limit = modeloTabla.getRowCount() - 1;
                            for (int i = limit; i >= 0; i--) {
                                modeloTabla.removeRow(i);
                            }

                            ArrayList arrayData = new ArrayList();
                            for (Object[] objects : lstProductos) {
                                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                                Producto producto = new Producto();
                                producto.setId(Integer.parseInt((String) objects[0]));
                                producto.setNombre((String) objects[1]);
                                producto.setDepartamento((String) objects[2]);
                                producto.setCodigo(Double.parseDouble((String) objects[3]));
                                producto.setCantidad(Integer.parseInt((String) objects[4]));
                                producto.setPrecio(Integer.parseInt((String) objects[5]));
                                arrayData.add(producto);
                            }

                            Collections.sort(arrayData, new ComparaPorPrecio());

                            for (Object object : arrayData) {
                                Producto producto = new Producto();
                                producto = (Producto) object;
                                Object[] row = {
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getDepartamento(),
                                    producto.getCodigo(),
                                    producto.getCantidad(),
                                    producto.getPrecio()
                                };
                                modeloTabla.addRow(row);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "NOMBRE":
                try {
                    ArrayList<Object[]> lstProductos = productosDALC.MostrarProductos();
                    if (lstProductos != null) {

                        if (modeloTabla.getRowCount() > 0) {
                            int limit = modeloTabla.getRowCount() - 1;
                            for (int i = limit; i >= 0; i--) {
                                modeloTabla.removeRow(i);
                            }

                            ArrayList arrayData = new ArrayList();
                            for (Object[] objects : lstProductos) {
                                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                                Producto producto = new Producto();
                                producto.setId(Integer.parseInt((String) objects[0]));
                                producto.setNombre((String) objects[1]);
                                producto.setDepartamento((String) objects[2]);
                                producto.setCodigo(Double.parseDouble((String) objects[3]));
                                producto.setCantidad(Integer.parseInt((String) objects[4]));
                                producto.setPrecio(Integer.parseInt((String) objects[5]));
                                arrayData.add(producto);
                            }

                            Collections.sort(arrayData, new ComparaPorNombre());

                            for (Object object : arrayData) {
                                Producto producto = new Producto();
                                producto = (Producto) object;
                                Object[] row = {
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getDepartamento(),
                                    producto.getCodigo(),
                                    producto.getCantidad(),
                                    producto.getPrecio()
                                };
                                modeloTabla.addRow(row);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "DEPARTAMENTO":
                try {
                    ArrayList<Object[]> lstProductos = productosDALC.MostrarProductos();
                    if (lstProductos != null) {

                        if (modeloTabla.getRowCount() > 0) {
                            int limit = modeloTabla.getRowCount() - 1;
                            for (int i = limit; i >= 0; i--) {
                                modeloTabla.removeRow(i);
                            }

                            ArrayList arrayData = new ArrayList();
                            for (Object[] objects : lstProductos) {
                                //columnNames = {" "id","Nombre", "Código", "Cantidad", "Precio"};
                                Producto producto = new Producto();
                                producto.setId(Integer.parseInt((String) objects[0]));
                                producto.setNombre((String) objects[1]);
                                producto.setDepartamento((String) objects[2]);
                                producto.setCodigo(Double.parseDouble((String) objects[3]));
                                producto.setCantidad(Integer.parseInt((String) objects[4]));
                                producto.setPrecio(Integer.parseInt((String) objects[5]));
                                arrayData.add(producto);
                            }

                            Collections.sort(arrayData, new ComparaPorDepartamento());

                            for (Object object : arrayData) {
                                Producto producto = new Producto();
                                producto = (Producto) object;
                                Object[] row = {
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getDepartamento(),
                                    producto.getCodigo(),
                                    producto.getCantidad(),
                                    producto.getPrecio()
                                };
                                modeloTabla.addRow(row);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "+":
                if (tblProductos.getSelectedRow() >= 0) {
                    boolean correcto = true;
                    char[] texto = jtfCantidad.getText().toCharArray();
                    for (int i = 0; i < texto.length; i++) {
                        char caracter = texto[i];
                        if (caracter < 48 || caracter > 57) {
                            correcto = false;
                        }
                    }

                    if (correcto) {
                        index = tblProductos.getSelectedRow();
                        int cantidad = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(tblProductos.getSelectedRow(), 4)));
                        int id = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(tblProductos.getSelectedRow(), 0)));
                        try {
                            productosDALC.UpdateProducto(cantidad + Integer.parseInt(jtfCantidad.getText()), id);
                        } catch (SQLException ex) {
                            Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ArrayList<Object[]> arrayData = new ArrayList<Object[]>();

                        try {

                            arrayData = productosDALC.MostrarProductos();
                        } catch (SQLException ex) {
                            Logger.getLogger(ThreadDataBase.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (arrayData != null) {
                            if (modeloTabla.getRowCount() > 0) {
                                int limit = modeloTabla.getRowCount() - 1;
                                for (int i = limit; i >= 0; i--) {
                                    modeloTabla.removeRow(i);
                                }
                            }

                            for (Object[] objects : arrayData) {
                                modeloTabla.addRow(objects);
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Parámetros incorrectos");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un producto de la tabla"
                            + " para modificar inventario");
                }
                break;

            case "-":
                if (tblProductos.getSelectedRow() >= 0) {
                    int cantidad = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(tblProductos.getSelectedRow(), 4)));
                    if (cantidad > 0) {
                        boolean correcto = true;
                        char[] texto = jtfCantidad.getText().toCharArray();
                        for (int i = 0; i < texto.length; i++) {
                            char caracter = texto[i];
                            if (caracter < 48 || caracter > 57) {
                                correcto = false;
                            }
                        }

                        if (correcto) {
                            index = tblProductos.getSelectedRow();
                            int id = Integer.parseInt(String.valueOf(modeloTabla.getValueAt(tblProductos.getSelectedRow(), 0)));
                            try {
                                productosDALC.UpdateProducto(cantidad - Integer.parseInt(jtfCantidad.getText()), id);
                            } catch (SQLException ex) {
                                Logger.getLogger(JFrameInventario.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ArrayList<Object[]> arrayData = new ArrayList<Object[]>();

                            try {

                                arrayData = productosDALC.MostrarProductos();
                            } catch (SQLException ex) {
                                Logger.getLogger(ThreadDataBase.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (arrayData != null) {
                                if (modeloTabla.getRowCount() > 0) {
                                    int limit = modeloTabla.getRowCount() - 1;
                                    for (int i = limit; i >= 0; i--) {
                                        modeloTabla.removeRow(i);
                                    }
                                }

                                for (Object[] objects : arrayData) {
                                    modeloTabla.addRow(objects);
                                }
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Parámetros incorrectos");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: La existencia de este producto es 0");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un producto de la tabla"
                            + " para modificar inventario");
                }
                break;
        }
    }
}
