package model.parserNRP;

import java.util.HashMap;
import java.util.List;

import model.domain.Gramatica;
import model.domain.Produccion;
import model.domain.Variable;

public class ParserTable {

	private HashMap<String, HashMap<String, Produccion>> TablaDeParsing;
	HashMap<String, char[]> firstGramatica;
	HashMap<String, char[]> followGramatica;
	Gramatica gramatica;

	public ParserTable(Gramatica gramatica) {
		this.gramatica = gramatica;
		this.firstGramatica = FirstFollow.getFirst(this.gramatica);
		this.followGramatica = FirstFollow.getFollow(this.gramatica);
		this.TablaDeParsing = new HashMap<String, HashMap<String, Produccion>>();
		this.construirHashMaps();
		this.construirTabla();
		this.toString();
	}

	// creo la tabla de parsing
	private void construirHashMaps() {

		HashMap<String, HashMap<String, Produccion>> hashMapVariable = new HashMap<String, HashMap<String, Produccion>>();

		for (Variable variable : this.gramatica.getVariables()) {

			HashMap<String, Produccion> hashMapCaracteres = new HashMap<String, Produccion>();

			for (char c : this.gramatica.getTerminales())
				hashMapCaracteres.put(c + "", null);

			hashMapCaracteres.put("$", null);

			hashMapVariable.put(variable.getStringVariable(), hashMapCaracteres);

		}
		this.TablaDeParsing = hashMapVariable;

	}

	private void construirTabla() {

		//RECORRO LAS PRODUCCIONES
		for (Produccion produccion : this.gramatica.getProducciones()) {
			
			//ME QUEDO CON EL PRIMER SIMBOLO DE LA DERECHA DE LA PRODUCCION 
			String primerSimbolo = produccion.getCuerpo().get(0);
			boolean esAnulable = false;
			
			//SI EL SIMBOLO ES VACIO, NO ME SIRVE ESA PRODUCCION
			if(!primerSimbolo.equals("#")) {
				
				//ME QUEDO CON LOS FIRST DEL PRIMER SIMBOLO DE LA DERECHA
				String firstsPrimerSimbolo = new String(this.firstGramatica.get(primerSimbolo));
				
				
				//indice para recorrer lo que sigue al simbolo en el que estoy
				int j = 0;
				esAnulable = esAnulable(firstsPrimerSimbolo.toCharArray());
				
				

				
				
				//mientras tenga un simbolo siguiente y ademas sea anulable
				//voy a fijarme los first de lo que le sigue al simbolo ese
				while(j+1<produccion.getCuerpo().size() && esAnulable) {
					
					//si no existia agrego los char
					firstsPrimerSimbolo += new String(this.firstGramatica.get(produccion.getCuerpo().get(j+1)));
					
					esAnulable =  esAnulable(this.firstGramatica.get(produccion.getCuerpo().get(j+1)));
					j++;
				}
							
				//ELIMINO EL EPSILON
				firstsPrimerSimbolo = firstsPrimerSimbolo.replaceAll("[#]", "");
						
					
				//AGREGO LA PRODUCCION, A CADA TERMINAL DE FIRST Y A LA VARIABLE
				for(char terminal : firstsPrimerSimbolo.toCharArray())
					this.TablaDeParsing.get(produccion.getVariable().getStringVariable()).put(terminal+"", produccion);
			
			}
			//SI EL STRING ES ANULABLE, AGREGO LOS FOLLOW
			if(primerSimbolo.equals("#") || esAnulable) {
		
				//AGREGO LOS FOLLOW DE VARIABLE IZQUIERDA A FOLLOW DE SIMBOLO
				char[] followsVariableProduccion = this.followGramatica.get(produccion.getVariable().getStringVariable());
				for(char terminal : followsVariableProduccion) 
					this.TablaDeParsing.get(produccion.getVariable().getStringVariable()).put(terminal+"", produccion);		
				
				
			}
		}

	}
	
	private boolean esAnulable(char[] firsts) {

		for (char c : firsts) {
			if ((int) c == 35)
				return true;
		}

		return false;
	}

	public HashMap<String, HashMap<String, Produccion>> getTablaParsing() {
		return this.TablaDeParsing;
	}
	
	@Override 
	public String toString(){
		this.TablaDeParsing.entrySet().forEach(entry->{
			System.out.println(" VAR : "+ entry.getKey()
			);
			printMap2(entry.getValue());
		});
		
		return "";
	}
	
	
	public void printMap2(HashMap<String, Produccion> hashmap) {
		
		hashmap.entrySet().forEach(entry2->{
			if(entry2.getValue() != null )
				System.out.println(" CHAR "+entry2.getKey()+" PROD "+ entry2.getValue().getVariable().getStringVariable() +" ->"+
		cuerpoToString(entry2.getValue().getCuerpo()));
		});
		
	}
	
public void printMapFollow(HashMap<String, char[]> hashmap) {
		
		hashmap.entrySet().forEach(entry2->{
			if(entry2.getValue() != null )
				System.out.println(" VAR "+entry2.getKey()+" FOLLOW "+ new String(entry2.getValue()));
		});
		
	}
	
	public String cuerpoToString(List<String> list) {
		String ret = "";
		
		for(String cuerpo : list)
			ret += cuerpo;
		
		return ret;
	}
}
