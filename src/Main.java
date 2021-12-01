import product.ProductDaoImpl;

public class Main {

    public static void main(String[] args) {
        ProductDaoImpl productDao = new ProductDaoImpl();
        App app = new App(productDao);
        app.run();
    }
}
