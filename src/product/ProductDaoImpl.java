package product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/javalab2";
    private static final String ID = "root";
    private static final String PASS = "root";

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, ID, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        Statement stmt = null;
        Connection connection = null;
        List<Product> list = new ArrayList<>();

        try {
            connection = getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from product");

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));

                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
            close(connection);
        }

        return list;
    }

    @Override
    public List<Product> getByIdFragment(int idFragment) {
        PreparedStatement stmt = null;
        Connection connection = null;
        List<Product> list = new ArrayList<>();

        try {
            connection = getConnection();
            String like =
                    "%" + idFragment + "%";
            stmt = connection.prepareStatement("select * from product WHERE CONVERT(id, CHAR) LIKE ?");
            stmt.setString(1, like);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));

                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
            close(connection);
        }

        return list;
    }

    @Override
    public int insert(Product product) {
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
            connection = getConnection();
            stmt = connection.prepareStatement("insert into product values(null,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(connection);
        }
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
