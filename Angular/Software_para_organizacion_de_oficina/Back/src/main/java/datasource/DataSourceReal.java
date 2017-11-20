package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import data.Auth;
import data.Auth.Rol;
import data.Cliente;
import data.Info;
import data.Pago;
import data.Requerimiento;
import data.Trabajo;
import spring.ChipherTool;

public class DataSourceReal implements IDataSource {

	private Info obj;

	public DataSourceReal() {
		readFromFile();
	}

	void readFromFile() {
		System.out.println("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			obj = mapper.readValue(new File("file.json"), Info.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void infoToFile(Object data, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(filename), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> getClientes() {
		
		if (obj==null){
			throw new RuntimeException("Current relative path is: " + Paths.get("").toAbsolutePath().toString());
		}
		
		return obj.getClientes();
	}

	@Override
	public Cliente createCliente(Cliente c) {
		c.setId(System.currentTimeMillis()+"");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		c.setFechaSuscripcion(sdf.format(new Date()));
		
		c.setTrabajos(new ArrayList<>());
		obj.getClientes().add(c);
		return c;
	}

	@Override
	public List<String> getUniversidadesList() {
		return obj.getUniversidades();
	}

	@Override
	public void createUniversidad(String universidad) {
		obj.getUniversidades().add(universidad);
		infoToFile(obj, "file.json");
	}
	

	@Override
	public Trabajo createTrabajo(String idCliente, Trabajo trabajo) {
		Cliente c = new Cliente();
		c.setId(idCliente);

		Cliente original = obj.getClientes().get(obj.getClientes().indexOf(c));
		trabajo.setId(c.getId() + "-" + original.getTrabajos().size());
		
		
		original.getTrabajos().add(trabajo);
		infoToFile(obj, "file.json");
		return trabajo;
	}

	@Override
	public void importCSV(MultipartFile filename) {

		Info base = new Info();
		base.setClientes(new ArrayList<>());
		Hashtable<String, Cliente> clientes = new Hashtable<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		

		int cant = -1;
		
		try {
			List<String> archivo = CSVUtils.readCsvFile(filename);

			for (String fila : archivo) {

				try {
					cant++;
					String[] valores = fila.split(CSVUtils.sep_column);

					String dato_nombre = valores[1].trim();

					Cliente c = null;
					if (!clientes.containsKey(dato_nombre)) {
						c = new Cliente();
						c.setId(cant+"");
						c.setNombre(dato_nombre);
						c.setFechaSuscripcion(valores[0].trim());
						try {	c.setTelefono1(valores[2].split("/")[0].trim());	} catch (Exception e) {	}
						try {	c.setTelefono2(valores[2].split("/")[1].trim());	} catch (Exception e) {	}
						try {	c.setTelefono3(valores[2].split("/")[2].trim());	} catch (Exception e) {	}

						try {	c.setEmail1(valores[3].split(",")[0].trim());	} catch (Exception e) {	}
						try {	c.setEmail2(valores[3].split(",")[1].trim());	} catch (Exception e) {	}
						try {	c.setEmail3(valores[3].split(",")[2].trim());	} catch (Exception e) {	}

						c.setTrabajos(new ArrayList<>());
						base.getClientes().add(c);
						clientes.put(dato_nombre, c);
					} else {
						c = clientes.get(dato_nombre);
					}

					Trabajo t = new Trabajo();
					t.setId(c.getId() + "-" + c.getTrabajos().size());
					t.setTema(valores[4].trim());
					
					if (Strings.isNullOrEmpty(t.getTema()))
						t.setTema("Sin datos");
					
					t.setMonto(valores[13].trim());

					String carrera = valores[5].trim();
					if (!base.getCarreras().contains(carrera))
						base.getCarreras().add(carrera);
					t.setCarrera(carrera);
					
					String univ = valores[6].trim();
					if (!base.getUniversidades().contains(univ))
						base.getUniversidades().add(univ);
					t.setUniversidad(univ);
									
					String dondeSeEntero = valores[7].trim();
					if (!base.getDondeSeEntero().contains(dondeSeEntero))
						base.getDondeSeEntero().add(dondeSeEntero);
					t.setDondeSeEntero(dondeSeEntero);

					t.setAsesor(valores[10].trim());
					
					String estado = Strings.isNullOrEmpty(valores[11].trim())?"Desconocido":valores[11].trim();
					t.setEstado(estado);

					t.setFecha(valores[0].trim());
					t.setFecha_entrega(valores[8].trim());
					
					t.setPagos(new ArrayList<>());
					
					try {
						Pago p = new Pago();
						p.setFecha_pago(valores[0].trim());
						p.setAbono(valores[14].trim());
						p.setId(t.getId() + "-" + t.getPagos().size());
						if(p.getAbono()!=null && !p.getAbono().isEmpty())
							t.getPagos().add(p);
					} catch (Exception e) {
						System.out.println();
					}

					try {

						Pago p = new Pago();
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[0].trim())));	} catch (Exception e) {}
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[16].trim())));	} catch (Exception e) {}
						p.setAbono(valores[15].trim());
						p.setId(t.getId() + "-" + t.getPagos().size());
						if(p.getAbono()!=null && !p.getAbono().isEmpty())
							t.getPagos().add(p);
					} catch (Exception e) {
						System.out.println();
					}
					
					try {

						Pago p = new Pago();
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[0].trim())));	} catch (Exception e) {}
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[16].trim())));	} catch (Exception e) {}
						try {	p.setFecha_pago(sdf.format(sdf.parse(valores[18].trim())));	} catch (Exception e) {}
						p.setAbono(valores[17].trim());
						p.setId(t.getId() + "-" + t.getPagos().size());
						if(p.getAbono()!=null && !p.getAbono().isEmpty())
							t.getPagos().add(p);
					} catch (Exception e) {
						System.out.println();
					}
					try {
						
						float pagado = 0;
						for (Pago valor : t.getPagos()) {
							pagado += Float.parseFloat(valor.getAbono());
						}
						float saldo = Float.parseFloat(t.getMonto()) - pagado;	
						t.setSaldo(saldo+"");
						
					} catch (Exception e) {
						// TODO: handle exception
					}

						
					c.getTrabajos().add(t);

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			System.out.println(archivo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		infoToFile(base, "file.json");
		obj=base;
	}

	@Override
	public Cliente getCliente(String idCliente) {
		
		Cliente c = new Cliente();
		c.setId(idCliente);
		
		return obj.getClientes().get((obj.getClientes().indexOf(c)));
	}

	@Override
	public Trabajo getTrabajo(String idCliente, String idTrabajo) {
		
		Cliente c = new Cliente();
		c.setId(idCliente);
		Cliente clie = obj.getClientes().get((obj.getClientes().indexOf(c)));
		
		Trabajo t = new Trabajo();
		t.setId(idTrabajo);
		Trabajo trab = clie.getTrabajos().get((clie.getTrabajos().indexOf(t)));
		
		return trab;
	}

	@Override
	public Trabajo getChangeTrabajoStatus(String clienteID, String trabajoID, String estado) {

		Trabajo trabajo = getTrabajo(clienteID, trabajoID);
		trabajo.setEstado(estado);
		return trabajo;
	}

	@Override
	public List<String> getCarrerasList() {
		return obj.getCarreras();
	}

	@Override
	public List<String> getDondeSeEnteroList() {
		return obj.getDondeSeEntero();
	}

	@Override
	public void addRequerimiento(String idCliente, String idTrabajo, Requerimiento requerimiento) {
		Trabajo t = getTrabajo(idCliente, idTrabajo);
		t.getRequerimientos().add(requerimiento);
	}

	@Override
	public Trabajo updateFechaEntrega(String clienteID, String trabajoID, String fechaNueva) {
		Trabajo t = getTrabajo(clienteID, trabajoID);
		t.setFecha_entrega(fechaNueva);
		infoToFile(obj, "file.json");
		
		return t;
	}

	@Override
	public List<Cliente> getClientNuevosList(Date fechaDesde, Date fechaHasta) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<Cliente> filtrados = new ArrayList<>();

		for (Cliente c : obj.getClientes()) {

			Cliente m = c.clone();
						
			for (Trabajo t : m.getTrabajos()) {

				try {

					List<Pago> fil = new ArrayList<>();

					for (Pago p : t.getPagos()) {

						if (sdf.parse(p.getFecha_pago()).before(fechaHasta)
								&& sdf.parse(p.getFecha_pago()).after(fechaDesde)) {
							fil.add(p);
						}

					}

					t.setPagos(fil);

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			List<Trabajo> filTrab = new ArrayList<>();
			for (Trabajo t : m.getTrabajos()) {
				if (!t.getPagos().isEmpty())
				{
					filTrab.add(t);
				}
			}
			m.setTrabajos(filTrab);
			
			if (!m.getTrabajos().isEmpty())
				filtrados.add(m);
			
		}
		
		
		return filtrados;

	}

	@Override
	public Hashtable<String, List<String>> getLastSellByTime(int cantidadDias) {

		Hashtable<String, List<String>> hast = new Hashtable<>();
				
		int diaHoy = getDiasDesde70(new Date());

		for (Cliente c : obj.getClientes()) {
			for (Trabajo aa : c.getTrabajos()) {
				try {

					int diaTrabajo = getDiasDesde70(aa.getFecha());
					long valorr = diaHoy - diaTrabajo;

					int bloque = (int) (valorr / cantidadDias);

					if (!hast.containsKey(bloque+""))
						hast.put(bloque+"", new ArrayList<>());
					hast.get(bloque+"").add(aa.getFecha());
					
				} catch (Exception e) {
				}
			}
		}

		String andres="";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			andres = mapper.writeValueAsString(hast);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> v = new ArrayList<>();
		v.addAll(hast.keySet());
		
		Collections.sort(v);
		
		Hashtable<String, List<String>> hast2 = new Hashtable<>();
		for (String string : v) {
			hast2.put(string, hast.get(string));
			
		}
		
		
		
		System.out.println(andres);
		
		return hast2;
	}

	private int getDiasDesde70(String fecha) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return getDiasDesde70(sdf.parse(fecha));

	}

	private int getDiasDesde70(Date fecha) {
		long fechaa = fecha.getTime();
		return new Long(fechaa / (1000 * 3600 * 24)).intValue();

	}

	@Override
	public List<Float> getLastSellByTimeCash(int cantidadDias, int cantidadValores) {
		
		Hashtable<String, Float> hast = new Hashtable<>();
		
		int diaHoy = getDiasDesde70(new Date());

		for (Cliente c : obj.getClientes()) {
			for (Trabajo aa : c.getTrabajos()) {
				try {

					int diaTrabajo = getDiasDesde70(aa.getFecha());
					long valorr = diaHoy - diaTrabajo;

					int bloque = (int) (valorr / cantidadDias);

					if (!hast.containsKey(bloque+""))
						hast.put(bloque+"", new Float(0));
					
					hast.put(bloque+"", hast.get(bloque+"")+Float.parseFloat(aa.getMonto()));
					
				} catch (Exception e) {
				}
			}
		}

		String andres="";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			andres = mapper.writeValueAsString(hast);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Float> lista = new ArrayList<>();
			
		for (int i = 0; i<cantidadValores; i++){
			if (hast.containsKey(i+""))
				lista.add(hast.get(i+""));
			else
				lista.add(new Float(0));
		}
		
		
		System.out.println(andres);
		
		return lista;
	}

	@Override
	public synchronized List<Float> getSellsCashByTimeNewClients(int cantidadDias, int cantidadValores) {

		synchronized (obj) {
			
			Hashtable<String, Float> hast = new Hashtable<>();
			
			int diaHoy = getDiasDesde70(new Date());

			for (Cliente c : obj.getClientes()) {
				
				Trabajo t = c.getFistWork();
				
				for (Trabajo aa : c.getTrabajos()) {
					try {

						int diaTrabajo = getDiasDesde70(aa.getFecha());
						int perAtrasTrabajo = (int) ((diaHoy - diaTrabajo) / cantidadDias);				
						
						if (!hast.containsKey(perAtrasTrabajo+""))
							hast.put(perAtrasTrabajo+"", new Float(0));
						
						if(aa.equals(t))
							hast.put(perAtrasTrabajo+"", hast.get(perAtrasTrabajo+"")+Float.parseFloat(aa.getMonto()));
						
					} catch (Exception e) {
					}
				}
			}

			String andres="";
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				andres = mapper.writeValueAsString(hast);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			List<Float> lista = new ArrayList<>();
				
			for (int i = 0; i<cantidadValores; i++){
				if (hast.containsKey(i+""))
					lista.add(hast.get(i+""));
				else
					lista.add(new Float(0));
			}
			
			
			System.out.println(andres);
			
			return lista;
			
			
		}
		

		
		
	}

	@Override
	public synchronized List<Float> getSellsCashByTimeOldClients(int cantidadDias, int cantidadValores) {
		
		Hashtable<String, Float> hast = new Hashtable<>();
		
		int diaHoy = getDiasDesde70(new Date());

		for (Cliente c : obj.getClientes()) {
			
			Trabajo tt =  c.getFistWork();
			
			for (Trabajo aa : c.getTrabajos()) {
				try {

					int diaTrabajo = getDiasDesde70(aa.getFecha());

					System.out.println(aa.getFecha());
					System.out.println(c.getFechaSuscripcion());
					System.out.println();
					
					int perAtrasTrabajo = (int) ((diaHoy - diaTrabajo) / cantidadDias);
					
					if (!hast.containsKey(perAtrasTrabajo+""))
						hast.put(perAtrasTrabajo+"", new Float(0));
					
					if(!aa.equals(tt))
						hast.put(perAtrasTrabajo+"", hast.get(perAtrasTrabajo+"")+Float.parseFloat(aa.getMonto()));
					
				} catch (Exception e) {
				}
			}
		}

		String andres="";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			andres = mapper.writeValueAsString(hast);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Float> lista = new ArrayList<>();
			
		for (int i = 0; i<cantidadValores; i++){
			if (hast.containsKey(i+""))
				lista.add(hast.get(i+""));
			else
				lista.add(new Float(0));
		}
		
		
		System.out.println(andres);
		
		return lista;
	}

	@Override
	public synchronized List<Float> getSellsAmmountByTimeNewClients(int cantidadDias, int cantidadValores) {


		Hashtable<String, Float> hast = new Hashtable<>();
		
		int diaHoy = getDiasDesde70(new Date());

		for (Cliente c : obj.getClientes()) {
			
			Trabajo tt =  c.getFistWork();

			
			for (Trabajo aa : c.getTrabajos()) {
				try {

					int diaTrabajo = getDiasDesde70(aa.getFecha());

					int perAtrasTrabajo = (int) ((diaHoy - diaTrabajo) / cantidadDias);

					
					if (!hast.containsKey(perAtrasTrabajo+""))
						hast.put(perAtrasTrabajo+"", new Float(0));
					
					if(aa.equals(tt))
						hast.put(perAtrasTrabajo+"", hast.get(perAtrasTrabajo+"")+1);
					
				} catch (Exception e) {
				}
			}
		}

		String andres="";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			andres = mapper.writeValueAsString(hast);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Float> lista = new ArrayList<>();
			
		for (int i = 0; i<cantidadValores; i++){
			if (hast.containsKey(i+""))
				lista.add(hast.get(i+""));
			else
				lista.add(new Float(0));
		}
		
		System.out.println(andres);
		return lista;
	}

	@Override
	public void exportCSV() {

		List<String> filas = new ArrayList<>();
	
		List<String> list = Arrays.asList("Fecha", "Nombre", "Telefonos", "Emails", "Trabajo", "Carrera", "Universidad",
				"Donde_Se_Entero", "Fecha_Suscripcion", "", "Asesor", "Estado", "Observ", "Monto", "Ab1", "Fecha1", "Ab2", "Fecha2",
				"Ab3", "Fecha3", "Ab4", "Fecha4", "Ab5", "Fecha5", "Ab6", "Fecha6", "Ab7", "Fecha7", "Ab8", "Fecha8",
				"Ab9", "Fecha9", "Ab10", "Fecha10", "FPago1", "Obv1", "FPago2", "Obv2", "FPago3", "Obv3", "FPago4",
				"Obv4", "FPago5", "Obv5", "FPago6", "Obv6", "FPago7", "Obv7", "FPago8", "Obv8", "FPago9", "Obv9",
				"FPago10", "Obv10");
		String aaasda =  String.join(",", list);
		filas.add(aaasda);
		
		for (Cliente c : obj.getClientes()) {
			for (Trabajo t : c.getTrabajos()) {
				filas.add('"'+ c.toCSV() +'"'+','+'"'+ t.toCSV());
			}
		}

		CSVUtils.write(filas, "BaseDatos.csv");

	}

	@Override
	public void createCarrera(String carrera) {
		obj.getCarreras().add(carrera);
		infoToFile(obj, "file.json");

	}

	@Override
	public void createDondeEntero(String dondeEntero) {
		obj.getDondeSeEntero().add(dondeEntero);
		infoToFile(obj, "file.json");

	}

	@Override
	public Cliente editCliente(Cliente editedUser) {
		Cliente dbUser = obj.getClientes().get((obj.getClientes().indexOf(editedUser)));

		dbUser.setNombre(editedUser.getNombre());

		dbUser.setEmail1(editedUser.getEmail1());
		dbUser.setEmail2(editedUser.getEmail2());
		dbUser.setEmail3(editedUser.getEmail3());
	
		dbUser.setTelefono1(editedUser.getTelefono1());
		dbUser.setTelefono2(editedUser.getTelefono2());
		dbUser.setTelefono3(editedUser.getTelefono3());
		
		infoToFile(obj, "file.json");
		return editedUser;
	}

	@Override
	public Pago addPago(String clienteID, String trabajoID, Pago pago) {
		Trabajo trabajo = getTrabajo(clienteID, trabajoID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		pago.setFecha_pago(sdf.format(new Date()));
		trabajo.getPagos().add(pago);
		return pago;
	}

	@Override
	public Trabajo editTrabajo(String idCliente, Trabajo trab) {
		
		Trabajo dbTrab = getTrabajo(idCliente, trab.getId());
		
		dbTrab.setAsesor(trab.getAsesor());	
		dbTrab.setCarrera(trab.getCarrera());	
		dbTrab.setDondeSeEntero(trab.getDondeSeEntero());	
		dbTrab.setFecha_entrega(trab.getFecha_entrega());
		
		dbTrab.setMonto(trab.getMonto());	

		dbTrab.setTema(trab.getTema());	
		dbTrab.setUniversidad(trab.getUniversidad());	
				
				
		infoToFile(obj, "file.json");
		return dbTrab;
	}

	@Override
	public Auth auth(String user, String pass) {

		String clave = ChipherTool.encrypt(user + pass);
		
		if (obj.getUsers().containsKey(clave))
		{
			Auth aa = obj.getUsers().get(clave);
			
			return new Auth(aa.getRol(), ChipherTool.encrypt(aa.getRol().name() +"_"+ clave));
			
		}
		
		return new Auth(null, "FAIL");
		
	}

	@Override
	public Auth create(String user, String pass, Rol rol) {
		String clave = ChipherTool.encrypt(user + pass);
		Auth valor = new Auth(rol,clave);
		obj.getUsers().put(clave, valor);
		infoToFile(obj, "file.json");
		return valor;
	}

}
