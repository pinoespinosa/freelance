package tiendas;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Hashtable;

import csvMarket.Script;
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
	
	protected String original_url_imagen_1;
	protected String original_url_imagen_2;
	protected String original_url_imagen_3;
	protected String original_url_imagen_4;
	protected String original_url_imagen_5;
	
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
	private static int nextCode=-1;

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

	
	public String getTiendaID() {
		return getTiendaFromBD(this.getTienda());
	}

	public String getMarcaID() throws SQLException, ClassNotFoundException {
		return getMarcaFromBD(this.getTienda());
	}

	private static String getTiendaFromBD(String tienda) {
		if (!tiendasID.containsKey(tienda))
			try {
				tiendasID.put(tienda, BD.executeSqlQueryString(
						"SELECT id FROM "+BD.get_BD_NAME()+".tiendas as ti WHERE ti.nombre='" + tienda + "';", "id"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return tiendasID.get(tienda);
	}

	private static String getMarcaFromBD(String marca) throws ClassNotFoundException {
		if (!marcasID.containsKey(marca))
			try {
				marcasID.put(marca, BD.executeSqlQueryString(
						"SELECT id FROM "+BD.get_BD_NAME()+".marcas as ti WHERE ti.nombre='" + marca + "';", "id"));
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
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo.replace("\"", "").replace(CSVUtils.end_line, "");
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo.replace("\"", "").replace(CSVUtils.end_line, "");
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.replace("\"", "").replace(CSVUtils.end_line, "");
	}

	public String getValor() {
		return valor.replace("\"", "");
	}

	public String getValorNumero() {
		
		
		String value = getValor().replace("$", "").trim();
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');

		DecimalFormat format = new DecimalFormat("###,###.###");
		format.setDecimalFormatSymbols(symbols);
		try {
			float f = format.parse(value).floatValue();
			return f+"";
		} catch (ParseException e) {
			throw new RuntimeException(
					"Formato de precio invalido: " + getValor() + " en el producto " + titulo + ". Debe usarse el punto como separador de miles y el punto para separar los decimales. Ej: $ 100.100,99 o $10.000 o $15,25");
		}
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca.replace("\"", "").replace(CSVUtils.end_line, "");
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

	public String getOriginal_url_imagen_1() {
		return original_url_imagen_1;
	}

	public void setOriginal_url_imagen_1(String original_url_imagen_1) {
		this.original_url_imagen_1 = original_url_imagen_1;
	}

	public String getOriginal_url_imagen_2() {
		return original_url_imagen_2;
	}

	public void setOriginal_url_imagen_2(String original_url_imagen_2) {
		this.original_url_imagen_2 = original_url_imagen_2;
	}

	public String getOriginal_url_imagen_3() {
		return original_url_imagen_3;
	}

	public void setOriginal_url_imagen_3(String original_url_imagen_3) {
		this.original_url_imagen_3 = original_url_imagen_3;
	}

	public String getOriginal_url_imagen_4() {
		return original_url_imagen_4;
	}

	public void setOriginal_url_imagen_4(String original_url_imagen_4) {
		this.original_url_imagen_4 = original_url_imagen_4;
	}

	public String getOriginal_url_imagen_5() {
		return original_url_imagen_5;
	}

	public void setOriginal_url_imagen_5(String original_url_imagen_5) {
		this.original_url_imagen_5 = original_url_imagen_5;
	}
	
	public static int getNextCode(){
		if (nextCode == -1)
			try {
				nextCode = Integer.parseInt(BD.executeSqlQueryString(Script.getLastProductoCode(), "codigo"));
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		
		nextCode ++;
	return nextCode;
	}
	
	public String toCSVLine() {
		return "\"" + getId() + "\",\"" + getTitulo() + "\",\"" + getSubtitulo() + "\",\"" + getDescripcion() + "\",\""
				+ getValor() + "\",\"" + getMarca() + "\",\"" + getCategoria_1() + "\",\"" + getCategoria_2() + "\",\""
				+ getCategoria_3() + "\",\"" + getUrl_redireccion() + "\",\"" + getOriginal_url_imagen_1() + "\",\""
				+ getOriginal_url_imagen_2() + "\",\"" + getOriginal_url_imagen_3() + "\",\"" + getOriginal_url_imagen_4() + "\",\""
				+ getOriginal_url_imagen_5() + "\",\"" + getTienda() + "\",\"" + getStock() + "\"";
	}

}
