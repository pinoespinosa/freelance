package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.Auth;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class UsuariosController {

	@Autowired
	private IDataSource dataSource;
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/usuario", method = RequestMethod.GET)
	public List<Auth> getUsuariosList(
			@RequestHeader("Acces-Token") String token,
			@RequestParam String cuerpoColegiadoID) {
		return dataSource.getUsuariosList(cuerpoColegiadoID);
	}


	/**
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "acta/usuario", method = RequestMethod.GET)
	public List<Auth> getUsuariosActaList(@RequestHeader("Acces-Token") String token, @RequestParam String actaID) {
		
		String[] ccId = actaID.split("_")[1].split("-");
		return dataSource.getUsuariosList(ccId[0] + "-" + ccId[1]);
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
