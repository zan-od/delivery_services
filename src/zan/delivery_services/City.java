package zan.delivery_services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import zan.delivery_services.db.Db;

public class City {
	private Integer id;
	private String name;
	private String ref;
	private DeliveryService service;
	
	public boolean isNew(){
		return (getId() == null);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public DeliveryService getService() {
		return service;
	}
	public void setService(DeliveryService service) {
		this.service = service;
	}
	
	public static City findByRef(String ref) throws IOException{
		SqlSession session = Db.getSession();
		City city = session.selectOne("cityMapper.findByRef", ref);
		session.close();
		
		return city;
	}
	
	public static City findById(int id) throws IOException{
		SqlSession session = Db.getSession();
		City city = session.selectOne("cityMapper.findById", id);
		session.close();
		
		return city;
	}
	
	public void save() throws IOException{
		SqlSession session = Db.getSession();
		
		if (isNew()){
			session.insert("insertCity", this);
		} else {
			session.update("updateCity", this);
		}
		session.commit();
		session.close();
	}

	public static List<City> getAll() throws IOException{
		List<City> items = Arrays.asList();
		
		SqlSession session = Db.getSession();
		items = session.selectList("cityMapper.selectAll");
		
		return items;
	}
}
