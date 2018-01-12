package datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;


public class SimpleFile {


	public static void writeFile (String path, String fileName, String texto){
		try{
			PrintWriter writer = new PrintWriter(path + fileName, "UTF-8");
			if (!texto.isEmpty())
				writer.println(texto);
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void writeFile (String path, String fileName, List<String> texto){
		try{
			PrintWriter writer = new PrintWriter(path + fileName, "UTF-8");
			writer.println("Header");
			while (!texto.isEmpty()){
				writer.println(texto.get(0));
				texto.remove(0);}
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public static void deleteFile (String path, String fileName){

		File s = new File(path+fileName);
		s.delete();

	}



	public static List<String> readFile (String path, String fileName){

		List<String> lista = new ArrayList<String>();
		BufferedReader reader=null;
		try {
			
			reader = new BufferedReader(new FileReader(path+fileName));
			String line = null;

			while ( (line = reader.readLine()) != null) 
				lista.add(line);
			
			reader.close();
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, errors.toString() );
		}
		return lista;

	}

	
	public static void removeRepetidosFile (String path, String fileName){

		// Leo el archivo
		List<String> archivo = SimpleFile.readFile(path, fileName);
		
		// SQL no diferencia entre una frase terminada con un espacio de otra sin espacio, entonces por cada columnas 
		// elimino el espacio si es el último caracter
		Set <String> archivosSinRepetidos = new HashSet<String>();
		for (String registro : archivo) {
			String[] arregloColumnas = registro.split("	");
			registro="";					
			for (String columna : arregloColumnas) {
				
				while (columna.endsWith(" "))
						columna=columna.substring(0,columna.length()-1);
				
				registro+=columna + "	";
			}
		
			registro=registro.substring(0,registro.length()-1);
								
			archivosSinRepetidos.add(registro);
		}
		
		// Limpio el archivo y le paso el conjunto sin repetidos
		archivo.clear();
		archivo.add("header");
		archivo.addAll(archivosSinRepetidos);				
		
		// Escribo el archivo
		SimpleFile.writeFile(path, fileName, archivo);

	}
}
