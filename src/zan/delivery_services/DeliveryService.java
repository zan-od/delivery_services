package zan.delivery_services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;
import org.apache.ibatis.session.SqlSession;
import zan.delivery_services.api.NewPostAPIHelper;
import zan.delivery_services.api.NewPostAPIHelper.CityData;
import zan.delivery_services.db.Db;
import zan.delivery_services.gui.LoadDataWorker;

public class DeliveryService implements DeliveryServiceAPI{
	private Integer id;
	private String code;
	private String name;
	
	private LoadDataWorker worker;
	private City lastCity;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public SwingWorker<?, ?> getWorker() {
		return worker;
	}
	
	public boolean isWorkerUsed() {
		return worker!=null;
	}
	
	public void setWorker(LoadDataWorker worker) {
		this.worker = worker;
	}
	
	public void insert() throws IOException{
		SqlSession session = Db.getSession();
		if (session == null) return;
		session.insert("deliveryServiceMapper.insertDeliveryService", this);
		session.commit();
	}

	public static List<DeliveryService> getAll() throws IOException{
		List<DeliveryService> items = Arrays.asList();
		
		SqlSession session = Db.getSession();
		if (session == null) return items;
		items = session.selectList("deliveryServiceMapper.selectAll");
		
		return items;
	}
	
	public void connect(){
		NewPostAPIHelper helper = new NewPostAPIHelper();
		List<NewPostAPIHelper.CityData> cities = helper.getCities();
		
		if (cities == null) {
			return;
		}
		
		int warehousesCount = cities.size();
		int i = 0;
		for (CityData cityData : cities) {
			City savedCity = new City();
			
			try {
				savedCity = City.findByRef(cityData.getRef());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			savedCity.setService(this);
			
			try {
				savedCity.save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Saved city: " + savedCity.getName());
			
			lastCity = savedCity;
			
			if (worker!=null) {
				if (worker.isCancelled()) { break; }
				int progress = (int) ((i++)*100)/warehousesCount;
				worker.updateProgress(progress, lastCity==null?"":lastCity.getName());
			}
		}
	}
	
	@Override
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
