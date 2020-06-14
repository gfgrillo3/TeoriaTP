package model.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.domain.AFND;
import model.domain.Estado;
import model.domain.Transicion;

public class InputReader {

	public AFND crearAFND() {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		String alfabeto = null;
		int cantidadEstados = 0;
		String[] estadosFinales = null;
		List<Transicion> transiciones = new ArrayList<Transicion>();
		
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File("C:\\Users\\Franco\\Desktop\\afndClase.txt");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			int indiceLinea = 0;
			
			while ((linea = br.readLine()) != null) {
				//System.out.println(linea);
				linea = limpiarEspaciosBlancos(linea);
				if(indiceLinea == 0)
					alfabeto = linea.replace(",", "");
				else if(indiceLinea == 1)
					cantidadEstados= Integer.parseInt(linea);
				else if(indiceLinea == 2) {
					estadosFinales = linea.split(",");
				}
				else if(indiceLinea > 2 ) {
					transiciones.add(crearTransicion(linea));
				}
				indiceLinea++; 
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
		
		//CONVIERTO DATOS EN AFND
		return convertirInputEnAFND(alfabeto, cantidadEstados, estadosFinales, transiciones);
	}
	
	private AFND convertirInputEnAFND(String alfabeto, int cantidadEstados, String[] estadosFinales,
			List<Transicion> transiciones) {
		
		char[] alfabetoInputAFMD;
		alfabetoInputAFMD = alfabeto.toCharArray();
		List<Integer> estadosFinalesAFND = new ArrayList<Integer>();		
		
		for(String estado : estadosFinales) {
			estadosFinalesAFND.add(Integer.parseInt(estado));
		}
		
		AFND afnd = new AFND(alfabetoInputAFMD, cantidadEstados, estadosFinalesAFND, transiciones);
		afnd.toString();
		return afnd;
	}

	public Transicion crearTransicion(String lineaTransicion){
		
		String lineaLimpiaDeEspacios = limpiarEspaciosBlancos(lineaTransicion);
		String estadoInicial = "";
		char input = 0;
		String estadoFinal = "";
		
		int indiceComa = 0;
		int indiceFlecha = 0;
		
		for(int i = 0; i<lineaLimpiaDeEspacios.length(); i++) {
			if(lineaLimpiaDeEspacios.charAt(i) == ",".charAt(0)) {
				indiceComa = i;
			}
			else if(lineaLimpiaDeEspacios.charAt(i) == "-".charAt(0)) {
				indiceFlecha = i;
			}
		}
		
		for(int ei = 0; ei<indiceComa; ei++) {
			estadoInicial = estadoInicial + String.valueOf(lineaLimpiaDeEspacios.charAt(ei));
		}
		for(int c = indiceComa+1; c<indiceFlecha; c++) {
			input = lineaLimpiaDeEspacios.charAt(c);
		}
		for(int ef = indiceFlecha+2; ef<lineaLimpiaDeEspacios.length(); ef++) {
			estadoFinal = estadoFinal + String.valueOf(lineaLimpiaDeEspacios.charAt(ef));
		}
		
		//UNA VEZ QUE TENGO TODOS LOS DATOS CREO LA TRANSICIÓN
		ArrayList<Estado> estadoI = new ArrayList<Estado>();
		ArrayList<Estado> estadoF = new ArrayList<Estado>();
		
		estadoI.add(new Estado(Integer.parseInt(estadoInicial)));
		estadoF.add(new Estado(Integer.parseInt(estadoFinal)));
		
		
		Transicion transicion = new Transicion(estadoI, input, estadoF);
		
		return transicion;
	}
	 
	
	private String limpiarEspaciosBlancos(String linea){
		String lineaLimpia = linea.replace(" ", "");
		return lineaLimpia;
	}

}
