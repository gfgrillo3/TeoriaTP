package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.ControladorPrincipal;
import model.AFNDtoAFD.AFNDtoAFD;
import model.domain.AFD;
import model.domain.AFND;
import model.domain.Gramatica;
import model.domain.Produccion;
import model.domain.Variable;
import model.graphViz.AutomataToDotString;
import model.input.InputReader;
import model.input.InputReaderParser;
import model.parserNRP.FirstFollow;
import model.parserNRP.ParserSolver;
import model.parserNRP.ParserTable;
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
		 
		
		/*
		
		X_{1} -> X_{2}z
		X_{1} -> X_{5}w
		X_{1} -> X_{5}X_{2}y

		X_{2} -> #
		
		X_{3} -> X_{4}
		
		X_{4} -> z	
		
		X_{5} -> #

		
		*/
		Variable v1 = new Variable("X_{1}");
		Variable v2 = new Variable("X_{2}");
		Variable v3 = new Variable("X_{3}");
		Variable v4 = new Variable("X_{4}");
		Variable v5 = new Variable("X_{5}");
		
		ArrayList<String> cuerpoProdsV1 = new ArrayList<String>();
		cuerpoProdsV1.add("X_{2}");
		cuerpoProdsV1.add("z");
		Produccion prodV1 = new Produccion(v1, cuerpoProdsV1);

		ArrayList<String> cuerpoProdsV1a5 = new ArrayList<String>();
		cuerpoProdsV1a5.add("X_{5}");
		cuerpoProdsV1a5.add("w");
		Produccion prodV1a5 = new Produccion(v1, cuerpoProdsV1a5);
		
		ArrayList<String> cuerpoProdsV1a5y2 = new ArrayList<String>();
		cuerpoProdsV1a5y2.add("X_{5}");
		cuerpoProdsV1a5y2.add("X_{2}");
		cuerpoProdsV1a5y2.add("y");
		Produccion prodV1a5y2 = new Produccion(v1, cuerpoProdsV1a5y2);

		ArrayList<String> cuerpoProdsV2 = new ArrayList<String>();
		//cuerpoProdsV2.add("r");
		cuerpoProdsV2.add("#");
		Produccion prodV2 = new Produccion(v2, cuerpoProdsV2);
		
		ArrayList<String> cuerpoProdsV3 = new ArrayList<String>();
		cuerpoProdsV3.add("X_{4}");
		Produccion prodV3 = new Produccion(v3, cuerpoProdsV3);
		
		ArrayList<String> cuerpoProdsV4 = new ArrayList<String>();
		cuerpoProdsV4.add("z");
		Produccion prodV4 = new Produccion(v4, cuerpoProdsV4);
		
		ArrayList<String> cuerpoProdsV5 = new ArrayList<String>();
		cuerpoProdsV5.add("#");
		Produccion prodV5 = new Produccion(v5, cuerpoProdsV5);		
		
		ArrayList<Produccion> produccionesGramatica = new ArrayList<Produccion>();
		produccionesGramatica.add(prodV1);
		produccionesGramatica.add(prodV2);
		produccionesGramatica.add(prodV3);
		produccionesGramatica.add(prodV4);
		produccionesGramatica.add(prodV5);
		produccionesGramatica.add(prodV1a5);
		produccionesGramatica.add(prodV1a5y2);
		
		ArrayList<Variable> variablesGramatica = new ArrayList<Variable>();
		variablesGramatica.add(v1);
		variablesGramatica.add(v2);
		variablesGramatica.add(v3);
		variablesGramatica.add(v4);
		variablesGramatica.add(v5);
		
		Gramatica gramatica = new Gramatica(produccionesGramatica, variablesGramatica, new char[]{'r','z', 'w', 'y'});
		
		HashMap<String, char[]> firstGramatica = FirstFollow.getFirst(gramatica);
		
		firstGramatica.forEach((variable,firsts) -> System.out.println("Variable: " + variable + " --->  FIRSTS : " + charArrayPrint(firsts)));
				
		System.out.println("------------------------------------------------------------------");
		
		HashMap<String, char[]> followGramatica = FirstFollow.getFollow(gramatica);
		followGramatica.forEach((variable,follows) -> System.out.println("Variable: " + variable + " --->  FOLLOWS : " + charArrayPrint(follows)));		
		
		
		ParserTable tabla = new ParserTable(gramatica);
		
		tabla.getTablaParsing().entrySet().forEach(entry->{
			System.out.println(" VAR : "+ entry.getKey()
			);
			printMap2(entry.getValue());
		});
		
		String s = "z";
		
		System.out.println("El string: " + s + " pertenece? = " + ParserSolver.resolver(s, tabla));
		
		/*
		Gramatica gramaticaN = InputReaderParser.crearGramatica();

		for(Variable v : gramaticaN.getVariables()) {
			System.out.println(v.getStringVariable());
		}
		
		for(char a : gramaticaN.getTerminales()) {
			System.out.println(a);
		}
		
		for(Produccion p: gramaticaN.getProducciones()) {
				System.out.println(p.getCuerpo().size());
		}
		
		example.entrySet().forEach(entry->{
		    System.out.println(entry.getKey() + " " + entry.getValue());  
		 });
		 */
		/*
		tabla.getTablaParsing().forEach((variable,otroHash) -> otroHash.forEach((caracter,produccion) -> 
				System.out.println("Variable: " + variable + " --->  CARACTER : " + caracter + " --->   PRODUCCION : " + produccion.getVariable().getStringVariable()
						+ " ->"+ cuerpoToString(produccion.getCuerpo()))));
		//(variable,follows) -> System.out.println("Variable: " + variable + " --->  FOLLOWS : " + charArrayPrint(follows))
		
		*/
	}
	
	private static String charArrayPrint(char[] charArr) {
		String ret = "";
		for(char c : charArr)
			ret += c+" ,";
		return ret;
	}
	
	public static void printMap2(HashMap<String, Produccion> hashmap) {
		
		hashmap.entrySet().forEach(entry2->{
			if(entry2.getValue() != null )
				System.out.println(" CHAR "+entry2.getKey()+" PROD "+ entry2.getValue().getVariable().getStringVariable() +" ->"+
		cuerpoToString(entry2.getValue().getCuerpo()));
		});
		
	}
	
	
	public static String cuerpoToString(List<String> list) {
		String ret = "";
		
		for(String cuerpo : list)
			ret += cuerpo;
		
		return ret;
	}

}
	

