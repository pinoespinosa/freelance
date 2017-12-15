package web.controller;

import java.util.Collections;
import java.util.Comparator;
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
import data.Tarea;
import data.Tema;
import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;
import spring.ProjectConstants;

@RestController
public class TemasController {

	@Autowired
	private IDataSource dataSource;
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/abierto", method = RequestMethod.GET)
	public List<Tema> getTemaAbiertoList(
			@PathVariable final String cuerpoColegiadoID,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.getTemaAbiertoList(cuerpoColegiadoID,Auth.getEmpresaID(token));
	}

	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/historico", method = RequestMethod.GET)
	public List<Tema> getTemaListConsulta(
			@PathVariable final String cuerpoColegiadoID,
			@RequestParam final String actaID,
			@RequestHeader("Acces-Token") String token) {
		
		
		
		return dataSource.getTemaListConsulta(cuerpoColegiadoID, actaID, Auth.getEmpresaID(token));

	}
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{actaID}/acta/tema/abierto", method = RequestMethod.GET)
	public List<Tema> getTemaAbiertoFromActaList(
			@PathVariable final String actaID,
			@RequestHeader("Acces-Token") String token) {
		
		String[] ccId = actaID.split("_")[1].split("-");
		List<Tema> temaAbiertoList = dataSource.getTemaAbiertoList(ccId[0] + "-" + ccId[1] ,Auth.getEmpresaID(token));
		
		Collections.sort(temaAbiertoList, new Comparator<Tema>() {

			@Override
			public int compare(Tema o1, Tema o2) {
					new Long(o1.getFechaCreacion()).compareTo(new Long(o2.getFechaCreacion()));
				return 0;
			}
		} );
		return temaAbiertoList;
	}
	
	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{actaID}/acta/isDone", method = RequestMethod.GET)
	public Tema actaIsDone(
			@PathVariable final String actaID,
			@RequestHeader("Acces-Token") String token) {
		
		String[] ccId = actaID.split("_")[1].split("-");
		return dataSource.actaIsDone(ccId[0] + "-" + ccId[1] ,Auth.getEmpresaID(token),actaID);
	}

	
	/**
	 * Retorna la lista de clientes
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{actaID}/acta/isDone/ok", method = RequestMethod.GET)
	public Tema actaIsDoneOk(
			@PathVariable final String actaID,
			@RequestHeader("Acces-Token") String token) {
		
		String[] ccId = actaID.split("_")[1].split("-");
		return dataSource.actaIsDoneOk(ccId[0] + "-" + ccId[1] ,Auth.getEmpresaID(token),actaID);
	}
	
	/**
	 * Crea un tema
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/create", method = RequestMethod.POST)
	@ResponseBody
	public Tema createTema(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestBody Tema tema,
			@RequestParam String actaID,
			@RequestParam List<String> cuerpoColList,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.createTema(cuerpoColegiadoID, tema,Auth.getEmpresaID(token), actaID, cuerpoColList);
	}

	/**
	 * Crea un tarea
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tarea/create", method = RequestMethod.POST)
	@ResponseBody
	public Tema createTarea(
			@PathVariable final String cuerpoColegiadoID,
			@RequestParam String temaID, 
			@RequestBody Tarea tarea,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.createTarea(cuerpoColegiadoID, temaID, tarea, Auth.getEmpresaID(token));
	}
	
	/**
	 * Add Comentario a tema
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/addComment", method = RequestMethod.POST)
	@ResponseBody
	public Tema addComentarioToTema(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestParam String temaID, 
			@RequestParam String comentario,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.addComentarioToTema(cuerpoColegiadoID, temaID, comentario,Auth.getEmpresaID(token));
	}

	/**
	 * Add Comentario a tarea
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tarea/addComment", method = RequestMethod.POST)
	@ResponseBody
	public Tarea addComentarioToTarea(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestParam String temaID, 
			@RequestParam String tareaID, 
			@RequestParam String comentario,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.addComentarioToTarea(cuerpoColegiadoID, temaID,tareaID, comentario, Auth.getEmpresaID(token));
	}

	
	/**
	 * Add Comentario a tarea
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tarea/close", method = RequestMethod.POST)
	@ResponseBody
	public Tarea closeTarea(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestParam String temaID, 
			@RequestParam String tareaID,
			@RequestParam String comentario,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.closeTarea(cuerpoColegiadoID, temaID,tareaID, Auth.getEmpresaID(token),comentario);
	}
	
	/**
	 * Cerrar tema
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/close", method = RequestMethod.POST)
	@ResponseBody
	public Tema cerrarTema(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestParam String temaID, 
			@RequestParam String comentario,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.cerrarTema(cuerpoColegiadoID, temaID, comentario,Auth.getEmpresaID(token));
	}
	
	
	/**
	 * Edita un cliente
	 */
	@ApiOperation(hidden = ProjectConstants.HIDE_SWAGGER_OP, value = "")
	@RequestMapping(value = "{cuerpoColegiadoID}/tema/edit", method = RequestMethod.POST)
	@ResponseBody
	public Acta editActa(
			@PathVariable final String cuerpoColegiadoID, 
			@RequestBody Acta user,
			@RequestHeader("Acces-Token") String token) {
		return dataSource.editActa(cuerpoColegiadoID, user,Auth.getEmpresaID(token));
	}

	public IDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
