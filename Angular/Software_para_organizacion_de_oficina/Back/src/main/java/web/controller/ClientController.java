package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Cliente;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class ClientController {

	@Autowired
	private OfficeService officeService;

	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public List<Cliente> getClientList() {
		return officeService.getClientList();
	}

	/**
	 * Retorna un cliente en base al ID
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
	public Cliente getClient(@PathVariable final String id) {
		return officeService.getClient(id);
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/create", method = RequestMethod.POST)
	@ResponseBody
	public Cliente createCliente(@RequestBody Cliente user) {
		return officeService.createCliente(user);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/edit", method = RequestMethod.POST)
	@ResponseBody
	public Cliente editCliente(@RequestBody Cliente user) {
		return officeService.editCliente(user);
	}

}
