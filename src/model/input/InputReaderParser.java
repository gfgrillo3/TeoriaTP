package model.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.domain.Gramatica;
import model.domain.Produccion;
import model.domain.Variable;

public class InputReaderParser {

	public static Gramatica crearGramatica(File archivo) {

		//File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
				
		List<Produccion> listaProducciones = new ArrayList<Produccion>();
		
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			//File archivo = new File("C:\\Users\\Gustavo\\Desktop\\gramatica.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			
			while ((linea = br.readLine()) != null) {
				listaProducciones.add(crearProduccion(linea));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return new Gramatica(listaProducciones);
	}
	
	private static Produccion crearProduccion(String linea) {
		
		String stringVariable = "";
		String simbolosDeProducciones = "";
		String[] simbolosDeProduccion = null;
		List<String> cuerpo = new ArrayList<String>();
		//INDICE DE DONDE COMIENZA FLECHA
		int indiceFlecha = 0;
		//RECORRO HASTA ENCONTRAR LA FLECHA
		for(int i = 0; i<linea.length(); i++) {
			if(linea.charAt(i) == '-')
				indiceFlecha = i;
		}
		//GENERO EL STRING DE LA VARIABLE IZQUIERDA
		for(int j=0; j<indiceFlecha-1; j++) {
			stringVariable += linea.charAt(j);
		}
		//GENERO EL STRING DE LOS SIMBOLOS DE LA DERECHA
		for(int k=indiceFlecha+3; k<linea.length(); k++) {
			simbolosDeProducciones += linea.charAt(k);
		}
		//CREO EL ARRAY DE SIMBOLOS 
		simbolosDeProduccion = simbolosDeProducciones.split(" ");
		//AGREGO CADA SIMBOLO A LA NUEVA LISTA DE CUERPO DE LA PRODUCCION
		for(String simbolo: simbolosDeProduccion) {
			cuerpo.add(simbolo);
		}		
		//CREO LAS NUEVAS VARIABLES A ENTREGAR
		Variable variable = new Variable(stringVariable);
		Produccion produccion = new Produccion(variable, cuerpo);
		
		return produccion;		
	}
		 
}
