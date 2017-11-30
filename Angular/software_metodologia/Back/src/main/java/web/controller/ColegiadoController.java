package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Auth;
import data.CuerpoColegiado;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class ColegiadoController {

	@Autowired
	private IDataSource dataSource;
	
	/**
	 * 
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado", method = RequestMethod.GET)
	public List<CuerpoColegiado> getCuerpoColegiadoList(
			@RequestHeader("Acces-Token") String token) {
		return dataSource.getCuerpoColegiadoList(Auth.getEmpresaID(token), Auth.getCuerpoColegiadosList(token));
	}

	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/{cuerpoColegiadoID}", method = RequestMethod.GET)
	public CuerpoColegiado getCuerpoColegiado(
			@PathVariable final String cuerpoColegiadoID,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.getCuerpoColegiado(cuerpoColegiadoID, Auth.getEmpresaID(token));
	}
	
	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/create", method = RequestMethod.POST)
	@ResponseBody
	public CuerpoColegiado createCuerpoColegiado(
			@RequestBody CuerpoColegiado user,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.createCuerpoColegiado(Auth.getEmpresaID(token),user);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/edit", method = RequestMethod.POST)
	@ResponseBody
	public CuerpoColegiado editCuerpoColegiado(
			@RequestBody CuerpoColegiado user,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.editCuerpoColegiado(user,Auth.getEmpresaID(token));
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
