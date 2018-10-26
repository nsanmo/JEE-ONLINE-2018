package dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import domain.Equipo;
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


	public void save(Partido partido, Equipo local, Equipo visitante) {
		// Guardar partido y equipos desde ambiente transaccional
		
		// Si local y visitante NO no null:
		// Asociamos ambos equipos al partido 
		//   Ejemplo: partido.setEquipoByIdEquipoLocal(local)
		Transaction tx = session.beginTransaction(); 
		if(local != null) partido.setEquipoByIdEquipoLocal(local);
		if(visitante != null) partido.setEquipoByIdEquipoVisitante(visitante);
		// Abrimos transaccion
		// Guardamos (SaveOrUpdate) partido
		// commit
		session.saveOrUpdate(partido);
		
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
