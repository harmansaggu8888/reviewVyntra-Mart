package DAO;

import Model.User;


public interface jwt_DAO {

	public String createToken(String session_email, String session_pass, String session_type) ;
	public User checkToken(String token) ;

}