package zan.delivery_services.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cities")
public class City implements Serializable {

	private static final long serialVersionUID = 6975863085133461581L;
	
	private Long id;
	private String name;
	private String ref;
	private DeliveryService service;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "ref")
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Transient
	//@Column(name = "service")
	public DeliveryService getService() {
		return service;
	}
	public void setService(DeliveryService service) {
		this.service = service;
	}

}
