package zan.delivery_services.city.bo;

import java.util.List;

import zan.delivery_services.city.model.City;

public interface CityBo {
	void save(City city);
	void update(City city);
	void delete(City city);
	List<City> getAllCities();
}
