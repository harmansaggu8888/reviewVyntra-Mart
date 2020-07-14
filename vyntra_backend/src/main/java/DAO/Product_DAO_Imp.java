package DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.Product;
import Model.User;

@Repository
public class Product_DAO_Imp implements Product_DAO {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public boolean addProduct(Product product) {
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().save(product);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	

	@Override
	public List<Product> getProducts() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Product> query=currentSession.createQuery("from Product", Product.class);
		List<Product> list=query.getResultList();
		return list;
	}


	
	@Override
	public Product findByProductid(int productid) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Product> query=currentSession.createQuery("from Product where productid=:productid", Product.class);
		query.setParameter("productid", productid);
		Product product=query.getSingleResult();
		return product;
	}

	@Override
	public boolean deleteByProductid(int productid) {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Product> query=currentSession.createQuery("from Product where productid=:productid", Product.class);
			query.setParameter("productid", productid);
			Product product = query.getSingleResult();
			currentSession.delete(product);
			return true;
		}
}
