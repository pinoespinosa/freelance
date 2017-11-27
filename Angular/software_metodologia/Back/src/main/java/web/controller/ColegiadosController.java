package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.CuerpoColegiado;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class ColegiadosController {

	@Autowired
	private IDataSource dataSource;
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado", method = RequestMethod.GET)
	public List<CuerpoColegiado> getCuerpoColegiadoList() {
		return dataSource.getCuerpoColegiadoList();
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/create", method = RequestMethod.POST)
	@ResponseBody
	public CuerpoColegiado createCuerpoColegiado(@RequestBody CuerpoColegiado user) {
		return dataSource.createCuerpoColegiado(user);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/edit", method = RequestMethod.POST)
	@ResponseBody
	public CuerpoColegiado editCuerpoColegiado(@RequestBody CuerpoColegiado user) {
		return dataSource.editCuerpoColegiado(user);
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
