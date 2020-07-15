package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.Cart;
import Model.Product;
import Model.UserOrder;

@Repository
public class Order_DAO_Imp implements Order_DAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	public UserOrder findByOrderId(int orderId){
		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserOrder> query=currentSession.createQuery("from user_order where orderId=:orderid", UserOrder.class);
		query.setParameter("orderid", orderId);
		UserOrder order =query.getResultList().get(0);
		return order;
	}

	public boolean addToUserOrders(UserOrder ord)
	{
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().save(ord);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	
	@Override
	public List<UserOrder> getOrders() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserOrder> query = currentSession.createQuery("from UserOrder", UserOrder.class);
		List<UserOrder> list = query.getResultList();
		return list;
	}

}
