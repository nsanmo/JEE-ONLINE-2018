package dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import domain.Partido;


public class PartidoDAOImpl extends RetrieveDAO<Partido> implements PartidoDAO {

	private static Session session=null;
	
	public PartidoDAOImpl(){
		if(session==null){
			createSession();
		}
	}
	
	private void createSession(){
		Configuration conf= new Configuration().configure();
		
		StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
	    sb.applySettings(conf.getProperties());
	    StandardServiceRegistry standardServiceRegistry = sb.build();                   
	    SessionFactory sf = conf.buildSessionFactory(standardServiceRegistry);

		session= sf.openSession();
		
	}


	public Partido save(Partido entity) {
		Transaction tx=session.beginTransaction();
		entity.setIdPartido(100);
		session.saveOrUpdate(entity);
		tx.commit();
		return entity;
	}

	public void remove(Partido entity) {
		Transaction tx=session.beginTransaction();
		session.delete(entity);
		tx.commit();
	}


	// Getters and Setters
	public Session getSession(){
		return session;
	}
	
	public static void setSession(Session s){
		session=s;
	}


}
