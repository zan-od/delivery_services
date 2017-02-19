package zan.delivery_services.delivery_service.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zan.delivery_services.delivery_service.dao.DeliveryServiceDao;
import zan.delivery_services.delivery_service.model.DeliveryService;

@Service("deliveryServiceBo")
public class DeliveryServiceBoImpl implements DeliveryServiceBo {

	@Autowired
	DeliveryServiceDao deliveryServiceDao;
	
	public void setStockDao(DeliveryServiceDao deliveryServiceDao) {
		this.deliveryServiceDao = deliveryServiceDao;
	}
	
	@Override
	public void save(DeliveryService service) {
		deliveryServiceDao.save(service);
	}

	@Override
	public void update(DeliveryService service) {
		deliveryServiceDao.update(service);
	}

	@Override
	public void delete(DeliveryService service) {
		deliveryServiceDao.delete(service);
	}

	@Override
	public List<DeliveryService> getAllServices() {
		return deliveryServiceDao.getAllServices();
	}

}
