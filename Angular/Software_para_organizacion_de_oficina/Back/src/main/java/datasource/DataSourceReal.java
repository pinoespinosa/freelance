package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.Cliente;
import data.Info;
import data.Trabajo;

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
		c.setTrabajos(new ArrayList<>());
		obj.getClientes().add(c);
		return c;
	}

	@Override
	public List<String> getUniversidadesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUniversidad(String universidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTrabajo(String idCliente, Trabajo trabajo) {
		Cliente c = new Cliente();
		c.setId(idCliente);

		Cliente original = obj.getClientes().get(obj.getClientes().indexOf(c));
		original.getTrabajos().add(trabajo);
		infoToFile(obj, "file.json");
	}

	@Override
	public void importCSV(MultipartFile filename) {

		Info base = new Info();
		base.setClientes(new ArrayList<>());
		Hashtable<String, Cliente> clientes = new Hashtable<>();

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
					t.setCarrera(valores[5].trim());
					t.setTema(valores[4].trim());
					t.setUniversidad(valores[6].trim());
					t.setMonto(valores[13].trim());
					t.setDondeSeEntero(valores[7].trim());
					t.setAsesor(valores[10].trim());
					t.setFecha(valores[0].trim());
					t.setFecha_entrega(valores[8].trim());
					
					try {	t.setObservaciones(valores[12].trim());			} catch (Exception e) {	}
					try {	t.setObservaciones_next(valores[24].trim());	} catch (Exception e) {	}
		
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

}
