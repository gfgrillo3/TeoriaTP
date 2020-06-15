package model.graphViz;

import java.io.File;
import java.io.IOException;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class GraphViz
{
	
	public static void drawer(String automata) {
		
		MutableGraph g;
		try {
			g = new Parser().read(automata);
			Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("graphs/graphVizGen.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

} 