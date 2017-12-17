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
import data.CuerpoColegiado;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class ActaController {

	@Autowired
	private IDataSource dataSource;

	
	/**
	 * Retorna la lista de las actas
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "acta/{actaID}", method = RequestMethod.GET)
	public Acta getActa(
			@PathVariable final String actaID,
			@RequestHeader("Acces-Token") String token) {

		return dataSource.getActa(actaID, Auth.getEmpresaID(token));
	}
	
	
	
	
	
	
	/**
	 * Retorna la lista de las actas
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta", method = RequestMethod.GET)
	public List<Acta> getActaList(
			@PathVariable final String cuerpoColegiadoID,
			@RequestHeader("Acces-Token") String token) {

		return dataSource.getActaList(cuerpoColegiadoID, Auth.getEmpresaID(token));
	}

	
	/**
	 * Retorna el ultimo acta
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/last", method = RequestMethod.GET)
	public Acta getActaLast(
			@PathVariable final String cuerpoColegiadoID,
			@RequestHeader("Acces-Token") String token) {

		
		
		List<Acta> list = dataSource.getActaList(cuerpoColegiadoID, Auth.getEmpresaID(token));
		if (!list.isEmpty())
			return list.get(list.size()-1);
		else
			return new Acta();
	}
	
	/**
	 * Retorna la lista de las actas
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "acta/citada", method = RequestMethod.GET)
	public List<Acta> getActaCitadaList(@RequestHeader("Acces-Token") String token) {

		List<CuerpoColegiado> ccList = dataSource.getCuerpoColegiadoList(Auth.getEmpresaID(token),
				Auth.getCuerpoColegiadosList(token));

		List<Acta> filtradas = new ArrayList<>();

		for (CuerpoColegiado cc : ccList) {

			for (Acta acta : cc.getActas()) {
				if ("Citada".equals(acta.getEstado()))
					filtradas.add(acta);
			}

		}

		return filtradas;
	}
	
	/**
	 * Retorna la ultima acta creada
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/invite", method = RequestMethod.POST)
	@ResponseBody
	public Acta getLastActa(
			@PathVariable final String cuerpoColegiadoID,
			@RequestHeader("Acces-Token") String token) {
		
		return dataSource.getLastActa(cuerpoColegiadoID, Auth.getEmpresaID(token));
	}
	

	
	/**
	 * Crea un acta y envia las invitaciones
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/create", method = RequestMethod.POST)
	@ResponseBody
	public Acta createActa(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestBody Acta user,
			@RequestHeader("Acces-Token") String token) {

		return dataSource.createActa(cuerpoColegiadoID, Auth.getEmpresaID(token), user);
	}

	
	/**
	 * Crea un acta y envia las invitaciones
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/{actaID}/updatePaso", method = RequestMethod.POST)
	@ResponseBody
	public Acta updatePaso(
			@PathVariable final String cuerpoColegiadoID, 
			@PathVariable final String actaID, 
			@RequestParam final String paso,
			@RequestHeader("Acces-Token") String token) {

		return dataSource.updatePaso(cuerpoColegiadoID, actaID, paso, Auth.getEmpresaID(token));
	}
	
	
	/**
	 * Edita un acta
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/{actaID}/updateIntegrantes", method = RequestMethod.POST)
	@ResponseBody
	public Acta updateIntegrantes(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestBody Acta user,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.editActa(cuerpoColegiadoID, user, Auth.getEmpresaID(token));
	}
	
	
	
	
	/**
	 * Edita un acta
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/edit", method = RequestMethod.POST)
	@ResponseBody
	public Acta editActa(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestBody Acta user,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.editActa(cuerpoColegiadoID, user, Auth.getEmpresaID(token));
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
