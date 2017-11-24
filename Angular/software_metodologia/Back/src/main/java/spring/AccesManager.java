package spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import data.Auditoria;
import data.Auth.Rol;
import datasource.IDataSource;

public class AccesManager {

	@Autowired
	IDataSource datasource;

	public enum Tareas {
		CREAR_TRABAJO, EDITAR_TRABAJO, CAMBIO_ESTADO, CAMBIAR_FECHA, CAMBIAR_ASESOR, AGREGAR_PAGO, AUDITORIA
	}

	public boolean canDoIt(String token, Tareas accion) {

		boolean alow = allow(token, accion);

		if (alow) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			datasource.audit(new Auditoria("", sdf.format(new Date()), ChipherTool.getUser(token), accion.name(), ChipherTool.getRol(token).name()));
			return true;
		} else
			return false;

	}

	public boolean allow(String token, Tareas accion) {

		Rol rol = ChipherTool.getRol(token);

		switch (accion) {

		case CREAR_TRABAJO:
			return (Rol.GERENTE.equals(rol));

		case EDITAR_TRABAJO:
			return (Rol.GERENTE.equals(rol));

		case CAMBIO_ESTADO:
			return (Rol.ADMINISTRADOR.equals(rol) || Rol.GERENTE.equals(rol) || Rol.JEFE_OPERATIVO.equals(rol));

		case CAMBIAR_FECHA:
			return (Rol.ADMINISTRADOR.equals(rol) || Rol.GERENTE.equals(rol) || Rol.JEFE_OPERATIVO.equals(rol));

		case CAMBIAR_ASESOR:
			return (Rol.ADMINISTRADOR.equals(rol) || Rol.GERENTE.equals(rol) || Rol.JEFE_OPERATIVO.equals(rol));

		case AGREGAR_PAGO:
			return (Rol.ADMINISTRADOR.equals(rol) || Rol.GERENTE.equals(rol) || Rol.JEFE_OPERATIVO.equals(rol));

		case AUDITORIA:
			return (Rol.GERENTE.equals(rol));

			
		}

		return false;

	}

}
