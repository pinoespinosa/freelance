package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Acta;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class ActaController {

	@Autowired
	private IDataSource dataSource;
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta", method = RequestMethod.GET)
	public List<Acta> getActaList(@PathVariable final String cuerpoColegiadoID) {
		return dataSource.getActaList(cuerpoColegiadoID);
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/create", method = RequestMethod.POST)
	@ResponseBody
	public Acta createActa(@PathVariable final String cuerpoColegiadoID, @RequestBody Acta user) {
		return dataSource.createActa(cuerpoColegiadoID, user);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/edit", method = RequestMethod.POST)
	@ResponseBody
	public Acta editActa(@PathVariable final String cuerpoColegiadoID, @RequestBody Acta user) {
		return dataSource.editActa(cuerpoColegiadoID, user);
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
