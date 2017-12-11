package web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import spring.SemaforoConfig;
import web.service.CardCounterService;

@RestController
public class CardCounterController {

	@Autowired
	private CardCounterService officeService;

	@ApiOperation(tags = "Vehiculos Detectados", notes = "Mediante este servicio el programa que detecta vehiculos informa la cantidad de vehiculos visualizados en la interseccion 1", value = "")
	@RequestMapping(value = "/car/moving1", method = RequestMethod.POST)
	public void updateMovingCars1(@RequestParam String congestion, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("Congestion1:" + congestion);
		officeService.updateMovingCars("1", congestion);
	}

	@ApiOperation(tags = "Vehiculos Detectados", notes = "Mediante este servicio el programa que detecta vehiculos informa la cantidad de vehiculos visualizados en la interseccion 2", value = "")
	@RequestMapping(value = "/car/moving2", method = RequestMethod.POST)
	public void updateMovingCars2(@RequestParam String congestion, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("Congestion2:" + congestion);
		officeService.updateMovingCars("2", congestion);
	}

	@ApiOperation(tags = "Vehiculos Detectados", notes = "Informa la cantidad de vehiculos detectados al front", value = "")
	@RequestMapping(value = "/semaphore/cant1", method = RequestMethod.GET)
	public String getCant1(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getCant("1") + "\"}";
	}

	@ApiOperation(tags = "Vehiculos Detectados", notes = "Informa la cantidad de vehiculos detectados al front", value = "")
	@RequestMapping(value = "/semaphore/cant2", method = RequestMethod.GET)
	public String getCant2(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getCant("2") + "\"}";
	}

	// ***************************** SEMAFORO ******************************

	@ApiOperation(tags = "Semaforo", notes = "Informa el color de el semaforo 1 en este instante", value = "fdsf")
	@RequestMapping(value = "/semaphore/color1", method = RequestMethod.GET)
	public String getColor(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getColor("1") + "\"}";
	}

	@ApiOperation(tags = "Semaforo", notes = "Informa el color de el semaforo 2 en este instante", value = "")
	@RequestMapping(value = "/semaphore/color2", method = RequestMethod.GET)
	public String getColor2(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return "{ \"id\":\"" + officeService.getColor("2") + "\"}";
	}
	
	@ApiOperation(tags = "Semaforo", notes = "Metodo para configurar los tiempos de corte del semaforo", value = "")
	@RequestMapping(value = "/semaphore/config", method = RequestMethod.POST)
	@ResponseBody
	public void configSemaforos(@RequestBody SemaforoConfig config, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		officeService.configSemaforos(config);
	}
	
	@ApiOperation(tags = "Semaforo", notes = "Metodo ver la configuracion de los tiempos del semaforo", value = "")
	@RequestMapping(value = "/semaphore/config", method = RequestMethod.GET)
	public SemaforoConfig getConfigSemaforos(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return officeService.getConfigSemaforos();
	}
}
