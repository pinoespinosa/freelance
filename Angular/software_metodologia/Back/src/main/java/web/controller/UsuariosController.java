package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Auth;
import data.CuerpoColegiado;
import data.Usuario;
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
	public List<Usuario> getUsuariosList() {
		return dataSource.getUsuariosList();
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/usuario/create", method = RequestMethod.POST)
	@ResponseBody
	public Usuario createUser(@RequestBody Usuario user) {
		return dataSource.createUser(user);
	}

	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/usuario/edit", method = RequestMethod.POST)
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
