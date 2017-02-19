package zan.delivery_services.delivery_service.dao;

import java.util.List;

import zan.delivery_services.delivery_service.model.DeliveryService;

public interface DeliveryServiceDao {
	void save(DeliveryService service);
	void update(DeliveryService service);
	void delete(DeliveryService service);
	List<DeliveryService> getAllServices();

}
