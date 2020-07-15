package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import response.viewOrdResp;
import response.response;
import response.userResp;
import response.cartResp;
import Model.UserAddress;
import response.serverResp;
import response.prodResp;

import Model.User;
import Model.Product;
import Service.Vyntra_Service;
import common.WebConstants;


@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping(value="/api")
public class Controller {
	
	@Autowired
	private Vyntra_Service vyntraservice;
	
	@GetMapping("product/{product_id}")
	public Product productByID(@PathVariable("product_id") int product_id) {
		 return vyntraservice.getProductByID(product_id);
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<serverResp> addUser(@Valid @RequestBody User user) {
		return vyntraservice.addUser(user);
	}
	
	@PostMapping("/verify_user")
	public ResponseEntity<serverResp> verifyUser(@Valid @RequestBody Map<String, String> credential) {
		return vyntraservice.verifyUser(credential);
	}
	
	@PostMapping("/verify_admin")
	public ResponseEntity<serverResp> verifyAdmin(@Valid @RequestBody Map<String, String> credential) {
		return vyntraservice.verifyAdmin(credential);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<prodResp> addProduct(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN,
			@RequestParam(name = WebConstants.PROD_FILE) MultipartFile prodImage,
			@RequestParam(name = WebConstants.PROD_DESC) String description,
			@RequestParam(name = WebConstants.PROD_PRICE) String price,
			@RequestParam(name = WebConstants.PROD_NAME) String productname,
			@RequestParam(name = WebConstants.PROD_QUANITY) String quantity){
		return vyntraservice.addProduct(AUTH_TOKEN,prodImage,description,price,productname,quantity);
	}
		
	@PostMapping("/getProducts")
	public ResponseEntity<prodResp> getProducts(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN)
	{
		return vyntraservice.getProducts(AUTH_TOKEN);
	}
	
	@PostMapping("/get-Products")
	public ResponseEntity<prodResp> get_Products()
	{
		return vyntraservice.get_Products();
	}
	
	@GetMapping("/getProduct/{prodid}")
	public ResponseEntity<prodResp> getProductbyID(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN,
			@PathVariable("prodid") String productid){
		return vyntraservice.getProductbyID( AUTH_TOKEN,  productid);
	}
	
	@GetMapping("/delProduct/{prodid}")
	public ResponseEntity<prodResp> delProduct(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN,
			@PathVariable("prodid") String productid){
		return vyntraservice.delProduct( AUTH_TOKEN,  productid);
	}
	
	
	@GetMapping("/addToCart/{prodid}")
	public ResponseEntity<serverResp> addToCart(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN,
			@PathVariable("prodid") String productId) throws IOException {
		return vyntraservice.addToCart(AUTH_TOKEN, productId);
	}
	
	@GetMapping("/viewCart")
	public ResponseEntity<cartResp> viewCart(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN)
			throws IOException {
		
		return vyntraservice.viewCart(AUTH_TOKEN);
	}
	
	
	
	@GetMapping("/updateCart")
	public ResponseEntity<cartResp> updateCart(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN,
			@RequestParam(name = WebConstants.BUF_ID) String bufcartid,
			@RequestParam(name = WebConstants.BUF_QUANTITY) String quantity) throws IOException {
		return vyntraservice.updateCart(AUTH_TOKEN, bufcartid, quantity);
	}
	
	
	
	@GetMapping("/delCart")
	public ResponseEntity<cartResp> delCart(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN,
			@RequestParam(name = WebConstants.BUF_ID) String bufcartid) throws IOException {
		return vyntraservice.delCart( AUTH_TOKEN, bufcartid);
	}

	
	@GetMapping("/placeOrder")
	public ResponseEntity<serverResp> placeOrder(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN)
			throws IOException {
		return vyntraservice.placeOrder( AUTH_TOKEN);
	}
	
	
	

	@GetMapping("/getOrders")
	public ResponseEntity<viewOrdResp> viewOrders(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN)
			throws IOException {

		return vyntraservice.viewOrders( AUTH_TOKEN);
	}

	
	
	
	@PostMapping("/addAddress")
	public ResponseEntity<userResp> addAddress(@Valid @RequestBody UserAddress address,
			@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN) {
		return vyntraservice.addAddress( address,  AUTH_TOKEN);
		
	}

	@PostMapping("/getAddress")
	public ResponseEntity<response> getAddress(@RequestHeader(name = WebConstants.USER_AUTH_TOKEN) String AUTH_TOKEN) {

		return vyntraservice.getAddress( AUTH_TOKEN);
	}
	
	
}
