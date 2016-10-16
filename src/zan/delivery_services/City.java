package zan.delivery_services;

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
	
	public void findByRef(){
		SqlSession session = Db.getSession();
		if (session == null) return;
		City found = session.selectOne("cityMapper.findByRef", this);
		if(found==null){
			return;
		}
		if (found.getId()!=0){
			setId(found.getId());
		}
		session.close();
	}
	
	public void save(){
		SqlSession session = Db.getSession();
		if (session == null) return;
		
		if (isNew()){
			session.insert("insertCity", this);
		} else {
			session.update("updateCity", this);
		}
		session.commit();
		session.close();
	}

	public void load(){
		SqlSession session = Db.getSession();
		if (session == null) return;
		
		if (isNew()) return;
		
		City result = session.selectOne("loadCity", this);
		session.close();
		
		setName(result.getName());
		setRef(result.getRef());
		setService(result.getService());
	}
	
	public static List<City> getAll(){
		List<City> items = Arrays.asList();
		
		SqlSession session = Db.getSession();
		if (session == null) return items;
		items = session.selectList("cityMapper.selectAll");
		
		return items;
	}
}
