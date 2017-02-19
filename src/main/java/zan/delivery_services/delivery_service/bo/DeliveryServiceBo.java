package zan.delivery_services.delivery_service.bo;

import java.util.List;

import zan.delivery_services.delivery_service.model.DeliveryService;

public interface DeliveryServiceBo {
	void save(DeliveryService service);
	void update(DeliveryService service);
	void delete(DeliveryService service);
	List<DeliveryService> getAllServices();
}
