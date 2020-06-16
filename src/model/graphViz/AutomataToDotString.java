package model.graphViz;

import java.util.List;
import model.domain.Estado;
import model.domain.Transicion;
import model.logica.StringGenerator;

public class AutomataToDotString {

	public static String automataDotString(List<List<Estado>> estadosFinales, List<Transicion> transiciones) {

		String automaton = "digraph finite_state_machine { rankdir=LR; ";
		automaton += stringEstadosFinales(estadosFinales);
		automaton += "node [shape=plaintext] \" \"; ";
		automaton += "node [shape = circle]; ";
		automaton += " \" \" -> \"{1}\"; ";
		automaton += stringTransiciones(transiciones);
		automaton += "}";
		
		return automaton;
	}
	
	public static String automataNoDeterministicoDotString(List<Estado> estadosFinales, List<Transicion> transiciones) {

		String automaton = "digraph finite_state_machine { rankdir=LR; ";
		automaton += stringEstadosFinalesAFND(estadosFinales);
		automaton += "node [shape=plaintext] \" \"; ";
		automaton += "node [shape = circle]; ";
		automaton += " \" \" -> \"{1}\"; ";
		automaton += stringTransiciones(transiciones);
		automaton += "}";
		
		return automaton;
	}
	
	private static String stringEstadosFinales(List<List<Estado>> estadosFinales) {

		String stringEstadosFinales = "node [shape = doublecircle]; ";

		for(List<Estado> listEstados : estadosFinales)
			stringEstadosFinales+= "\""+StringGenerator.toStringListEstados(listEstados)+"\" ";
		
		stringEstadosFinales += ";";

		return stringEstadosFinales;
	}

	private static String stringEstadosFinalesAFND(List<Estado> estadosFinales) {

		String stringEstadosFinales = "node [shape = doublecircle]; ";

		for(Estado estado : estadosFinales)
			stringEstadosFinales+= "\"{"+estado.getValor()+"}\" ";
		
		stringEstadosFinales += ";";

		return stringEstadosFinales;
	}

	private static String stringTransiciones(List<Transicion> transiciones) {
		
		String strTransiciones = "";
		
		for(Transicion transicion : transiciones)
			strTransiciones += stringTransicion(transicion);


		return strTransiciones;
	}
	
	private static String stringTransicion(Transicion transicion) {

		//LR_0 -> LR_2 [ label = \"SS(B)\" ];
		
		String strTransicion = "";

		strTransicion += "\"" + StringGenerator.toStringListEstados(transicion.getEstadoInicial())+ "\"";
		strTransicion += " -> ";
		strTransicion += "\"" + StringGenerator.toStringListEstados(transicion.getEstadoFinal()) + "\"";
		strTransicion += " [label = \""+transicion.getInput()+"\" ]; ";

		return strTransicion;
	}
	

	

}
