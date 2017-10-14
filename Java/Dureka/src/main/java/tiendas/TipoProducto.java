package tiendas;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import csvMarket.CategoriaInfo;
import csvMarket.Script;
import utils.BD;

public class TipoProducto {

	private static int nextCode = -1;
	
	protected String id;
	protected String codigo;
	protected String nombre;
	protected String texto_meli;
	protected String slug;
	protected String subtitulo;
	protected String imagen;
	protected String marca;
	protected String categoria_1;
	protected String categoria_2;
	protected String categoria_3;
	protected String caract1_tipo;
	protected String caract1_valor;
	protected String caract2_tipo;
	protected String caract2_valor;
	protected String caract3_tipo;
	protected String caract3_valor;
	protected String caract4_tipo;
	protected String caract4_valor;
	protected String caract5_tipo;
	protected String caract5_valor;
	protected String descipcion;
	protected String titulo;
	protected String description;
	protected String keywords;
	protected String fecha;
	protected String estado;
	protected String orden;

	
	
	
	public TipoProducto(ProductoAbs prod, CategoriaInfo cat) throws SQLException {
		super();
		
		this.setTitulo(prod.getTitulo());
		this.setCodigo(getNextCode()+"");
		this.setSubtitulo(prod.getSubtitulo());
		this.setNombre(prod.getTitulo());
		this.setMarca(prod.getMarcaID());
		this.setKeywords(prod.getTitulo() + ',' + prod.getMarca() + ',' + prod.getCategoria_1() + ','
				+ prod.getCategoria_2() + ',' + prod.getCategoria_3());
		
		this.setCategoria_1(cat.getCategoriaID());
		this.setCategoria_2(cat.getSubCategoriaID());
		this.setCategoria_3(cat.getSubSubCategoriaID());
	
		this.setImagen(prod.getUrl_imagen_1());
		
		String slug = prod.getTitulo().replaceAll(" ", "-").toLowerCase().replaceAll("ñ", "n");
		slug = slug.replaceAll("á","a").replaceAll("é","e").replaceAll("í","i").replaceAll("ó","o").replaceAll("ú","u");
		slug = slug.replaceAll("[^a-zA-Z0-9\\-]+","");
		this.setSlug(slug);
		
		this.setCaract1_valor(cat.getCaracteristica_1());
		this.setCaract2_valor(cat.getCaracteristica_2());
		this.setCaract3_valor(cat.getCaracteristica_3());
		this.setCaract4_valor(cat.getCaracteristica_4());

		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		this.setFecha(dt.format(new Date()));
		
		this.setEstado("1");
		this.setOrden("0");
		
		//TODO  Pendiente hallar el TipoProducto
		//TODO	 
		
	}

	public static int getNextCode() throws NumberFormatException, SQLException{
		if (nextCode == -1)
			nextCode = Integer.parseInt(BD.executeSqlQueryString(Script.getLastTipoProductoCode(), "codigo"));
		
		nextCode ++;
	return nextCode;
	}

	public String getId() {
		return get(id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return get(codigo);
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return get(nombre);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTexto_meli() {
		return get(texto_meli);
	}

	public void setTexto_meli(String texto_meli) {
		this.texto_meli = texto_meli;
	}

	public String getSlug() {
		return get(slug);
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getSubtitulo() {
		return get(subtitulo);
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String getImagen() {
		return get(imagen);
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getMarca() {
		return get(marca);
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria_1() {
		return get(categoria_1);
	}

	public void setCategoria_1(String categoria_1) {
		this.categoria_1 = categoria_1;
	}

	public String getCategoria_2() {
		return categoria_2;
	}

	public void setCategoria_2(String categoria_2) {
		this.categoria_2 = categoria_2;
	}

	public String getCategoria_3() {
		return get(categoria_3);
	}

	public void setCategoria_3(String categoria_3) {
		this.categoria_3 = categoria_3;
	}

	public String getCaract1_tipo() {
		return getInt(caract1_tipo);
	}

	public void setCaract1_tipo(String caract1_tipo) {
		this.caract1_tipo = caract1_tipo;
	}

	public String getCaract1_valor() {
		return get(caract1_valor);
	}

	public void setCaract1_valor(String caract1_valor) {
		this.caract1_valor = caract1_valor;
	}

	public String getCaract2_tipo() {
		return getInt(caract2_tipo);
	}

	public void setCaract2_tipo(String caract2_tipo) {
		this.caract2_tipo = caract2_tipo;
	}

	public String getCaract2_valor() {
		return get(caract2_valor);
	}

	public void setCaract2_valor(String caract2_valor) {
		this.caract2_valor = caract2_valor;
	}

	public String getCaract3_tipo() {
		return getInt(caract3_tipo);
	}

	public void setCaract3_tipo(String caract3_tipo) {
		this.caract3_tipo = caract3_tipo;
	}

	public String getCaract3_valor() {
		return get(caract3_valor);
	}

	public void setCaract3_valor(String caract3_valor) {
		this.caract3_valor = caract3_valor;
	}

	public String getCaract4_tipo() {
		return getInt(caract4_tipo);
	}

	public void setCaract4_tipo(String caract4_tipo) {
		this.caract4_tipo = caract4_tipo;
	}

	public String getCaract4_valor() {
		return get(caract4_valor);
	}

	public void setCaract4_valor(String caract4_valor) {
		this.caract4_valor = caract4_valor;
	}

	public String getCaract5_tipo() {
		return getInt(caract5_tipo);
	}

	public void setCaract5_tipo(String caract5_tipo) {
		this.caract5_tipo = caract5_tipo;
	}

	public String getCaract5_valor() {
		return get(caract5_valor);
	}

	public void setCaract5_valor(String caract5_valor) {
		this.caract5_valor = caract5_valor;
	}

	public String getDescipcion() {
		return get(descipcion);
	}

	public void setDescipcion(String descipcion) {
		this.descipcion = descipcion;
	}

	public String getTitulo() {
		return get(titulo);
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescription() {
		return get(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return get(keywords);
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getFecha() {
		return get(fecha);
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return getInt(estado);
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getOrden() {
		return getInt(orden);
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	private String get(String value){
		if (value==null || value.isEmpty())
			return "";
		else
			return value;
	}
	
	private String getInt(String value){
		if (value==null || value.isEmpty())
			return "-1";
		else
			return value;
	}
	
	
}
