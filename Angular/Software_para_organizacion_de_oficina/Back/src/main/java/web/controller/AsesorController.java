package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Asesor;
import data.Cliente;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class AsesorController {

	@Autowired
	private OfficeService officeService;

	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/asesor", method = RequestMethod.GET)
	public List<Asesor> getAsesorList() {
		return officeService.getAsesorList();
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/asesor/create", method = RequestMethod.POST)
	@ResponseBody
	public Asesor createCliente(@RequestBody Asesor user) {
		return officeService.createAsesor(user);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/asesor/edit", method = RequestMethod.POST)
	@ResponseBody
	public Cliente editCliente(@RequestBody Cliente user) {
		return officeService.editCliente(user);
	}

}
