package tiendas;

import java.sql.SQLException;
import java.util.Hashtable;

import utils.BD;
import utils.CSVUtils;

public abstract class ProductoAbs {

	protected String id;
	protected String titulo;
	protected String subtitulo;
	protected String descripcion;
	protected String valor;
	protected String marca;
	protected String categoria_1;
	protected String categoria_2;
	protected String categoria_3;
	protected String url_redireccion;
	protected String url_imagen_1;
	protected String url_imagen_2;
	protected String url_imagen_3;
	protected String url_imagen_4;
	protected String url_imagen_5;
	protected String tienda;
	protected String stock;

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ProductoAbs)
			return this.getId().equals(((ProductoAbs) obj).getId());
		return false;
	}

	private static Hashtable<String, String> tiendasID = new Hashtable<>();
	private static Hashtable<String, String> marcasID = new Hashtable<>();

	public static ProductoAbs create(String excelLine) {
		return new ProductoImpl(excelLine.split(CSVUtils.sep_column));
	}

	public String editUrl(String url){
		
		if (url==null || url.isEmpty() || url.equals("''"))
			return "";
		
		String[] aa = url.split("/");
		String name = aa[aa.length-1].split("\\?")[0];
		
		if (!name.contains("."))
			name+=".jpg";
		
		return "https://www.durekashop.com/file/im/prod/" + name;
	
	}

	
	public String getTiendaID() throws SQLException {
		return getTiendaFromBD(this.getTienda());
	}

	public String getMarcaID() throws SQLException {
		return getMarcaFromBD(this.getTienda());
	}

	private static String getTiendaFromBD(String tienda) {
		if (!tiendasID.containsKey(tienda))
			try {
				tiendasID.put(tienda, BD.executeSqlQueryString(
						"SELECT id FROM dureka.tiendas as ti WHERE ti.nombre='" + tienda + "';", "id"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return tiendasID.get(tienda);
	}

	private static String getMarcaFromBD(String marca) {
		if (!marcasID.containsKey(marca))
			try {
				marcasID.put(marca, BD.executeSqlQueryString(
						"SELECT id FROM dureka.marcas as ti WHERE ti.nombre='" + marca + "';", "id"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return marcasID.get(marca);
	}

	public String getId() {
		return id.replace("\"", "");
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo.replace("\"", "");
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo.replace("\"", "");
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String getDescripcion() {
		return descripcion.replace("\"", "");
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor.replace("\"", "");
	}

	public String getValorNumero() {
		return getValor().replace("$", "").trim().replace(".", "").replace(" ", ".");
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getMarca() {
		return marca.replace("\"", "");
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria_1() {
		return categoria_1.replace("\"", "");
	}

	public void setCategoria_1(String categoria_1) {
		this.categoria_1 = categoria_1;
	}

	public String getCategoria_2() {
		return categoria_2.replace("\"", "");
	}

	public void setCategoria_2(String categoria_2) {
		this.categoria_2 = categoria_2;
	}

	public String getCategoria_3() {
		return categoria_3.replace("\"", "");
	}

	public void setCategoria_3(String categoria_3) {
		this.categoria_3 = categoria_3;
	}

	public String getUrl_redireccion() {
		return url_redireccion.replace("\"", "");
	}

	public void setUrl_redireccion(String url_redireccion) {
		this.url_redireccion = url_redireccion;
	}

	public String getUrl_imagen_1() {
		return url_imagen_1.replace("\"", "");
	}

	public void setUrl_imagen_1(String url_imagen_1) {
		this.url_imagen_1 = url_imagen_1;
	}

	public String getUrl_imagen_2() {
		return url_imagen_2.replace("\"", "");
	}

	public void setUrl_imagen_2(String url_imagen_2) {
		this.url_imagen_2 = url_imagen_2;
	}

	public String getUrl_imagen_3() {
		return url_imagen_3.replace("\"", "");
	}

	public void setUrl_imagen_3(String url_imagen_3) {
		this.url_imagen_3 = url_imagen_3;
	}

	public String getUrl_imagen_4() {
		return url_imagen_4.replace("\"", "");
	}

	public void setUrl_imagen_4(String url_imagen_4) {
		this.url_imagen_4 = url_imagen_4;
	}

	public String getUrl_imagen_5() {
		return url_imagen_5.replace("\"", "");
	}

	public void setUrl_imagen_5(String url_imagen_5) {
		this.url_imagen_5 = url_imagen_5;
	}

	public String getTienda() {
		return tienda.replace("\"", "");
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public String getStock() {
		return stock.replace("\"", "");
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String toCSVLine() {
		return "\"" + getId() + "\",\"" + getTitulo() + "\",\"" + getSubtitulo() + "\",\"" + getDescripcion() + "\",\""
				+ getValor() + "\",\"" + getMarca() + "\",\"" + getCategoria_1() + "\",\"" + getCategoria_2() + "\",\""
				+ getCategoria_3() + "\",\"" + getUrl_redireccion() + "\",\"" + getUrl_imagen_1() + "\",\""
				+ getUrl_imagen_2() + "\",\"" + getUrl_imagen_3() + "\",\"" + getUrl_imagen_4() + "\",\""
				+ getUrl_imagen_5() + "\",\"" + getTienda() + "\",\"" + getStock() + "\"";
	}

}
