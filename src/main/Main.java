package main;

import controller.ControladorPrincipal;
import model.AFNDtoAFD.AFNDtoAFD;
import model.domain.AFD;
import model.domain.AFND;
import model.graphViz.AutomataToDotString;
import model.input.InputReader;
import view.MainWindow;

public class Main {
	
	static {
	     System.setProperty("java.awt.headless", "false");
	}

	public static void main(String[] args) {

		/*
		Transicion t1 = new Transicion(new Estado(1), 'a', new Estado(3));
		Transicion t2 = new Transicion(new Estado(1), 'a', new Estado(4));
		Transicion t3 = new Transicion(new Estado(1), 'a', new Estado(5));
		Transicion t4 = new Transicion(new Estado(1), 'b', new Estado(4));
		
		Transicion t5 = new Transicion(new Estado(1), 'b', new Estado(6));
		Transicion t6 = new Transicion(new Estado(1), 'a', new Estado(8));
		Transicion t7 = new Transicion(new Estado(1), 'c', new Estado(8));
		Transicion t8 = new Transicion(new Estado(3), 'a', new Estado(15));
		Transicion t9 = new Transicion(new Estado(3), 'b', new Estado(16));
		Transicion t10 = new Transicion(new Estado(5), 'a', new Estado(18));
		Transicion t11 = new Transicion(new Estado(8), 'b', new Estado(17));
		Transicion t12 = new Transicion(new Estado(64), 'a', new Estado(65));
		
		List<Transicion> transiciones = new ArrayList<Transicion>();
		transiciones.add(t3);
		transiciones.add(t4);
		transiciones.add(t2);
		transiciones.add(t1);
		
		transiciones.add(t5);
		transiciones.add(t6);
		transiciones.add(t7);
		transiciones.add(t8);
		transiciones.add(t9);
		transiciones.add(t10);
		transiciones.add(t11);
		transiciones.add(t12);
		
		
		char[] inputs = new char[] {'a','b','c'};
		
		List<Integer> estadosFinales = new ArrayList<>();
		estadosFinales.add(8);
		estadosFinales.add(17);
		
		
		AFND afnd = new AFND(inputs, 32, estadosFinales, transiciones);
		*/
		//PRINT DE TRANSICIONES DEL AFND
		/*
		for(Transicion t : afnd.getTransiciones()) {
			System.out.println("EI = "+t.getEstadoInicial().getValor()+" , IN = "+t.getInput()+" , EF = "+t.getEstadoFinal().getValor());
		}
		*/
		
		
		/*
		InputReader input = new InputReader();
		
		AFND afnd = InputReader.crearAFND();
		
		
		AFNDtoAFD a = new AFNDtoAFD();
		AFD afd = a.fromAFNDtoAFD(afnd);
		
		
		System.out.println(afd.toString());
		System.out.println(afd.procesarString("arrrrra"));
		
		AutomataToDotString dot = new AutomataToDotString();
		dot.graficarAutomata(afd.getEstadosFinales(), afd.getTransiciones());
		*/
			
		MainWindow vista = new MainWindow();
		ControladorPrincipal controlador = new ControladorPrincipal(vista);
		controlador.inicializarVentanaPrincipal();

	}

}
	

