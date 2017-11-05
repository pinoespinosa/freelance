package web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import data.Cliente;
import data.Trabajo;
import web.service.OfficeService;

@RestController
public class OfficeController {
	@Autowired
	private OfficeService officeService;

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public List<Cliente> getClientList(HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return officeService.getClientList();
	}

	@RequestMapping(value = "/client", method = RequestMethod.POST)
	@ResponseBody
	public String createCliente(@RequestBody final Cliente cliente, final HttpServletResponse response) {
		officeService.createCliente(cliente);
		return "OK";
	}

	@RequestMapping(value = "/universidad", method = RequestMethod.GET)
	public List<String> getUniversidades() {
		return officeService.getUniversidList();
	}

	@RequestMapping(value = "/universidad", method = RequestMethod.POST)
	@ResponseBody
	public String createUniversidad(@RequestBody final String universidad, final HttpServletResponse response) {
		officeService.createUniversidad(universidad);
		return "OK";
	}

	@RequestMapping(value = "/importCSV", method = RequestMethod.POST)
	@ResponseBody
	public String importCSV(@RequestParam(required = false) final MultipartFile file,
			final HttpServletResponse response) {
		officeService.importCSV(file);
		return "OK";
	}
	
	@RequestMapping(value = "/trabajo", method = RequestMethod.GET)
	public List<Cliente> getTrabajo(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return officeService.getClientList();
	}

	@RequestMapping(value = "{idCliente}/trabajo", method = RequestMethod.POST)
	@ResponseBody
	public String createTrabajo(@PathVariable final String idCliente, @RequestBody final Trabajo trabajo,
			final HttpServletResponse response) {
		officeService.createTrabajo(idCliente, trabajo);
		return "OK";
	}

}
