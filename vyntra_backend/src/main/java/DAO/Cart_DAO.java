package DAO;

import java.util.List;
import Model.Cart;
import Model.Product;


public interface Cart_DAO  {

	List<Cart> findByEmail(String email);
	
	public boolean addToCart(Cart buf);

	public Cart findBycartIdAndEmail(int cartId, String email);
	
	public boolean updateCart(Cart cart);

	public void deleteBycartIdAndEmail(int cartId, String email);

	//List<Cart> findAllByEmail(String email);

	public List<Cart> findAllByOrderId(int orderId);
}
