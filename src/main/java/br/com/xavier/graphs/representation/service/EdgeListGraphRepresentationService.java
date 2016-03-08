package br.com.xavier.graphs.representation.service;

import org.springframework.stereotype.Service;

import br.com.xavier.graphs.impl.edges.DefaultUnweightedEdge;
import br.com.xavier.graphs.impl.edges.DefaultWeightedEdge;
import br.com.xavier.graphs.impl.nodes.NumberedNode;
import br.com.xavier.graphs.impl.simple.directed.DefaultSDUGraph;
import br.com.xavier.graphs.impl.simple.directed.DefaultSDWGraph;
import br.com.xavier.graphs.impl.simple.undirected.DefaultSUUGraph;
import br.com.xavier.graphs.impl.simple.undirected.DefaultSUWGraph;
import br.com.xavier.graphs.interfaces.Graph;
import br.com.xavier.graphs.representation.model.Delimiters;
import br.com.xavier.graphs.representation.model.GraphProperties;

@Service
public class EdgeListGraphRepresentationService {
	
	public EdgeListGraphRepresentationService() {}

	public String generateEdgesListGraphScript(
		GraphProperties graphProperties, 
		String htmlElementContainer,
		String graphWidgetVar,
		String textRepresentation,
		Delimiters delimiters
	){
		if(graphProperties.isWeightedGraph()){
			return generateWeightedEdgesListGraphScript(graphProperties);
		} else {
			return generateUnweightedEdgesListGraphScript(graphProperties);
		}
	}
	
	private String generateWeightedEdgesListGraphScript(GraphProperties graphProperties) {
		Graph<NumberedNode, DefaultWeightedEdge<NumberedNode>> graph = null;
		
		if(graphProperties.isDirectedGraph()){
			graph = new DefaultSDWGraph<>();			
		} else {
			graph = new DefaultSUWGraph<>();
		}
		
		return "";
	}
	
	private String generateUnweightedEdgesListGraphScript(GraphProperties graphProperties) {
		Graph<NumberedNode, DefaultUnweightedEdge<NumberedNode>> graph = null;
		
		if(graphProperties.isDirectedGraph()){
			graph = new DefaultSDUGraph<>();			
		} else {
			graph = new DefaultSUUGraph<>();
		}
		
		return "";
	}
	

}
