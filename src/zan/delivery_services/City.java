package zan.delivery_services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;

import zan.delivery_services.db.ConnectionFactory;

@Entity
@Table(name = "cities")
public class City{
	private Long id;
	private String name;
	private String ref;
	private DeliveryService service;
	
	@Transient
	public boolean isNew(){
		return (getId() == null);
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "ref")
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Transient
	//@Column(name = "service")
	public DeliveryService getService() {
		return service;
	}
	public void setService(DeliveryService service) {
		this.service = service;
	}
	
	public static City findByRef(String ref) throws IOException{
		try(Session session = ConnectionFactory.getSession()){
			return (City) session.createQuery("from City where ref = :ref").setParameter("ref", ref).getSingleResult();
		}
	}
	
	public static City findById(int id) throws IOException{
		try(Session session = ConnectionFactory.getSession()){
			return (City) session.createQuery("from City where id = :id").setParameter("id", id).getSingleResult();
		}
	}
	
	public void save() throws IOException{
		try(Session session = ConnectionFactory.getSession()){
			session.save(this);
		}
	}

	public static List<City> getAll() throws IOException{
		List<City> items = Arrays.asList();
		
		try(Session session = ConnectionFactory.getSession()){
			items = (List<City>) session.createQuery("from City").getResultList();
		}
		
		return items;
	}
}
