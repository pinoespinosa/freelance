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
import data.Tema;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class TemasController {

	@Autowired
	private IDataSource dataSource;
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/abierto", method = RequestMethod.GET)
	public List<Tema> getTemaAbiertoList(@PathVariable final String cuerpoColegiadoID) {
		return dataSource.getTemaAbiertoList(cuerpoColegiadoID);
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/create", method = RequestMethod.POST)
	@ResponseBody
	public Tema createActa(@PathVariable final String cuerpoColegiadoID, @RequestBody Tema tema) {
		return dataSource.createTema(cuerpoColegiadoID, tema);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/edit", method = RequestMethod.POST)
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
