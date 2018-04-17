package web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;
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
import data.DiaCalendar;
import data.Tarea;
import data.Tema;
import data.UsuarioActa;
import datasource.IDataSource;
import edu.emory.mathcs.backport.java.util.Collections;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class ActaController {

	@Autowired
	private IDataSource dataSource;

	public static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

	
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
	 * Retorna la lista de responsables
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/responsables", method = RequestMethod.GET)
	public List<UsuarioActa> getResponsables(@RequestHeader("Acces-Token") String token) {

		return dataSource.getResponsables(Auth.getEmpresaID(token));
	}
	

	/**
	 * Retorna la lista de las actas
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/acta/filtrada", method = RequestMethod.GET)
	public List<Tarea> getTareaFiltrada(
			@RequestHeader("Acces-Token") String token,
			@RequestParam String responsableId, @RequestParam String cuerpoColegiadoId) {

		return dataSource.getActaFiltrada(Auth.getEmpresaID(token), responsableId, cuerpoColegiadoId);
	}


	/**
	 * Retorna la lista de las actas
	 * 
	 * @throws ParseException
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/acta/filtrada/calendario", method = RequestMethod.GET)
	public List<List<DiaCalendar>> getTareasCalendario(@RequestHeader("Acces-Token") String token,
			@RequestParam String responsableId, @RequestParam String cuerpoColegiadoId, @RequestParam String mes) throws ParseException {

		List<List<DiaCalendar>> result = ejemplo(Auth.getEmpresaID(token), responsableId, cuerpoColegiadoId, mes.replace("-", "/"), Integer.parseInt(mes.split("-")[0])-1);

		return result;
	}
	
	
	private List<List<DiaCalendar>> ejemplo(String empresaId, String responsableId, String cuerpoColegiadoId, String mes, int mesSolo )
			throws ParseException {

		List<List<DiaCalendar>> result = new ArrayList<>();

		List<Tarea> aa = dataSource.getActaFiltrada(empresaId, responsableId, cuerpoColegiadoId);

		List<Tarea> aux = new ArrayList<>();

		for (Tarea tarea : aa) {
			if (tarea.getFechaCreacion().contains(mes))
				aux.add(tarea);
		}

		Comparator<Tarea> comparator = new Comparator<Tarea>() {

			@Override
			public int compare(Tarea o1, Tarea o2) {
				try {
					return formatoFecha.parse(o1.getFechaCreacion())
							.compareTo(formatoFecha.parse(o2.getFechaCreacion()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}
		};

		Collections.sort(aux, comparator);

		Calendar start = Calendar.getInstance();
		start.setTime(formatoFecha.parse("01/"+mes));

		int diaa = start.get(Calendar.DAY_OF_WEEK);

		int pos = 1;

		int dia = 1;

		int semana = 0;
		result.add(new ArrayList<>());

		while (pos != diaa) {
						
			result.get(0).add(new DiaCalendar());
			pos++;

		}

		while (start.get(Calendar.MONTH) == mesSolo) {

			while (pos <= 7) {
				DiaCalendar auz = new DiaCalendar();
				auz.setDetalle(dia + "");
				auz.setTareas(dataSource.getTareaDia(empresaId, responsableId, cuerpoColegiadoId, formatoFecha.format(start.getTime())));
				
				
				if (start.get(Calendar.MONTH) == mesSolo) {
					start.add(Calendar.DAY_OF_MONTH, 1);
					result.get(semana).add(auz);
				}
				pos++;
				dia++;

			}
			pos = 1;
			if (start.get(Calendar.MONTH) == mesSolo)
				result.add(new ArrayList<>());
			semana++;
		}

		return result;

	}
	
	
	/**
	 * Vista de consultas por temas
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "/acta/filtrada/temas", method = RequestMethod.GET)
	public List<Tema> getTareaFiltradaPorTemas(
			@RequestHeader("Acces-Token") String token,
			@RequestParam String actaId, @RequestParam String cuerpoColegiadoId, @RequestParam String estrategiaId) {

		return dataSource.getTareaFiltradaPorTemas(Auth.getEmpresaID(token), cuerpoColegiadoId, actaId, estrategiaId);
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
				token);

		String email = Auth.getUserEmail(token);
		
		List<Acta> filtradas = new ArrayList<>();

		for (CuerpoColegiado cc : ccList) {

			for (Acta acta : cc.getActas()) {
				if ("Citada".equals(acta.getEstado()) && email.equals(acta.getIntegranteAutorActa()))
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
	 * @throws IOException 
	 * @throws MessagingException 
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/{actaID}/close", method = RequestMethod.POST)
	@ResponseBody
	public Acta closeActa(
			@PathVariable final String cuerpoColegiadoID, 
			@PathVariable final String actaID,
			@RequestBody final Acta acta,
			@RequestHeader("Acces-Token") String token) throws IOException, MessagingException {

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

	
	/**
	 * Edita un acta
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "acta/editReunion", method = RequestMethod.POST)
	@ResponseBody
	public Acta editActaReunion(
			@RequestBody Acta acta,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.editActaReunion(acta, Auth.getEmpresaID(token));
	}
	
	
	
	
	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
