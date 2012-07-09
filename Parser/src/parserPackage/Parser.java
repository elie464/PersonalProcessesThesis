package parserPackage;

import org.w3c.dom.Document;

import graphPackage.Graph;



public interface Parser {

	
	public Document parse(String filename);
	public Graph createGraph(Document doc, int id);
}
