package csvMarket;

import java.sql.SQLException;
import java.util.Hashtable;

import utils.BD;
import utils.CSVUtils;

public class CategoriaInfo {

	private static Hashtable<String, String> categoriaID = new Hashtable<>();
	private static Hashtable<String, String> subcategoriaID = new Hashtable<>();
	private static Hashtable<String, String> subsubcategoriaID = new Hashtable<>();

	private String Palabra, Categoria, SubCategoria, SubSubCategoria, Caracteristica_1, Caracteristica_2,
			Caracteristica_3, Caracteristica_4;

	public CategoriaInfo(String line) {

		Palabra = 			get(line,0);
		Categoria = 		get(line,1);
		SubCategoria = 		get(line,2);
		SubSubCategoria = 	get(line,3);
		Caracteristica_1 = 	get(line,4);
		Caracteristica_2 = 	get(line,5);
		Caracteristica_3 = 	get(line,6);
		Caracteristica_4 = 	get(line,7);

	}

	private String get(String line, int pos){
		if(line.split(CSVUtils.sep_column).length > pos)
			return line.split(CSVUtils.sep_column)[pos];
		else
			return "";
		
	}
	
	public String getCatKey(){
		return getCategoria() + '_' + getSubCategoria() + '_' + getSubSubCategoria();
		
	}
	
	public String getPalabra() {
		return Palabra;
	}

	public void setPalabra(String palabra) {
		Palabra = palabra;
	}

	public String getCategoriaID() {
		return getCategoriaFromBD(getCategoria());
	}
	
	public String getSubCategoriaID() {
		return getSubCategoriaFromBD(getSubCategoria());
	}
	
	public String getSubSubCategoriaID() {
		return getSubSubCategoriaFromBD(getCategoria());
	}
	
	private static String getCategoriaFromBD(String categoria) {
		if (!categoriaID.containsKey(categoria))
			try {
				categoriaID.put(categoria, BD.executeSqlQueryString(
						"SELECT id FROM dureka.categorias as ti WHERE ti.nombre='" + categoria + "';", "id"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		String valor = categoriaID.get(categoria); 
		return valor.isEmpty()?"-1":valor;
	}
	
	private static String getSubCategoriaFromBD(String subcategoria) {
		if (!subcategoriaID.containsKey(subcategoria))
			try {
				subcategoriaID.put(subcategoria, BD.executeSqlQueryString(
						"SELECT id FROM dureka.subcategorias as ti WHERE ti.nombre='" + subcategoria + "';", "id"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		String valor = subcategoriaID.get(subcategoria); 
		return valor.isEmpty()?"-1":valor;
	}
	
	private static String getSubSubCategoriaFromBD(String subsubcategoria) {
		if (!subsubcategoriaID.containsKey(subsubcategoria))
			try {
				subsubcategoriaID.put(subsubcategoria, BD.executeSqlQueryString(
						"SELECT id FROM dureka.subsubcategorias as ti WHERE ti.nombre='" + subsubcategoria + "';", "id"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		String valor = subsubcategoriaID.get(subsubcategoria);
		return valor.isEmpty()?"-1":valor;
	}
	
	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public String getSubCategoria() {
		return SubCategoria;
	}

	public void setSubCategoria(String subCategoria) {
		SubCategoria = subCategoria;
	}

	public String getSubSubCategoria() {
		return SubSubCategoria;
	}

	public void setSubSubCategoria(String subSubCategoria) {
		SubSubCategoria = subSubCategoria;
	}

	public String getCaracteristica_1() {
		return Caracteristica_1;
	}

	public void setCaracteristica_1(String caracteristica_1) {
		Caracteristica_1 = caracteristica_1;
	}

	public String getCaracteristica_2() {
		return Caracteristica_2;
	}

	public void setCaracteristica_2(String caracteristica_2) {
		Caracteristica_2 = caracteristica_2;
	}

	public String getCaracteristica_3() {
		return Caracteristica_3;
	}

	public void setCaracteristica_3(String caracteristica_3) {
		Caracteristica_3 = caracteristica_3;
	}

	public String getCaracteristica_4() {
		return Caracteristica_4;
	}

	public void setCaracteristica_4(String caracteristica_4) {
		Caracteristica_4 = caracteristica_4;
	}

}
