import product.Product;
import product.ProductDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AddProductDialog extends JDialog {
    private int addProductResult = 0;

    public int getAddProductResult() {
        return addProductResult;
    }

    private ProductDaoImpl productDao;

    JLabel labelName = new JLabel("Name");

    JLabel labelPrice = new JLabel("Price");

    JLabel labelQuantity = new JLabel("Quantity");

    JTextField fieldName = new JTextField();

    JTextField fieldPrice = new JTextField();

    JTextField fieldQuantity = new JTextField();

    JButton buttonSave = new JButton("Save");
    JButton buttonCancel = new JButton("Cancel");


    public AddProductDialog(Frame owner, boolean modal, ProductDaoImpl productDao) {
        super(owner, modal);
        this.productDao = productDao;
        init();
    }

    private void init() {
        this.setTitle("Add Product Dialog");
        this.setSize(350, 220);
        this.setLayout(new BorderLayout());

        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        inputs.add(labelName, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 150;
        inputs.add(fieldName, c);

        c.insets = new Insets(0, 0, 0, 5);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 0;
        inputs.add(labelPrice, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 150;
        inputs.add(fieldPrice, c);

        c.insets = new Insets(0, 0, 0, 5);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 0;
        inputs.add(labelQuantity, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 150;
        inputs.add(fieldQuantity, c);

        this.add(inputs, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonSave.addActionListener(e -> {
            if (!fieldName.getText().isEmpty() && !fieldPrice.getText().isEmpty() && !fieldQuantity.getText().isEmpty()) {
                try {
                    final String name = fieldName.getText();
                    final double price = Double.parseDouble(fieldPrice.getText());
                    final int quantity = Integer.parseInt(fieldQuantity.getText());

                    this.addProductResult = productDao.insert(new Product(1, name, price, quantity));

                    if (addProductResult > 0){
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Something went wrong, try again.");
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Something went wrong. Check inputs!");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Check inputs!");
            }
        });
        buttons.add(buttonSave);

        buttonCancel.addActionListener(e -> this.setVisible(false));
        buttons.add(buttonCancel);

        this.add(buttons, BorderLayout.SOUTH);
    }
}
