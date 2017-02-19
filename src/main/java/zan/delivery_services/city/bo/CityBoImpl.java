package zan.delivery_services.city.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zan.delivery_services.city.dao.CityDao;
import zan.delivery_services.city.model.City;

@Service("cityBo")
public class CityBoImpl implements CityBo {

	@Autowired
	CityDao cityDao;
	
	public void setStockDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}
	
	@Override
	public void save(City city) {
		cityDao.save(city);
	}

	@Override
	public void update(City city) {
		cityDao.update(city);
	}

	@Override
	public void delete(City city) {
		cityDao.delete(city);
	}

	@Override
	public List<City> getAllCities() {
		return cityDao.getAllCities();
	}

}
