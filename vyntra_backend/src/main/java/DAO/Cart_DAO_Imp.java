package DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import Model.Cart;
import Model.Product;

@Repository
public class Cart_DAO_Imp implements Cart_DAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<Cart> findByEmail(String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Cart> query=currentSession.createQuery("from  Cart where email=:email", Cart.class);
		query.setParameter("email", email);
		List<Cart> list=query.getResultList();
		return list;
	}
	
	
	@Override
	public void deleteBycartIdAndEmail(int cartId, String email) {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Cart> query=currentSession.createQuery("from  Cart where email=:email and cartId=:cartid", Cart.class);
			query.setParameter("email", email);
			query.setParameter("cartid", cartId);
			Cart cart = query.getSingleResult();
			currentSession.delete(cart);
		}
	
	
	@Override
	public Cart findBycartIdAndEmail(int cartId, String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Cart> query=currentSession.createQuery("from  Cart where email=:email and cartId=:cartid", Cart.class);
		query.setParameter("email", email);
		query.setParameter("cartid", cartId);
		Cart cart = query.getSingleResult();
		System.out.println("insde findBycartIdAndEmail()");
		return cart;
	}
	

	@Override
	public List<Cart> findAllByOrderId(int orderId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Cart> query=currentSession.createQuery("from  Cart where orderId=:orderId", Cart.class);
		query.setParameter("orderId", orderId);
		List<Cart> list=query.getResultList();
		return list;
	}
	
	
	public boolean addToCart(Cart cart) {
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().save(cart);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	
	@Override
	public boolean updateCart(Cart cart) {
		System.out.println("11111111111111111111");
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().update(cart);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	/*
	@Override
	public void deleteBycartIdAndEmail(int cartId, String email) {
		try {
			sessionFactory.getCurrentSession().delete(student);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	*/

}
