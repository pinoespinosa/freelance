package csvMarket;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tiendas.ProductoAbs;
import tiendas.TipoProducto;
import utils.BD;
import utils.LOG;

public class Script {

	


	
	public static String existTipoProductLike(String titulo) {

		return "SELECT COUNT(*) as cant "
				+ "FROM "+ BD.get_BD_NAME() +".tipoproductos AS tip "
				+ "WHERE '"+titulo+"' LIKE concat('%',tip.texto_meli,'%') and tip.texto_meli  IS NOT NULL and tip.texto_meli != ''";

	}
	
	
	public static String getDataTipoProduct(String titulo) {

		return "SELECT concat( tip.id, '_', tip.codigo, '_', tip.nombre, '_', tip.slug, '_', IF(ifnull(tip.subtitulo,'no'),'tip.subtitulo',' '), '_') as info "
				+ "FROM "+BD.get_BD_NAME()+".tipoproductos AS tip "
				+ "WHERE '"+titulo+"' LIKE concat('%',tip.texto_meli,'%') and tip.texto_meli  IS NOT NULL and tip.texto_meli != ''"
				+ "LIMIT 1;";

	}
	
	
	public static String existProductByID(String id) {
	
		return "SELECT count(*) as existe " + "FROM "+BD.get_BD_NAME()+".productos as pro "
				+ "WHERE pro.id_interno_tienda='" + id + "';";

	}
	
	
	public static String existTipoProduct(String nombreProduct) {
		
		return "SELECT count(*) as cant FROM "+BD.get_BD_NAME()+".tipoproductos AS tip WHERE tip.nombre like '" + nombreProduct.split("'")[0] +"';";

	}
	

	public static String existProductByTitle(String nombreProduct) {

		return "SELECT count(*) as cant FROM "+BD.get_BD_NAME()+".productos AS tip WHERE tip.nombre like '" + nombreProduct.split("'")[0] +"';";

	}
	
	public static String createProduct(String id, String codigo, String nombre, String slug, String subtitulo, ProductoAbs dato) throws SQLException{
		
		String tupla = codigo + "','" + nombre + "','" + slug + "','" + subtitulo + "','" + dato.getUrl_redireccion()
				+ "','" + dato.getId() + "','" + dato.getTiendaID() + "','" + id + "'," + dato.getValorNumero() + ",'"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		LOG.creadoPorProductoBase( "Se creo el producto  ----->   " + tupla);
		
		return "INSERT INTO `"+BD.get_BD_NAME()+"`.`productos` "
				+ "(`codigo`,`nombre`, `slug`,`subtitulo`,`url`,`id_interno_tienda`,`tienda`,`tipo_producto`,`precio`,`fecha`,`estado`)	"
				+ "VALUES ('" + tupla  +"', 1);";

		
	}
	
	public static String createProduct(ProductoAbs dato, TipoProducto tip) throws SQLException{
		
		String tupla = 
						ProductoAbs.getNextCode() + "','" + 
						dato.getTitulo().replace("'", "\'") + "','" + 
						TipoProducto.createSlug(dato.getTitulo().replace("'", "\'")) + "','" + 
						dato.getSubtitulo() + "','" + 
						dato.getUrl_redireccion()+ "','" + 
						dato.getId() + "','" + 
						dato.getTiendaID() + "','" + 
						tip.getId() + "'," + 
						dato.getValorNumero() + ",'" + 
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		LOG.creadoPorProductoBase( "Se creo el producto  ----->   " + tupla);
		
		return "INSERT INTO `"+BD.get_BD_NAME()+"`.`productos` "
				+ "(`codigo`,`nombre`, `slug`,`subtitulo`,`url`,`id_interno_tienda`,`tienda`,`tipo_producto`,`precio`,`fecha`,`estado`)	"
				+ "VALUES ('" + tupla  +"', 1);";

		
	}
	
	public static String updateProduct(String precio, String url , String id, String idTienda ) {

		return "UPDATE `"+BD.get_BD_NAME()+"`.`productos` " + "SET `precio`=" + precio
		+ ", `estado`=1 , `url`='"+url+"' " + "WHERE " + "`id_interno_tienda`='" + id + "' AND "
		+ "`tienda`=" + idTienda;

	}
	
	
	public static String createTipoProduct(TipoProducto tip) throws SQLException, ClassNotFoundException{
		
		String tupla = tip.getCodigo() + "','" + tip.getNombre() + "','" + tip.getTexto_meli() + "','" + tip.getSlug()
				+ "','" + tip.getSubtitulo() + "','" + tip.getImagen() + "','" + tip.getMarcaID() + "','"
				+ tip.getCategoria_1() + "','" + tip.getCategoria_2() + "','" + tip.getCategoria_3() + "','"
				+ tip.getCaract1_tipo() + "','" + tip.getCaract1_valor() + "','" + tip.getCaract2_tipo() + "','"
				+ tip.getCaract2_valor() + "','" + tip.getCaract3_tipo() + "','" + tip.getCaract3_valor() + "','"
				+ tip.getCaract4_tipo() + "','" + tip.getCaract4_valor() + "','" + tip.getCaract5_tipo() + "','"
				+ tip.getCaract5_valor() + "','" + tip.getDescipcion() + "','" + tip.getTitulo() + "','"
				+ tip.getDescription() + "','" + tip.getKeywords() + "','" + tip.getFecha() + "','" + tip.getEstado()
				+ "','" + tip.getOrden() + "'";

		LOG.text( "Se creo el tipo producto  ----->   " + tupla);
		
		return "INSERT INTO `"+BD.get_BD_NAME()+"`.`tipoproductos`(`codigo`,`nombre`,`texto_meli`,`slug`,`subtitulo`,`imagen`,`marca`,`categoria`,`subcategoria`,`subsubcategoria`,`caracteristica_tipo_1`,"
				+ "`caracteristica1`,`caracteristica_tipo_2`,`caracteristica2`,`caracteristica_tipo_3`,`caracteristica3`,`caracteristica_tipo_4`,`caracteristica4`,`caracteristica_tipo_5`,"
				+ "`caracteristica5`,`descripcion`,`title`,`description`,`keywords`,`fecha`,`estado`,`orden`) VALUES ('"+tupla+");";

		
	}
	
	public static String getLastTipoProductoCode(){
		return "SELECT codigo FROM "+BD.get_BD_NAME()+".tipoproductos ORDER BY CAST(codigo as SIGNED INTEGER) desc limit 1";
	}
	
	public static String getLastProductoCode(){
		return "SELECT codigo FROM "+BD.get_BD_NAME()+".productos ORDER BY CAST(codigo as SIGNED INTEGER) desc limit 1";
	}
	
}
