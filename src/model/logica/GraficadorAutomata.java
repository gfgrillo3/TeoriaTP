package model.logica;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import model.domain.AFD;
import model.domain.AFND;
import model.graphViz.AutomataToDotString;
import model.graphViz.GraphViz;
import model.input.InputReader;

public class GraficadorAutomata {

	public static void graficarAFND(File archivoAutomata) {

		AFND afnd = InputReader.crearAFND(archivoAutomata);

		String automata = AutomataToDotString.automataNoDeterministicoDotString(afnd.getEstadosFinales(),
				afnd.getTransiciones());

		File graficoAFND = GraphViz.drawer(automata);

		graficar(graficoAFND);

	}

	public static void graficarAFD(AFD afd) {

		String automata = AutomataToDotString.automataDotString(afd.getEstadosFinales(), afd.getTransiciones());
		
		File graficoAFD = GraphViz.drawer(automata);

		graficar(graficoAFD);

	}
	
	private static void graficar(File automata) {
		
		try {
			Desktop.getDesktop().open(automata);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
