package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.User;

@Repository
public class User_DAO_Imp implements User_DAO{


	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public boolean saveUser(User user) {
		boolean status=false;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			currentSession.save(user);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public User findByEmailAndPasswordAndUsertype(String email, String password, String usertype){
		Session currentSession = sessionFactory.getCurrentSession();
		System.out.println(email);
		System.out.println(password);
		System.out.println(usertype);
		Query<User> query=currentSession.createQuery("from User where email=:email AND password=:password AND usertype=:usertype" , User.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		query.setParameter("usertype", usertype);
		User user=query.getResultList().get(0); // needs change here
		return user;
	}


	

}
