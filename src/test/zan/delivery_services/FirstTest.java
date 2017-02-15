package test.zan.delivery_services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import zan.delivery_services.City;

public class FirstTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByRef() {
		City city = new City();
		assertNull(city.getRef());
		
		//SqlSession session = Db.getSession();
		//assertNull(session);
		
		//city.findByRef();
		//assertNull(city.getId());
	}

	@Test
	public void testIsNew() {
		City city = new City();
		assertTrue(city.isNew());
		
		city.setId(null);
		assertTrue(city.isNew());
		
		city.setId(1L);
		assertFalse(city.isNew());		
	}
}
