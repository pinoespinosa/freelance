package web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import data.Cliente;
import data.Pago;
import data.Requerimiento;
import data.Trabajo;
import datasource.IDataSource;

@Service
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	IDataSource datasource;
	
	@Override
	public String getHelloWorld() {
		return "Holaa";
	}

	@Override
	public List<Cliente> getClientList() {
		return datasource.getClientes();
	}

	@Override
	public Cliente createCliente(Cliente c) {
		return datasource.createCliente(c);
	}

	@Override
	public List<String> getUniversidList() {
		return datasource.getUniversidadesList();

	}

	@Override
	public void createUniversidad(String universidad) {
		datasource.createUniversidad(universidad);
	}

	@Override
	public void createTrabajo(String idCliente, Trabajo trabajo) {
		datasource.createTrabajo(idCliente, trabajo);
		
	}

	@Override
	public void importCSV(MultipartFile filename) {
		datasource.importCSV(filename);
	}

	@Override
	public void exportCSV() {
		datasource.exportCSV();

	}
	
	@Override
	public Cliente getClient(String idCliente) {
		return datasource.getCliente(idCliente);
	}

	@Override
	public Trabajo getTrabajo(String idCliente, String idTrabajo) {
		return datasource.getTrabajo(idCliente,idTrabajo);
		
	}

	@Override
	public Trabajo getChangeTrabajoStatus(String clienteID, String trabajoID, String estado) {
		return datasource.getChangeTrabajoStatus(clienteID, trabajoID, estado);
	}

	@Override
	public List<String> getCarrerasList() {
		return datasource.getCarrerasList();
	}

	@Override
	public List<String> getDondeSeEnteroList() {
		return datasource.getDondeSeEnteroList();
	}

	@Override
	public void addRequerimiento(String idCliente, String idTrabajo, Requerimiento requerimiento) {
		datasource.addRequerimiento(idCliente, idTrabajo, requerimiento);
	
	}

	@Override
	public Trabajo updateFechaEntrega(String clienteID, String trabajoID, String fechaNueva) {
		return datasource.updateFechaEntrega(clienteID, trabajoID, fechaNueva);

	}

	@Override
	public List<Cliente> getClientNuevosList(String fechaDesde, String fechaHasta) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date fDesde, fHasta;
		
		try {
			fDesde = sdf.parse(fechaDesde);    
		} catch (ParseException e) {
			fDesde = new Date(0);    
		}

		if (fechaDesde.isEmpty())
			fDesde = new Date(0);    

		try {
			fHasta = sdf.parse(fechaHasta);    
		} catch (ParseException e) {
			fHasta = new Date();    
		}
		
		if (fechaHasta.isEmpty())
			fHasta = new Date(); 
		
		try {
			return datasource.getClientNuevosList(fDesde, fHasta);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
				
	}

	@Override
	public Hashtable<String, List<String>> getLastSellByTime(int cantidadDias) {
		return datasource.getLastSellByTime(cantidadDias);
	}

	@Override
	public List<Float> getLastSellByTimeCash(int cantidadDias, int cantidadValores) {
		return datasource.getLastSellByTimeCash(cantidadDias, cantidadValores);
	}

	@Override
	public List<Float> getSellsCashByTimeNewClients(int cantidadDias, int cantidadValores) {
		return datasource.getSellsCashByTimeNewClients(cantidadDias, cantidadValores);

	}

	@Override
	public List<Float> getSellsCashByTimeOldClients(int cantidadDias, int cantidadValores) {
		return datasource.getSellsCashByTimeOldClients(cantidadDias, cantidadValores);

	}

	@Override
	public List<Float> getSellsAmmountByTimeNewClients(int cantidadDias, int cantidadValores) {
		return datasource.getSellsAmmountByTimeNewClients(cantidadDias, cantidadValores);

	}

	@Override
	public void createCarrera(String carrera) {
		datasource.createCarrera(carrera);
		
	}

	@Override
	public void createDondeEntero(String dondeEntero) {
		datasource.createDondeEntero(dondeEntero);
		
	}

	@Override
	public Cliente editCliente(Cliente user) {
		return datasource.editCliente(user);
	}

	@Override
	public Pago addPago(String clienteID, String trabajoID, Pago pago) {
		return datasource.addPago(clienteID,trabajoID,pago);
	}

	@Override
	public Trabajo editTrabajo(String idCliente, Trabajo trabajo) {
		return datasource.editTrabajo(idCliente, trabajo);
	}





}
