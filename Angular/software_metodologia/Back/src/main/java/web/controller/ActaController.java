package web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;

import org.apache.http.auth.AUTH;
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
import data.UsuarioActa;
import data.Auth.Rol;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class ActaController {

	@Autowired
	private IDataSource dataSource;

	
	/**
	 * Retorna el acta
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "acta/{actaID}", method = RequestMethod.GET)
	public Acta getActa(
			@PathVariable final String actaID,
			@RequestHeader("Acces-Token") String token) {

		return dataSource.getActa(actaID, Auth.getEmpresaID(token));
	}
	
	/**
	 * Retorna el acta por fin en mente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "acta/filtroMente", method = RequestMethod.GET)
	public List<Acta> getActaFinMente(@RequestParam final String texto, @RequestHeader("Acces-Token") String token) {

		List<Acta> actasBD = dataSource.getActaFiltroMente(texto, Auth.getEmpresaID(token));

		String usuarioEmail = Auth.getUserEmail(token);

		if (Rol.SOLO_CONSULTA.equals(Auth.getUserRol(token))) {
			List<Acta> resultado = new ArrayList<>();

			for (Acta acta : actasBD) {
				boolean estuvo = false;
				for (UsuarioActa user : acta.getIntegrantes()) {
					if (!estuvo && user.getEmail().equals(usuarioEmail))
						estuvo = true;
				}

				if (estuvo) {
					resultado.add(acta);
				}
			}
			return resultado;
		}
		return actasBD;
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
	 * @throws UnsupportedEncodingException 
	 * @throws AddressException 
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/acta/create", method = RequestMethod.POST)
	@ResponseBody
	public Acta createActa(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestBody Acta acta,
			@RequestHeader("Acces-Token") String token) throws UnsupportedEncodingException, AddressException {
		
		boolean sendEmail = !(Rol.SUPER_ADMINISTRADOR.equals(Auth.getUserRol(token)) && !dataSource.getSendEmail());

		return dataSource.createActa(cuerpoColegiadoID, Auth.getEmpresaID(token), acta, Auth.getUserEmail(token), sendEmail);
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
	 * @throws AddressException 
	 * @throws IOException 
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/{actaID}/close", method = RequestMethod.POST)
	@ResponseBody
	public Acta closeActa(
			@PathVariable final String cuerpoColegiadoID, 
			@PathVariable final String actaID,
			@RequestBody final Acta acta,
			@RequestHeader("Acces-Token") String token) throws AddressException, IOException {

			boolean sendEmail = !(Rol.SUPER_ADMINISTRADOR.equals(Auth.getUserRol(token)) && !dataSource.getSendEmail());
		
		return dataSource.closeActa(cuerpoColegiadoID, actaID, Auth.getEmpresaID(token),acta, sendEmail);
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
