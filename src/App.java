import product.ProductDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class App extends JFrame {
    private ProductTableModel[] tableModel;
    private JTable jt;
    private final ProductDaoImpl productDao;

    public App(ProductDaoImpl pDao) {
        this.productDao = pDao;
    }

    public void run() {
        tableModel = new ProductTableModel[]{new ProductTableModel(productDao.getAll())};

        this.setTitle("Product Search App");
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        GridBagConstraints c = new GridBagConstraints();

        JLabel labelInputTitle = new JLabel("Enter ID Fragment");
        JTextField textFieldIdFragment = new JTextField(20);

        jt = new JTable(tableModel[0]);
        jt.setBounds(30, 40, 200, 300);

        JButton buttonSearch = new JButton("Search");
        buttonSearch.addActionListener(e -> {
            if (!textFieldIdFragment.getText().isEmpty()) {
                try {
                    tableModel[0] = new ProductTableModel(productDao.getByIdFragment(Integer.parseInt(textFieldIdFragment.getText())));
                    jt.setModel(tableModel[0]);
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(App.this, "Bad input!");
                }
            }
        });

        JButton buttonAddProduct = new JButton("Add Product");
        buttonAddProduct.addActionListener(e -> {
            final AddProductDialog addProductDialog = new AddProductDialog(App.this, false, productDao);
            addProductDialog.setVisible(true);
            addProductDialog.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {

                }

                @Override
                public void componentMoved(ComponentEvent e) {

                }

                @Override
                public void componentShown(ComponentEvent e) {

                }

                @Override
                public void componentHidden(ComponentEvent e) {
                    if (addProductDialog.getAddProductResult() > 0) {
                        tableModel[0] = new ProductTableModel(productDao.getAll());
                        jt.setModel(tableModel[0]);
                        JOptionPane.showMessageDialog(App.this, "Added!");
                    }
                }
            });
        });

        JButton buttonShowAll = new JButton("Show All");
        buttonShowAll.addActionListener(e -> {
            tableModel[0] = new ProductTableModel(productDao.getAll());
            jt.setModel(tableModel[0]);
            textFieldIdFragment.setText("");
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 10, 0, 0);
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        this.getContentPane().add(labelInputTitle, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        this.getContentPane().add(textFieldIdFragment, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        this.getContentPane().add(buttonSearch, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 3;
        c.gridy = 0;
        this.getContentPane().add(buttonShowAll, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 10;
        c.gridy = 1;
        JScrollPane sp = new JScrollPane(jt);
        this.getContentPane().add(sp, c);

        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 0;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;
        this.getContentPane().add(buttonAddProduct, c);

        this.setVisible(true);
    }
}
