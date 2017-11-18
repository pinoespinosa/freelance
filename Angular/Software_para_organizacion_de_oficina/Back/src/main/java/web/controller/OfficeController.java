package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Requerimiento;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class OfficeController {
	
	public static String OK = "{\"STATUS\":\"OK\"}";
	
	@Autowired
	private OfficeService officeService;

	
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
		return OfficeController.OK;
	}
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/carrera", method = RequestMethod.POST)
	@ResponseBody
	public String createCarrera(@RequestBody final String carrera) {
		officeService.createCarrera(carrera);
		return OfficeController.OK;
	}
	
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/dondeEntero", method = RequestMethod.POST)
	@ResponseBody
	public String createDondeEntero(@RequestBody final String dondeEntero) {
		officeService.createDondeEntero(dondeEntero);
		return OfficeController.OK;
	}
	
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{idCliente}/{idTrabajo}/requerimiento", method = RequestMethod.POST)
	@ResponseBody
	public Requerimiento addRequerimiento(@PathVariable final String idCliente, @PathVariable final String idTrabajo,
			@RequestBody final Requerimiento requerimiento) {
		officeService.addRequerimiento(idCliente, idTrabajo, requerimiento);
		return requerimiento;
	}

}
