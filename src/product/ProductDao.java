package product;

import java.util.List;

public interface ProductDao {
    //int delete(int id);
    List<Product> getAll();
    List<Product> getByIdFragment(int idFragment);
    int insert(Product product);
}
