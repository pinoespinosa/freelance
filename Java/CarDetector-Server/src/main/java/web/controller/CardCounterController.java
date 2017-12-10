package web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.service.CardCounterService;

@RestController
public class CardCounterController {

	@Autowired
	private CardCounterService officeService;

	@RequestMapping(value = "/car/moving1", method = RequestMethod.POST)
	public void updateMovingCars1(@RequestParam String congestion, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("Congestion1:" + congestion );
		officeService.updateMovingCars("1", congestion);
	}

	@RequestMapping(value = "/car/moving2", method = RequestMethod.POST)
	public void updateMovingCars2(@RequestParam String congestion, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("Congestion2:" + congestion );
		officeService.updateMovingCars("2",congestion);
	}
	
	@RequestMapping(value = "/car/waiting", method = RequestMethod.GET)
	public String updateWaitingCars(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return officeService.updateWaitingCars();
	}

	@RequestMapping(value = "/semaphore/time/min/{color}", method = RequestMethod.GET)
	public void updateTimeMin(@PathVariable final ColorSemaforo color, @RequestParam int time,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		officeService.updateTimeMin(time, color);
	}

	@RequestMapping(value = "/semaphore/time/max/{color}", method = RequestMethod.GET)
	public void updateTimeMax(@PathVariable final ColorSemaforo color, @RequestParam int time,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		officeService.updateTimeMax(time, color);
	}

	@RequestMapping(value = "/semaphore/color1", method = RequestMethod.GET)
	public String getColor(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getColor("1") + "\"}";
	}
	
	@RequestMapping(value = "/semaphore/color2", method = RequestMethod.GET)
	public String getColor2(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getColor("2") + "\"}";
	}
	
	@RequestMapping(value = "/semaphore/cant1", method = RequestMethod.GET)
	public String getCant1(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getCant("1") + "\"}";
	}
	
	@RequestMapping(value = "/semaphore/cant2", method = RequestMethod.GET)
	public String getCant2(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getCant("2") + "\"}";
	}
	
}
