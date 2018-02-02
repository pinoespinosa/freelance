package data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Info {

	private boolean sendJuanEmail;
	
	private List<Auditoria> audit;

	private List<Empresa> empresas;

	private Hashtable<String, Auth> users = new Hashtable<>();

	public Hashtable<String, Auth> getUsers() {
		if (users == null)
			users = new Hashtable<>();
		return users;
	}

	public void setUsers(Hashtable<String, Auth> users) {
		this.users = users;
	}

	public List<Auditoria> getAudit() {
		if (audit == null)
			audit = new ArrayList<Auditoria>();
		return audit;
	}

	public void setAudit(List<Auditoria> audit) {
		this.audit = audit;
	}

	public List<Empresa> getEmpresas() {
		if (empresas == null)
			empresas = new ArrayList<>();
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public boolean isSendJuanEmail() {
		return sendJuanEmail;
	}

	public void setSendJuanEmail(boolean sendJuanEmail) {
		this.sendJuanEmail = sendJuanEmail;
	}


}
