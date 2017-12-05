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

	@RequestMapping(value = "/car/moving", method = RequestMethod.POST)
	public String updateMovingCars(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("Se actulizo la cantidad de autos");
		return officeService.updateMovingCars();
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

	@RequestMapping(value = "/semaphore/color", method = RequestMethod.GET)
	public String getColor(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getColor() + "\"}";
	}
}
