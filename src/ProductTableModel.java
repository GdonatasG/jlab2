import product.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","Name","Price","Quantity"};
    private List<Product> listOfProducts;
    public ProductTableModel(List<Product> products) {
        listOfProducts = products;
    }

    @Override
    public int getRowCount() {
        int size;
        if (listOfProducts == null) {
            size = 0;
        }
        else {
            size = listOfProducts.size();
        }
        return size;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        if (columnIndex == 0) {
            temp = listOfProducts.get(rowIndex).getId();
        }
        else if (columnIndex == 1) {
            temp = listOfProducts.get(rowIndex).getName();
        }
        else if (columnIndex == 2) {
            temp = listOfProducts.get(rowIndex).getPrice();
        }
        else if (columnIndex == 3) {
            temp = listOfProducts.get(rowIndex).getQuantity();
        }
        return temp;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
}
