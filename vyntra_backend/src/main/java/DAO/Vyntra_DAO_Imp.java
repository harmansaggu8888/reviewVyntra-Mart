package DAO;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



import Model.Product;

@Repository
public class Vyntra_DAO_Imp  implements Vyntra_DAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	


	@Override
	public boolean updateProduct(Product pro) {
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().update(pro);
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	

}
