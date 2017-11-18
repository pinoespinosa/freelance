package web.controller;

import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Cliente;
import data.Requerimiento;
import data.Trabajo;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;
import web.service.OfficeService;

@RestController
public class ReportsController {
	
	public static String OK = "{\"STATUS\":\"OK\"}";
	
	
	@Autowired
	private OfficeService officeService;

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/filtredPagos", method = RequestMethod.GET)
	public List<Cliente> getClientNuevosList(@RequestParam(required = true) final String fechaDesde,
			@RequestParam(required = true) final String fechaHasta) {
		return officeService.getClientNuevosList(fechaDesde, fechaHasta);
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/sells/period", method = RequestMethod.GET)
	public Hashtable<String, List<String>> getLastSellByTime(@RequestParam(required = true) final int cantidadDias) {
		return officeService.getLastSellByTime(cantidadDias);
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/sells/period/chash/newClients", method = RequestMethod.GET)
	public synchronized List<Float> getSellsCashByTimeNewClients(@RequestParam(required = true) final int cantidadDias,
			@RequestParam(required = true) final int cantidadValores) {
		return officeService.getSellsCashByTimeNewClients(cantidadDias, cantidadValores);
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/sells/period/chash/oldClients", method = RequestMethod.GET)
	public synchronized List<Float> getSellsCashByTimeOldClients(@RequestParam(required = true) final int cantidadDias,
			@RequestParam(required = true) final int cantidadValores) {
		return officeService.getSellsCashByTimeOldClients(cantidadDias, cantidadValores);
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/client/sells/period/amount/newClients", method = RequestMethod.GET)
	public synchronized List<Float> getSellsAmmountByTimeNewClients(
			@RequestParam(required = true) final int cantidadDias,
			@RequestParam(required = true) final int cantidadValores) {
		return officeService.getSellsAmmountByTimeNewClients(cantidadDias, cantidadValores);
	}
}
