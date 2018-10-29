package dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import domain.Partido; 


public class PartidoDAOImpl implements PartidoDAO {

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

		session=sf.openSession();
	}


	@SuppressWarnings("unchecked")
	public Partido findById(Integer id) {
		Partido p=null;
		p = (Partido) session.get(Partido.class, id);
		return p;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Partido> findAll() {
		List<Partido> lista=null;
		Criteria crit = session.createCriteria(Partido.class);
		lista = crit.list();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> findByJornada(String jornada) {
		
		Criteria crit = session.createCriteria(Partido.class);
		crit.add(Restrictions.eq("jornada", Integer.parseInt(jornada)));
		return crit.list();
	}


	@SuppressWarnings("unchecked")
	public List<Partido> findByEquipo(String equipo) {
			
		Criteria crit = session.createCriteria(Partido.class);
		
		crit.createAlias("equipoByIdEquipoLocal", "local");
		crit.createAlias("equipoByIdEquipoVisitante", "visitante");
		
		crit.add(Restrictions.or(Restrictions.eq("local.nombre",equipo).ignoreCase(),
				Restrictions.eq("visitante.nombre",equipo).ignoreCase()));
		
		return crit.list();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void save(Partido entity) {
		Transaction tx=session.beginTransaction();
		session.saveOrUpdate(entity);
		tx.commit();
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
