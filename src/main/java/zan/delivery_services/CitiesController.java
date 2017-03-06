package zan.delivery_services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zan.delivery_services.model.City;
import zan.delivery_services.repository.CityRepository;

@Controller
public class CitiesController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@RequestMapping("/cities")
    public String cities(Model model) {
        
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("id", "ID");
		columns.put("name", "NAME");
		model.addAttribute("columns", columns);
		
		List<City> cities = (List<City>) cityRepository.findAll();
		model.addAttribute("cities", cities);
        
		return "cities";
    }

}
