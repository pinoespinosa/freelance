package tiendas;

import utils.CSVUtils;

public class ProductoImpl extends ProductoAbs {

	public ProductoImpl(String[] excelLine) {
		super();

		this.id = excelLine[0].replace("\"", "").replace(CSVUtils.end_line, "");
		this.titulo = excelLine[1].replace("\"", "").replace(CSVUtils.end_line, "");
		this.subtitulo = excelLine[2].replace("\"", "").replace(CSVUtils.end_line, "");
		this.descripcion = excelLine[3].replace("\"", "").replace(CSVUtils.end_line, "");
		this.valor = excelLine[4].replace("\"", "").replace(CSVUtils.end_line, "");
		this.marca = excelLine[5].replace("\"", "").replace(CSVUtils.end_line, "");
		this.categoria_1 = excelLine[6].replace("\"", "").replace(CSVUtils.end_line, "");
		this.categoria_2 = excelLine[7].replace("\"", "").replace(CSVUtils.end_line, "");
		this.categoria_3 = excelLine[8].replace("\"", "").replace(CSVUtils.end_line, "");
		this.url_redireccion = excelLine[9].replace("\"", "").replace(CSVUtils.end_line, "");
		this.url_imagen_1 = excelLine[10].replace("\"", "").replace(CSVUtils.end_line, "");
		this.url_imagen_2 = excelLine[11].replace("\"", "").replace(CSVUtils.end_line, "");
		this.url_imagen_3 = excelLine[12].replace("\"", "").replace(CSVUtils.end_line, "");
		this.url_imagen_4 = excelLine[13].replace("\"", "").replace(CSVUtils.end_line, "");
		this.url_imagen_5 = excelLine[14].replace("\"", "").replace(CSVUtils.end_line, "");
		this.tienda = excelLine[15].replace("\"", "").replace(CSVUtils.end_line, "");
		
		if(excelLine.length>16)
			this.stock = excelLine[16].replace("\"", "").replace(CSVUtils.end_line, "");	
		else
			this.stock="";
		
		original_url_imagen_1 = getUrl_imagen_1().toString();
		original_url_imagen_2 = getUrl_imagen_2().toString();
		original_url_imagen_3 = getUrl_imagen_3().toString();
		original_url_imagen_4 = getUrl_imagen_4().toString();
		original_url_imagen_5 = getUrl_imagen_5().toString();
		
		setUrl_imagen_1(editUrl(getUrl_imagen_1()));
		setUrl_imagen_2(editUrl(getUrl_imagen_2()));
		setUrl_imagen_3(editUrl(getUrl_imagen_3()));
		setUrl_imagen_4(editUrl(getUrl_imagen_4()));
		setUrl_imagen_5(editUrl(getUrl_imagen_5()));

	}
}
