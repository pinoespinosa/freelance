package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.Auth;
import data.Auth.Rol;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class AuthController {

	@Autowired
	private IDataSource dataSource;

	/**
	 * Authentication
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public Auth auth(
			@RequestParam(required = true) final String user,
			@RequestParam(required = true) final String pass) {
		return dataSource.auth(user, pass);
	}

	/**
	 * Crear User
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public Auth createUser(
			@RequestParam(required = true) final String token,
			@RequestParam(required = true) final Rol rol, 
			@RequestParam(required = true) final String user, 
			@RequestParam(required = true) final String pass) {
		return dataSource.create(user, pass,rol);
	}
	
	/**
	 * Crear User
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public Auth editUser(
			@RequestParam(required = true) final String token,
			@RequestParam(required = true) final Rol rol, 
			@RequestParam(required = true) final String user, 
			@RequestParam(required = true) final String pass) {
		return dataSource.editUser(user, pass,rol);
	}

}
