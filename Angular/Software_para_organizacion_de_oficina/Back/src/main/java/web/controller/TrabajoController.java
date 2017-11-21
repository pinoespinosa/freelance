package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Pago;
import data.Trabajo;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class TrabajoController {
	
	public static String OK = "{\"STATUS\":\"OK\"}";
	
	
	@Autowired
	private OfficeService officeService;


	/**
	 * Retorna un trabajo en base a un ID
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/{idTrabajo}", method = RequestMethod.GET)
	public Trabajo getTrabajo(@PathVariable final String idCliente, @PathVariable final String idTrabajo) {
		return officeService.getTrabajo(idCliente, idTrabajo);
	}

	/**
	 * Crea un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/trabajo", method = RequestMethod.POST)
	@ResponseBody
	public Trabajo createTrabajo(@PathVariable final String idCliente, @RequestBody final Trabajo trabajo) {
		return officeService.createTrabajo(idCliente, trabajo);
	}
	
	/**
	 * Edita in cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/trabajo/edit", method = RequestMethod.POST)
	@ResponseBody
	public Trabajo editTrabajo(@PathVariable final String idCliente, @RequestBody Trabajo trabajo) {
		return officeService.editTrabajo(idCliente, trabajo);
	}
	

	/**
	 * Modifica el estado de un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/status", method = RequestMethod.POST)
	public Trabajo getChangeTrabajoStatus(@PathVariable final String clienteID, @PathVariable final String trabajoID,
			@RequestParam(required = true) final String estado) {
		return officeService.getChangeTrabajoStatus(clienteID, trabajoID, estado);
	}

	/**
	 * Modifica la fecha de entrega de un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/fechaEntrega", method = RequestMethod.POST)
	public Trabajo updateFechaEntrega(@PathVariable final String clienteID, @PathVariable final String trabajoID,
			@RequestParam(required = true) final String fechaNueva) {
		return officeService.updateFechaEntrega(clienteID, trabajoID, fechaNueva);
	}
		
	/**
	 * Modifica el asesor de un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/asesor", method = RequestMethod.POST)
	public Trabajo updateAsesor(@PathVariable final String clienteID, @PathVariable final String trabajoID,
			@RequestParam(required = true) final String asesor) {
		return officeService.updateAsesor(clienteID, trabajoID, asesor);
	}	

	/**
	 * Modifica la fecha de entrega de un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/pago", method = RequestMethod.POST)
	public Pago addPago(@PathVariable final String clienteID, @PathVariable final String trabajoID,
			@RequestBody final Pago pago) {
		return officeService.addPago(clienteID, trabajoID, pago);
	}
	
}
