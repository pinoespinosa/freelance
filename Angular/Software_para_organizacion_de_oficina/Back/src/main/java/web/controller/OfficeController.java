package web.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Auditoria;
import data.Requerimiento;
import io.swagger.annotations.ApiOperation;
import spring.AccesManager;
import spring.ProjectConstants;
import spring.AccesManager.Tareas;
import web.service.OfficeService;

@RestController
public class OfficeController {
	
	public static String OK = "{\"STATUS\":\"OK\"}";
	
	@Autowired
	private OfficeService officeService;
	@Autowired
	private AccesManager accessManager;
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/universidad", method = RequestMethod.GET)
	public List<String> getUniversidades() {
		return officeService.getUniversidList();
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/auditoria", method = RequestMethod.GET)
	public List<Auditoria> getAuditoria(
			@RequestHeader("Acces-Token") String token
			) throws AccessDeniedException {
		
		if (!accessManager.canDoIt(token, Tareas.AUDITORIA))
			throw new AccessDeniedException("Accion denegada");
		
		return officeService.getAuditoria();
	}
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/carrera", method = RequestMethod.GET)
	public List<String> getCarreras() {
		return officeService.getCarrerasList();
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/dondeEntero", method = RequestMethod.GET)
	public List<String> getDondeEntero() {
		return officeService.getDondeSeEnteroList();
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/universidad", method = RequestMethod.POST)
	@ResponseBody
	public String createUniversidad(@RequestBody final String universidad) {
		officeService.createUniversidad(universidad);
		return OfficeController.OK;
	}
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/carrera", method = RequestMethod.POST)
	@ResponseBody
	public String createCarrera(@RequestBody final String carrera) {
		officeService.createCarrera(carrera);
		return OfficeController.OK;
	}
	
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "/dondeEntero", method = RequestMethod.POST)
	@ResponseBody
	public String createDondeEntero(@RequestBody final String dondeEntero) {
		officeService.createDondeEntero(dondeEntero);
		return OfficeController.OK;
	}
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "", tags = "Internal")
	@RequestMapping(value = "{idCliente}/{idTrabajo}/requerimiento", method = RequestMethod.POST)
	@ResponseBody
	public Requerimiento addRequerimiento(@PathVariable final String idCliente, @PathVariable final String idTrabajo,
			@RequestBody final Requerimiento requerimiento) {
		officeService.addRequerimiento(idCliente, idTrabajo, requerimiento);
		return requerimiento;
	}

}
