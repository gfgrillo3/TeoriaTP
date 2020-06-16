package model.graphViz;

import java.io.File;
import java.io.IOException;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class GraphViz
{
	
	public static File drawer(String automata) {
		
		File archivo = null;
		
		MutableGraph g;
		try {
			g = new Parser().read(automata);
			archivo = Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("graphs/graphVizGen.png"));
			//Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(archivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return archivo;

	}

} 