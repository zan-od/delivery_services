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
import javax.swing.SwingWorker;

import org.hibernate.Session;

import zan.delivery_services.api.NewPostAPIHelper;
import zan.delivery_services.api.NewPostAPIHelper.CityData;
import zan.delivery_services.db.ConnectionFactory;
import zan.delivery_services.gui.LoadDataWorker;

@Entity
@Table(name = "delivery_services")
public class DeliveryService implements DeliveryServiceAPI{
	private Long id;
	private String code;
	private String name;
	private String apiKey;
	
	private LoadDataWorker worker;
	private City lastCity;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "api_key")
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Transient
	public SwingWorker<?, ?> getWorker() {
		return worker;
	}
	
	@Transient
	public boolean isWorkerUsed() {
		return worker!=null;
	}
	
	public void setWorker(LoadDataWorker worker) {
		this.worker = worker;
	}
	
	public void insert() throws IOException{
		try(Session session = ConnectionFactory.getSession()){
			session.save(this);
		}
	}

	public static List<DeliveryService> getAll() throws IOException{
		List<DeliveryService> items = Arrays.asList();
		
		try(Session session = ConnectionFactory.getSession()){
			items = (List<DeliveryService>) session.createQuery("from DeliveryService").getResultList();
		}
		
		return items;
	}
	
	public static DeliveryService load(int id) throws IOException{
		try(Session session = ConnectionFactory.getSession()){
			return (DeliveryService) session.createQuery("from DeliveryService where id = :id").setParameter("id", id).getSingleResult();
		}
	}
	
	public void connect(){
		NewPostAPIHelper helper = new NewPostAPIHelper();
		helper.setApiKey(apiKey);
		List<NewPostAPIHelper.CityData> cities = helper.getCities();
		
		if (cities == null) {
			return;
		}
		
		int warehousesCount = cities.size();
		int i = 0;
		for (CityData cityData : cities) {
			City city = new City();
			
			try {
				city = City.findByRef(cityData.getRef());
			} catch (IOException e) {
				city = new City();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			city.setService(this);
			
			try {
				city.save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Saved city: " + city.getName());
			
			lastCity = city;
			
			if (worker!=null) {
				if (worker.isCancelled()) { break; }
				int progress = (int) ((i++)*100)/warehousesCount;
				worker.updateProgress(progress, lastCity==null?"":lastCity.getName());
			}
		}
	}
	
	@Override
	@Transient
	public List<City> getSities() {
		// TODO Auto-generated method stub
		connect();
		return null;
	}
	
	@Override
	public void getOffices() {
		// TODO Auto-generated method stub
		
	}

}
