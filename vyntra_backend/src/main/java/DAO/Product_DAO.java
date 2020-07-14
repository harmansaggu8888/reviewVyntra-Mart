package DAO;



import java.util.List;

import Model.Product;
import Model.User;


public interface Product_DAO{

	public boolean addProduct(Product product);
	public List<Product> getProducts();
	Product findByProductid(int productid);
	public boolean deleteByProductid(int productid);
}
