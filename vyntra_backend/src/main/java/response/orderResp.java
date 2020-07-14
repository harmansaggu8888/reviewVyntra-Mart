package response;

import java.util.List;

import Model.Cart;

public class orderResp {

	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	private List<Cart> cartList;

	@Override
	public String toString() {
		return "orderResp [orderId=" + orderId + ", cartList=" + cartList + "]";
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}
}