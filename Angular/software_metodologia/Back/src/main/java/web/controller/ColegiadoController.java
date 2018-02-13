package web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.Acta;
import data.Auth;
import data.Auth.Rol;
import data.CuerpoColegiado;
import data.UsuarioActa;
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
		List<CuerpoColegiado> aa =  dataSource.getCuerpoColegiadoList(Auth.getEmpresaID(token), token);
		
		List<CuerpoColegiado> aux = new ArrayList<>();
		for (CuerpoColegiado cuerpoColegiado : aa) {
			aux.add(cuerpoColegiado.clone());
		}
		
		if (Rol.SOLO_CONSULTA.equals(Auth.getUserRol(token)))
		{
			String usuarioEmail = Auth.getUserEmail(token);
			for (CuerpoColegiado cuerpoColegiado : aux) {
				List<Acta> act= new ArrayList<>();
				for (Acta acta : cuerpoColegiado.getActas()) {
					boolean estuvo = false;
					for (UsuarioActa user : acta.getIntegrantes()) {
						if (!estuvo && user.getEmail().equals(usuarioEmail))
							estuvo = true;
					}
					
					if (estuvo){
						act.add(acta);
					}
				}
				cuerpoColegiado.setActas(act);
			}
		}
		
		return aux;
	}

	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/lista", method = RequestMethod.GET)
	public List<CuerpoColegiado> getCuerpoColegiadoList(
			@RequestHeader("Acces-Token") String token, @RequestParam String empresaID) {
		
		if (empresaID != null && !empresaID.equals("null"))
			return dataSource.getCuerpoColegiadoList(empresaID);
		
		return new ArrayList<>();
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
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/{cuerpoColegiadoID}/otros", method = RequestMethod.GET)
	public List<CuerpoColegiado> getCuerpoColegiadoOtros(@PathVariable final String cuerpoColegiadoID,
			@RequestHeader("Acces-Token") String token) {
		List<CuerpoColegiado> list = dataSource.getCuerpoColegiadoList(Auth.getEmpresaID(token),
				token);

		CuerpoColegiado cc = new CuerpoColegiado();
		cc.setId(cuerpoColegiadoID);
		list.remove(cc);
		return list;
	}

	/**
	 * Crea un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/cuerpocolegiado/create", method = RequestMethod.POST)
	@ResponseBody
	public CuerpoColegiado createCuerpoColegiado(
			@RequestBody CuerpoColegiado user,
			@RequestParam String empresaID,
			@RequestHeader("Acces-Token") String token) {
		
		if (!Rol.SUPER_ADMINISTRADOR.equals(Auth.getUserRol(token)))
		{
			if (!empresaID.equals(Auth.getEmpresaID(token)))
				throw new RuntimeException("Creacion no permitida para este usuario");
		}
		
		return dataSource.createCuerpoColegiado(empresaID,user,token);
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
