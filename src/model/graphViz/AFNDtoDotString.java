package model.graphViz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.domain.Estado;
import model.domain.Transicion;
import model.logica.StringGenerator;

public class AFNDtoDotString {

	String automaton = "digraph finite_state_machine { rankdir=LR; ";

	public void graficarAutomata(List<List<Estado>> estadosFinales, List<Transicion> transiciones) {

		
		automaton += this.stringEstadosFinales(estadosFinales);
		automaton += "node [shape=plaintext] \" \"; ";
		automaton += "node [shape = circle]; ";
		automaton += " \" \" -> \"{1}\"; ";
		automaton += this.stringTransiciones(transiciones);
		automaton += "}";
		
		GraphViz.drawer(automaton);
	}

	private String stringEstadosFinales(List<List<Estado>> estadosFinales) {

		String stringEstadosFinales = "node [shape = doublecircle]; ";

		for(List<Estado> listEstados : estadosFinales)
			stringEstadosFinales+= "\""+StringGenerator.toStringListEstados(listEstados)+"\" ";
		
		stringEstadosFinales += ";";

		return stringEstadosFinales;
	}

	private String stringTransiciones(List<Transicion> transiciones) {
		
		String strTransiciones = "";
		
		for(Transicion transicion : transiciones)
			strTransiciones += this.stringTransicion(transicion);


		return strTransiciones;
	}
	
	private String stringTransicion(Transicion transicion) {

		//LR_0 -> LR_2 [ label = \"SS(B)\" ];
		
		String strTransicion = "";

		strTransicion += "\"" + StringGenerator.toStringListEstados(transicion.getEstadoInicial())+ "\"";
		strTransicion += " -> ";
		strTransicion += "\"" + StringGenerator.toStringListEstados(transicion.getEstadoFinal()) + "\"";
		strTransicion += " [label = \""+transicion.getInput()+"\" ]; ";

		return strTransicion;
	}
	

	

}
