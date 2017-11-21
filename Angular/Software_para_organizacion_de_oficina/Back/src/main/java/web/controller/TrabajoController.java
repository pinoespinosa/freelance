package web.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Pago;
import data.Trabajo;
import io.swagger.annotations.ApiOperation;
import spring.AccesManager;
import spring.AccesManager.Tareas;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class TrabajoController {
	
	public static String OK = "{\"STATUS\":\"OK\"}";
	
	
	@Autowired
	private OfficeService officeService;

	@Autowired
	private AccesManager accessManager;
	

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
	public Trabajo createTrabajo(
			@PathVariable final String idCliente, 
			@RequestBody final Trabajo trabajo,
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {

		if (!accessManager.canDoIt(token, Tareas.CREAR_TRABAJO))
			throw new AccessDeniedException("Accion denegada");

		return officeService.createTrabajo(idCliente, trabajo);
	}
	
	/**
	 * Edita in cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/trabajo/edit", method = RequestMethod.POST)
	@ResponseBody
	public Trabajo editTrabajo(
			@PathVariable final String idCliente, 
			@RequestBody Trabajo trabajo,
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {
		
		if (!accessManager.canDoIt(token, Tareas.EDITAR_TRABAJO))
			throw new AccessDeniedException("Accion denegada");
		
		return officeService.editTrabajo(idCliente, trabajo);
	}
	

	/**
	 * Modifica el estado de un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/status", method = RequestMethod.POST)
	public Trabajo getChangeTrabajoStatus(
			@PathVariable final String clienteID, 
			@PathVariable final String trabajoID,
			@RequestParam(required = true) final String estado,
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {
		
		if (!accessManager.canDoIt(token, Tareas.CAMBIO_ESTADO))
			throw new AccessDeniedException("Accion denegada");
		
		return officeService.getChangeTrabajoStatus(clienteID, trabajoID, estado);
	}

	/**
	 * Modifica la fecha de entrega de un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/fechaEntrega", method = RequestMethod.POST)
	public Trabajo updateFechaEntrega(
			@PathVariable final String clienteID, 
			@PathVariable final String trabajoID,
			@RequestParam(required = true) final String fechaNueva, 
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {
		
		if (!accessManager.canDoIt(token, Tareas.CAMBIAR_FECHA))
			throw new AccessDeniedException("Accion denegada");
		
		return officeService.updateFechaEntrega(clienteID, trabajoID, fechaNueva);
	}
		
	/**
	 * Modifica el asesor de un trabajo
	 * @throws AccessDeniedException 
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/asesor", method = RequestMethod.POST)
	public Trabajo updateAsesor(
			@PathVariable final String clienteID, 
			@PathVariable final String trabajoID,
			@RequestParam(required = true) final String asesor, 
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {
		
		if (!accessManager.canDoIt(token, Tareas.CAMBIAR_ASESOR))
			throw new AccessDeniedException("Accion denegada");
		
		return officeService.updateAsesor(clienteID, trabajoID, asesor);
	}	

	/**
	 * Agregar pago a un trabajo
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/{clienteID}/{trabajoID}/pago", method = RequestMethod.POST)
	public Pago addPago(
			@PathVariable final String clienteID, 
			@PathVariable final String trabajoID,
			@RequestBody final Pago pago,
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {
		
		if (!accessManager.canDoIt(token, Tareas.AGREGAR_PAGO))
			throw new AccessDeniedException("Accion denegada");
		
		return officeService.addPago(clienteID, trabajoID, pago);
	}
	
}
