package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import web.service.CardCounterService;

@RestController
public class CardCounterController {
	
	@Autowired
	private CardCounterService officeService;

	@RequestMapping(value = "/car/moving", method = RequestMethod.GET)
	public String updateMovingCars() {
		return officeService.getHelloWorld();
	}
	
	@RequestMapping(value = "/car/waiting", method = RequestMethod.GET)
	public String updateWaitingCars() {
		return officeService.getHelloWorld();
	}
		
}
