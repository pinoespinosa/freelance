package web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
			@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String pass) {
		return dataSource.auth(email, pass);
	}

	/**
	 * Crear User
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public Auth createUser(
			@RequestHeader("Acces-Token") String token,
			@RequestParam(required = true) final String nombre,
			@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String pass,
			@RequestParam(required = true) final String logo,
			@RequestParam(required = true) final Rol rol, 
			@RequestParam(required = true) final List<String> ccList

			) {
		return dataSource.create(pass,rol, Auth.getEmpresaID(token), ccList, nombre, email,logo);
	}
	
	/**
	 * Crear User
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public Auth editUser(
			@RequestHeader("Acces-Token") String token,
			@RequestParam(required = true) final Rol rol, 
			@RequestParam(required = true) final String user, 
			@RequestParam(required = true) final String pass) {
		return dataSource.editUser(user, pass,rol);
	}
	
	/**
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "loadFile", method = RequestMethod.POST)
	@ResponseBody
	public void readFromFile() {
		dataSource.readFromFile();
	}
	

}
