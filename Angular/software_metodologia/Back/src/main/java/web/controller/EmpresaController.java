package web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

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
		return dataSource.createEmpresa(empresa);
	}

	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/empresa/update", method = RequestMethod.POST)
	@ResponseBody
	public Empresa updateEmpresa(
			@RequestBody Empresa empresa,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.updateEmpresa(empresa);
	}
	
	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/empresa/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Empresa> listEmpresa(@RequestHeader("Acces-Token") String token) {
		return dataSource.listEmpresa(token);
	}
	
	/**
	 * Devuelve las estrategias
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/estrategias", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getEstrategias(
			@RequestHeader("Acces-Token") String token,
			@RequestParam String empresaID) {
		
		if ( !Strings.isNullOrEmpty(empresaID) && !empresaID.equals("null"))
			return dataSource.getEstrategias(empresaID);
		
		return new ArrayList<>();

	}
	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/estrategias", method = RequestMethod.POST)
	@ResponseBody
	public List<String> addEstrategia(
			@RequestParam String estrategia,
			@RequestParam String empresaID,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.createEstrategia(estrategia, Auth.getEmpresaID(token));
	}
	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/estrategias/delete", method = RequestMethod.POST)
	@ResponseBody
	public List<String> quitarEstrategia(
			@RequestParam String estrategia,
			@RequestParam String empresaID,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.quitarEstrategia(estrategia, empresaID);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Devuelve las estrategias
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/indicador", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getIndicador(
			@RequestHeader("Acces-Token") String token,
			@RequestParam String empresaID) {
		return dataSource.getIndicador(empresaID);
	}
	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/indicador", method = RequestMethod.POST)
	@ResponseBody
	public List<String> addIndicador(
			@RequestParam String indicador,
			@RequestParam String empresaID,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.createIndicador(indicador, Auth.getEmpresaID(token));
	}
	
	/**
	 * Crea una empresa
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/indicador/delete", method = RequestMethod.POST)
	@ResponseBody
	public List<String> quitarIndicador(
			@RequestParam String indicador,
			@RequestParam String empresaID,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.quitarIndicador(indicador, empresaID);
	}
	
	
	
	
	
	
	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
