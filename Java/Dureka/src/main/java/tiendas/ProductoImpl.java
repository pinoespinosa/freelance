package tiendas;

public class ProductoImpl extends ProductoAbs {

	public ProductoImpl(String[] excelLine) {
		super();

		this.id = excelLine[0];
		this.titulo = excelLine[1];
		this.subtitulo = excelLine[2];
		this.descripcion = excelLine[3];
		this.valor = excelLine[4];
		this.marca = excelLine[5];
		this.categoria_1 = excelLine[6];
		this.categoria_2 = excelLine[7];
		this.categoria_3 = excelLine[8];
		this.url_redireccion = excelLine[9];
		this.url_imagen_1 = excelLine[10];
		this.url_imagen_2 = excelLine[11];
		this.url_imagen_3 = excelLine[12];
		this.url_imagen_4 = excelLine[13];
		this.url_imagen_5 = excelLine[14];
		this.tienda = excelLine[15];
		
		if(excelLine.length>16)
			this.stock = excelLine[16];	
		else
			this.stock="";

	}
}
