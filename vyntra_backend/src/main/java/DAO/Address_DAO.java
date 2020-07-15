package DAO;


import Model.UserAddress;
import Model.Product;
import Model.User;


public interface Address_DAO{

	public boolean addAddress(UserAddress address);
	UserAddress findByUser(User user);
}
