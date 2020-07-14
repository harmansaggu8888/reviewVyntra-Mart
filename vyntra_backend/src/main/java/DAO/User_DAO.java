package DAO;


import java.util.List;
import Model.User;

public interface User_DAO {
	
	public boolean saveUser(User user);
	User findByEmailAndPasswordAndUsertype(String email, String password, String usertype);

}


