package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Trabajo;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class OfficeController {
	@Autowired
	private OfficeService officeService;

	// ------------------------------ CLIENTE ------------------------------

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public List<Cliente> getClientList() {
		return officeService.getClientList();
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public Cliente getClient(@PathVariable final String id) {
		return officeService.getClient(id);
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/create", method = RequestMethod.POST)
	@ResponseBody
	public Cliente createCliente(@RequestBody Cliente user) {
		return officeService.createCliente(user);

	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/status", method = RequestMethod.POST)
	public Trabajo getChangeTrabajoStatus(@PathVariable final String clienteID, @PathVariable final String trabajoID,
			@RequestParam(required = true) final String estado) {
		return officeService.getChangeTrabajoStatus(clienteID, trabajoID, estado);
	}

	// ---------------------------------- CSV ----------------------------------

	@ApiOperation(value = "Get specific Student in the System ", tags = "Import CSV")
	@RequestMapping(value = "/importCSV", method = RequestMethod.POST)
	@ResponseBody
	public String importCSV(@RequestParam(required = true) final MultipartFile file) {
		officeService.importCSV(file);
		return "OK";
	}

	@ApiOperation(value = "Get specific Student in the System ", tags = "Export CSV")
	@RequestMapping(value = "/exportCSV", method = RequestMethod.POST)
	@ResponseBody
	public String exportCSV(@RequestParam(required = true) final MultipartFile file) {
		officeService.importCSV(file);
		return "OK";
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/universidad", method = RequestMethod.GET)
	public List<String> getUniversidades() {
		return officeService.getUniversidList();
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/carrera", method = RequestMethod.GET)
	public List<String> getCarreras() {
		return officeService.getCarrerasList();
	}	
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/dondeEntero", method = RequestMethod.GET)
	public List<String> getDondeEntero() {
		return officeService.getDondeSeEnteroList();
	}	
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/universidad", method = RequestMethod.POST)
	@ResponseBody
	public String createUniversidad(@RequestBody final String universidad) {
		officeService.createUniversidad(universidad);
		return "OK";
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/trabajo", method = RequestMethod.GET)
	public List<Cliente> getTrabajo() {
		return officeService.getClientList();
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/{idTrabajo}", method = RequestMethod.GET)
	public Trabajo getTrabajo(@PathVariable final String idCliente, @PathVariable final String idTrabajo) {
		return officeService.getTrabajo(idCliente, idTrabajo);
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/trabajo", method = RequestMethod.POST)
	@ResponseBody
	public String createTrabajo(@PathVariable final String idCliente, @RequestBody final Trabajo trabajo) {
		officeService.createTrabajo(idCliente, trabajo);
		return "OK";
	}

}
