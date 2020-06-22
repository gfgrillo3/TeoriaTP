package model.parserNRP;

import java.util.List;
import java.util.Stack;

public class ParserSolver {
	
	
	public static String resolver(String stringInput, ParserTable tablaDeParsing) {
		
		Stack<String> pila = new Stack<String>();
		//INICIALIZO LA PILA AGREGANDO $ AL COMINEZO
		inicializarPila(pila);
		//AGERGO $ AL FINAL DEL STRING DE ENTRADA
		String entrada = stringInput + "$";
		//PUNTERO QUE SEÑALA EN QUE CHAR DE LA ENTRADA ESTAMOS PARADOS 
		int punteroCharEntrada = 0;
		String charEntrada = entrada.charAt(punteroCharEntrada)+"";
		//FLAG QUE INDICA SI SURGIO UN ERROR
		boolean hayError = false;
		
		while(!hayError) {
			//SI EL TOPE DE LA PILA ES IGUAL AL CHAR DE LA ENTRADA 
			//HAGO UN POP DE LA PILA Y CORRO EL PUNTERO DEL CHAR HACIA EL SIGUIENTE
			if(pila.peek().equals(charEntrada)) {
				System.out.println(charEntrada + " y " + pila.peek() +" son iguales");
				pila.pop();
				punteroCharEntrada += 1;
				charEntrada = entrada.charAt(punteroCharEntrada)+"";
			}
			else{
				//SI EL TOPE DE LA PILA ES UN TERMINAL GENERO UN ERROR
				if(esTerminal(pila.peek().toString())) {
					System.out.println("Error! el tope " + pila.peek() + " es distinto al char " +  charEntrada);
					hayError = true;
				}
				//SI EL TOPE DE LA PILA NO ES TERMINAL ES UNA VARIABLE, POR LO TANTO, CHEQUEO LA TABLA
				else{
					//SI EN LA POSICION DE LA TABLA [X,a] HAY UNA PRODUCCION
					//REALIZO UN POP DE LA PILA Y LUEGO LE AGREGO EL CUERPO DE LA PRODUCCION
					if(tablaDeParsing.getTablaParsing().get(pila.peek()).get(charEntrada) != null) {
						System.out.println("Utilizo la produccion= " +
						tablaDeParsing.getTablaParsing().get(pila.peek()).get(charEntrada).getVariable().getStringVariable()
						+ " -->" +
						cuerpoToString(tablaDeParsing.getTablaParsing().get(pila.peek()).get(charEntrada).getCuerpo()));
						//ELIMINO TOPE DE PILA Y ENCOLO LA NUEVA PRODUCCION
						String auxiliarTopePila = pila.peek();
						pila.pop();
						for(int i = tablaDeParsing.getTablaParsing().get(auxiliarTopePila).get(charEntrada).getCuerpo().size()-1;
								i>=0;i--){
							if(!tablaDeParsing.getTablaParsing().get(auxiliarTopePila).get(charEntrada).getCuerpo().get(i).equals("#")) {
								pila.push(tablaDeParsing.getTablaParsing().get(auxiliarTopePila).get(charEntrada).getCuerpo().get(i));
							}
						}
					}
					//SI EN LA POSICION [X,a] DE LA TABLA NO HAY UNA PRODUCCION GENERO UN ERROR
					else {
						System.out.println("Error! no hay producción para T[" + pila.peek() + "," + charEntrada + "]");
						hayError = true;
					}
				}
			}
			//SI EN LA PILA Y EN EL CHAR SE TIENE EL SIMBOLO $ EL STRING ES ACEPTADO
			if(llegaronAlFinal(pila.peek(), charEntrada))
				return "ACEPTADO";
		}
		//SI LUEGO DE CICLAR NO SE LLEGO AL SIMBOLO $ EN AMBOS LADOS EL STRING SE RECHAZA
		return "RECHAZADO";
	}

	//DEVUELVE TRUE SI AMBOS STRINGS SON 4
	private static boolean llegaronAlFinal(String peek, String charEntrada) {
		return (peek.equals("$") && charEntrada.equals("$"));
	}
	//INICIALIZO LA PILA AGREGANDO EL $ Y EL SIMBOLO INICIAL
	private static void inicializarPila(Stack<String> pila) {		
		pila.push("$");
		pila.push("X_{1}");		
	}
	//TRUE SI EL LARGO DEL STRING ES 1 (LAS VARIABLES TIENEN LARGO 5 MINIMO)
	private static boolean esTerminal(String entrada) {
		return entrada.length() == 1;
	}
	//IMPRIME CUERPÓ DE LA PRODUCCION
	public static String cuerpoToString(List<String> list) {
		String ret = "";
		for(String cuerpo : list)
			ret += cuerpo;
		return ret;
	}

	
}
