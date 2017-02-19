package zan.delivery_services.city.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zan.delivery_services.city.model.City;
import zan.delivery_services.util.CustomHibernateDaoSupport;

@Repository("cityDao")
public class CityDaoImpl extends CustomHibernateDaoSupport implements CityDao {

	@Override
	public void save(City city) {
		getHibernateTemplate().save(city);
	}

	@Override
	public void update(City city) {
		getHibernateTemplate().update(city);
	}

	@Override
	public void delete(City city) {
		getHibernateTemplate().delete(city);
	}

	@Override
	public List<City> getAllCities() {
		return (List<City>) getHibernateTemplate().loadAll(City.class);
	}

}
