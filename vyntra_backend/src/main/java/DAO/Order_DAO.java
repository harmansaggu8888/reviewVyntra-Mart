package DAO;

import java.util.List;


import Model.UserOrder;



public interface Order_DAO{

	UserOrder findByOrderId(int orderId);
	public boolean addToUserOrders(UserOrder ord);
	
	//List<UserOrder> findAll();

}
