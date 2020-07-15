package Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import response.order;
import response.viewOrdResp;

import response.response;
import response.userResp;

import Model.UserOrder;
import Model.Cart;
import Model.UserAddress;

import response.cartResp;
import response.prodResp;

import common.WebConstants;
import DAO.jwt_DAO;

import DAO.User_DAO;
import DAO.Vyntra_DAO;
import DAO.Product_DAO;
import DAO.Cart_DAO;
import DAO.Order_DAO;
import DAO.Address_DAO;
import Model.Product;
import Model.User;
import common.ResponseCode;
import common.Validator;
import response.serverResp;
import response.viewOrdResp;

@Service
@Transactional
public class Vyntra_Service_Imp implements Vyntra_Service {
 
	@Autowired
	private Vyntra_DAO vyntradao;
	
	@Autowired
	private User_DAO userRepo;
	
	@Autowired
	private Product_DAO proRepo;
	
	@Autowired
	private jwt_DAO jwtRepo;
	
	@Autowired
	private Cart_DAO cartRepo;
	
	@Autowired
	private Order_DAO ordRepo;
	
	
	@Autowired
	private Address_DAO addrRepo;
	
	@Override
	public Product getProductByID(int productid) {
		return proRepo.findByProductid(productid);
	}

	@Override
	public boolean updateProduct(Product pro) {
		return vyntradao.updateProduct(pro);
	}
	
	@Override
	public ResponseEntity<serverResp> addUser(User user) {
		System.out.println("sign up user---------------------------"+user.getUsername());
		serverResp resp = new serverResp();
		try {
			if (Validator.isUserEmpty(user)) {
				resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
				resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
			} else if (!Validator.isValidEmail(user.getEmail())) {
				resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
				resp.setMessage(ResponseCode.INVALID_EMAIL_FAIL_MSG);
			} else {
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.CUST_REG);
				userRepo.saveUser(user);
				resp.setObject(user);
			}
		} catch (Exception e) {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(e.getMessage());
		}
		return new ResponseEntity<serverResp>(resp, HttpStatus.ACCEPTED);
	}
	

	@Override
	public ResponseEntity<serverResp> verifyUser(Map<String, String> credential) {

		String email = "";
		String password = "";
		if (credential.containsKey(WebConstants.USER_EMAIL)) {
			email = credential.get(WebConstants.USER_EMAIL);
		}
		if (credential.containsKey(WebConstants.USER_PASSWORD)) {
			password = credential.get(WebConstants.USER_PASSWORD);
		}
		User loggedUser = userRepo.findByEmailAndPasswordAndUsertype(email, password, WebConstants.USER_CUST_ROLE);
		serverResp resp = new serverResp();
		if (loggedUser != null) {
			
			System.out.println(WebConstants.USER_CUST_ROLE);
			String jwtToken = jwtRepo.createToken(email, password, WebConstants.USER_CUST_ROLE);
			resp.setStatus(ResponseCode.SUCCESS_CODE);
			resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
			resp.setAUTH_TOKEN(jwtToken);
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<serverResp>(resp, HttpStatus.OK);
	}



	@Override
	public ResponseEntity<serverResp> verifyAdmin(Map<String, String> credential) {

		String email = "";
		String password = "";
		if (credential.containsKey(WebConstants.USER_EMAIL)) {
			email = credential.get(WebConstants.USER_EMAIL);
		}
		if (credential.containsKey(WebConstants.USER_PASSWORD)) {
			password = credential.get(WebConstants.USER_PASSWORD);
		}
		User loggedUser = userRepo.findByEmailAndPasswordAndUsertype(email, password, WebConstants.USER_ADMIN_ROLE);
		serverResp resp = new serverResp();
		if (loggedUser != null) {
			
			System.out.println(WebConstants.USER_ADMIN_ROLE);
			String jwtToken = jwtRepo.createToken(email, password, WebConstants.USER_ADMIN_ROLE);
			resp.setStatus(ResponseCode.SUCCESS_CODE);
			resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
			resp.setAUTH_TOKEN(jwtToken);
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<serverResp>(resp, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<prodResp> addProduct(String AUTH_TOKEN,
			MultipartFile prodImage,String description, String price, String productname,
			String quantity){
		prodResp resp = new prodResp();
		if (Validator.isStringEmpty(productname) || Validator.isStringEmpty(description)
				|| Validator.isStringEmpty(price) || Validator.isStringEmpty(quantity)) {
			resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
			resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
		} else if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				Product prod = new Product();
				prod.setDescription(description);
				prod.setPrice(Double.parseDouble(price));
				prod.setProductname(productname);
				prod.setQuantity(Integer.parseInt(quantity));
				prod.setProductimage(prodImage.getBytes());
				proRepo.addProduct(prod);

				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.ADD_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOblist(proRepo.getProducts());
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
			resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
		}
		return new ResponseEntity<prodResp>(resp, HttpStatus.ACCEPTED);
	}
	
	
	@Override
	public ResponseEntity<prodResp> getProducts(String AUTH_TOKEN)
	{
		prodResp resp = new prodResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.LIST_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOblist(proRepo.getProducts());
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<prodResp>(resp, HttpStatus.ACCEPTED);
	}
	
	@Override
	public ResponseEntity<prodResp> get_Products()
	{
		prodResp resp = new prodResp();
		{
			try {
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.LIST_SUCCESS_MESSAGE);
				
				resp.setOblist(proRepo.getProducts());
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
			}
		} 
		return new ResponseEntity<prodResp>(resp, HttpStatus.ACCEPTED);
	}
	
	
	@Override
	public ResponseEntity<prodResp> getProductbyID( String AUTH_TOKEN, String productid){
		prodResp resp = new prodResp();
		if (Validator.isStringEmpty(productid)) {
			resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
			resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
		} else if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				Product prod = proRepo.findByProductid(Integer.parseInt(productid));
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.DEL_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOb(prod);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.toString());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<prodResp>(resp, HttpStatus.ACCEPTED);
	}
	
	@Override
	public ResponseEntity<prodResp> delProduct(String AUTH_TOKEN, String productid){
		prodResp resp = new prodResp();
		if (Validator.isStringEmpty(productid)) {
			resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
			resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
		} else if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				proRepo.deleteByProductid(Integer.parseInt(productid));
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.DEL_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOblist(proRepo.getProducts());
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.toString());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<prodResp>(resp, HttpStatus.ACCEPTED);
	}
	
	
	@Override
	public ResponseEntity<serverResp> addToCart(String AUTH_TOKEN, String productId){
		serverResp resp = new serverResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				User loggedUser = jwtRepo.checkToken(AUTH_TOKEN);
				Product cartItem = proRepo.findByProductid(Integer.parseInt(productId));

				Cart buf = new Cart();
				buf.setEmail(loggedUser.getEmail());
				buf.setQuantity(1);
				buf.setPrice(cartItem.getPrice());
				buf.setProductId(Integer.parseInt(productId));
				buf.setProductname(cartItem.getProductname());
				Date date = new Date();
				buf.setDateAdded(date);

				cartRepo.addToCart(buf);
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.CART_UPD_MESSAGE_CODE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<serverResp>(resp, HttpStatus.ACCEPTED);
	}
	
	
	@Override
	public ResponseEntity<cartResp> viewCart( String AUTH_TOKEN){
		
		//logger.info("Inside View cart request method");
		cartResp resp = new cartResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				//logger.info("Inside View cart request method 2");
				User loggedUser = jwtRepo.checkToken(AUTH_TOKEN);
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.VW_CART_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOblist(cartRepo.findByEmail(loggedUser.getEmail()));
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<cartResp>(resp, HttpStatus.ACCEPTED);
	}
	
	
	@Override
	public ResponseEntity<cartResp> updateCart(String AUTH_TOKEN,String bufcartid,String quantity){
		cartResp resp = new cartResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				User loggedUser = jwtRepo.checkToken(AUTH_TOKEN);
				Cart selCart = cartRepo.findBycartIdAndEmail(Integer.parseInt(bufcartid), loggedUser.getEmail());
				selCart.setQuantity(Integer.parseInt(quantity));
				cartRepo.updateCart(selCart);
				List<Cart> bufcartlist = cartRepo.findByEmail(loggedUser.getEmail());
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.UPD_CART_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOblist(bufcartlist);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<cartResp>(resp, HttpStatus.ACCEPTED);
	}
	
	

	@Override
	public ResponseEntity<cartResp> delCart( String AUTH_TOKEN, String bufcartid){
		cartResp resp = new cartResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				User loggedUser = jwtRepo.checkToken(AUTH_TOKEN);
				cartRepo.deleteBycartIdAndEmail(Integer.parseInt(bufcartid), loggedUser.getEmail());
				List<Cart> bufcartlist = cartRepo.findByEmail(loggedUser.getEmail());
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.DEL_CART_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				resp.setOblist(bufcartlist);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<cartResp>(resp, HttpStatus.ACCEPTED);
	}

	
	@Override
	public ResponseEntity<serverResp> placeOrder( String AUTH_TOKEN){

		serverResp resp = new serverResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				User loggedUser = jwtRepo.checkToken(AUTH_TOKEN);
				UserOrder po = new UserOrder();
				po.setEmail(loggedUser.getEmail());
				Date date = new Date();
				po.setOrderDate(date);
				po.setOrderStatus(ResponseCode.ORD_STATUS_CODE);
				double total = 0;
				List<Cart> buflist = cartRepo.findByEmail(loggedUser.getEmail());
				for (Cart buf : buflist) {
					total = +(buf.getQuantity() * buf.getPrice());
				}
				po.setTotalCost(total);
				if(ordRepo.addToUserOrders(po))
				{
					buflist.forEach(bufcart -> {
						bufcart.setOrderId(po.getOrderId());
						cartRepo.updateCart(bufcart);
	
					});
				}
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.ORD_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<serverResp>(resp, HttpStatus.ACCEPTED);
	}
	
	
	@Override
	public ResponseEntity<userResp> addAddress(UserAddress address, String AUTH_TOKEN) {
		userResp resp = new userResp();
		if (Validator.isAddressEmpty(address)) {
			resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
			resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
		} else if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				User user = jwtRepo.checkToken(AUTH_TOKEN);
				user.setAddress(address);
				address.setUser(user);
				addrRepo.addAddress(address);
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.CUST_ADR_ADD);
				resp.setUser(user);
				resp.setAddress(address);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<userResp>(resp, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<response> getAddress(String AUTH_TOKEN) {

		response resp = new response();
		if (jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				User user = jwtRepo.checkToken(AUTH_TOKEN);
				UserAddress adr = addrRepo.findByUser(user);

				HashMap<String, String> map = new HashMap<>();
				map.put(WebConstants.ADR_NAME, adr.getAddress());
				map.put(WebConstants.ADR_CITY, adr.getCity());
				map.put(WebConstants.ADR_STATE, adr.getState());
				map.put(WebConstants.ADR_COUNTRY, adr.getCountry());
				map.put(WebConstants.ADR_ZP, String.valueOf(adr.getZipcode()));
				map.put(WebConstants.PHONE, adr.getPhonenumber());

				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.CUST_ADR_ADD);
				resp.setMap(map);
				// resp.setAddress(adr);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<response>(resp, HttpStatus.ACCEPTED);
	}
	
	

	@Override
	public ResponseEntity<viewOrdResp> viewOrders(String AUTH_TOKEN){

		viewOrdResp resp = new viewOrdResp();
		if (!Validator.isStringEmpty(AUTH_TOKEN) && jwtRepo.checkToken(AUTH_TOKEN) != null) {
			try {
				resp.setStatus(ResponseCode.SUCCESS_CODE);
				resp.setMessage(ResponseCode.VIEW_SUCCESS_MESSAGE);
				resp.setAUTH_TOKEN(AUTH_TOKEN);
				List<order> orderList = new ArrayList<>();
				List<UserOrder> poList = ordRepo.getOrders();
				poList.forEach((po) -> {
					order ord = new order();
					ord.setOrderBy(po.getEmail());
					ord.setOrderId(po.getOrderId());
					ord.setOrderStatus(po.getOrderStatus());
					ord.setProducts(cartRepo.findAllByOrderId(po.getOrderId()));
					orderList.add(ord);
				});
				resp.setOrderlist(orderList);
			} catch (Exception e) {
				resp.setStatus(ResponseCode.FAILURE_CODE);
				resp.setMessage(e.getMessage());
				resp.setAUTH_TOKEN(AUTH_TOKEN);
			}
		} else {
			resp.setStatus(ResponseCode.FAILURE_CODE);
			resp.setMessage(ResponseCode.FAILURE_MESSAGE);
		}
		return new ResponseEntity<viewOrdResp>(resp, HttpStatus.ACCEPTED);
	}

	
}
