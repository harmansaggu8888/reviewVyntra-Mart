package DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.UserAddress;
import Model.User;

@Repository
public class Address_DAO_Imp implements Address_DAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean addAddress(UserAddress address){
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().save(address);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public UserAddress findByUser(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserAddress> query=currentSession.createQuery("from  UserAddress where userid=:userid", UserAddress.class);
		query.setParameter("userid", user.getUserid());
		UserAddress add = query.getResultList().get(0);
		return add;
	}
}
