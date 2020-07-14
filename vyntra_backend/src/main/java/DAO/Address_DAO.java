package DAO;


import Model.UserAddress;
import Model.User;


public interface Address_DAO{

	UserAddress findByUser(User user);
}
