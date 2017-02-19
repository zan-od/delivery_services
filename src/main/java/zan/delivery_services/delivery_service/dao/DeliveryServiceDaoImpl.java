package zan.delivery_services.delivery_service.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import zan.delivery_services.delivery_service.model.DeliveryService;
import zan.delivery_services.util.CustomHibernateDaoSupport;

@Repository("deliveryServiceDao")
public class DeliveryServiceDaoImpl extends CustomHibernateDaoSupport implements DeliveryServiceDao {

	//@Override
	public void save(DeliveryService service) {
		getHibernateTemplate().save(service);
	}

	@Override
	public void update(DeliveryService service) {
		getHibernateTemplate().update(service);
	}

	@Override
	public void delete(DeliveryService service) {
		getHibernateTemplate().delete(service);
	}

	@Override
	public List<DeliveryService> getAllServices() {
		return (List<DeliveryService>) getHibernateTemplate().loadAll(DeliveryService.class);
	}

}
