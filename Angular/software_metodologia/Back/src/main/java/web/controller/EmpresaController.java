package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Auth;
import data.Empresa;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class EmpresaController {

	@Autowired
	private IDataSource dataSource;
	
	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/empresa/create", method = RequestMethod.POST)
	@ResponseBody
	public Empresa createEmpresa(
			@RequestBody Empresa empresa,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.createEmpresa(Auth.getEmpresaID(token),empresa);
	}


	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
