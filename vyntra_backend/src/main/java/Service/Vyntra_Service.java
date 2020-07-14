package Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import Model.Product;
import Model.User;
import response.cartResp;
import response.prodResp;
import response.serverResp;

public interface Vyntra_Service {

	public ResponseEntity<prodResp> addProduct(String AUTH_TOKEN,MultipartFile prodImage,String description, String price, String productname, String quantity);
	public ResponseEntity<prodResp> getProducts(String AUTH_TOKEN);
	public ResponseEntity<prodResp> getProductbyID( String AUTH_TOKEN, String productid);
	public ResponseEntity<prodResp> delProduct(String AUTH_TOKEN, String productid);
	public Product getProductByID(int productid);
	public boolean updateProduct(Product pro);
	public ResponseEntity<serverResp> addUser(User user);
	public ResponseEntity<serverResp> verifyUser(Map<String, String> credential);
	public ResponseEntity<serverResp> verifyAdmin(Map<String, String> credential);
	public ResponseEntity<serverResp> addToCart(String AUTH_TOKEN, String productId);
	public ResponseEntity<cartResp> viewCart( String AUTH_TOKEN);
	public ResponseEntity<cartResp> updateCart(String AUTH_TOKEN,String bufcartid,String quantity);
	public ResponseEntity<cartResp> delCart( String AUTH_TOKEN, String bufcartid);
	public ResponseEntity<serverResp> placeOrder( String AUTH_TOKEN);
}
