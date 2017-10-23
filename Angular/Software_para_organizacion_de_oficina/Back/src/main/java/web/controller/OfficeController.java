package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Cliente;
import web.service.OfficeService;

@RestController
public class OfficeController {
	@Autowired
	private OfficeService officeService;


	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public List<Cliente> getClientList() {
		return officeService.getClientList();
	}

	@RequestMapping(value = "/universidad", method = RequestMethod.GET)
	public List<String> getUniversidades() {
		return officeService.getUniversidList();
	}
	
	@RequestMapping(value = "/client", method = RequestMethod.POST)
	@ResponseBody
	public String createCliente(@RequestBody final Cliente cliente, final HttpServletResponse response) {
		officeService.createCliente(cliente);
		return "OK";
	}
	
	@RequestMapping(value = "/universidad", method = RequestMethod.POST)
	@ResponseBody
	public String createUniversidad(@RequestBody final String universidad, final HttpServletResponse response) {
		officeService.createUniversidad(universidad);
		return "OK";
	}
	

}
