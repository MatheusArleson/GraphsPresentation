package br.com.xavier.graphs.representation.service;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Service;

import br.com.xavier.graphs.impl.edges.DefaultWeightedEdge;
import br.com.xavier.graphs.impl.nodes.NumberedNode;
import br.com.xavier.graphs.impl.nodes.NumberedNodesFactory;
import br.com.xavier.graphs.impl.parser.CytoscapeWeightedParser;
import br.com.xavier.graphs.impl.simple.undirected.DefaultSUWGraph;
import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.model.enums.GraphRepresentations;
import br.com.xavier.graphs.representation.model.impl.DefaultAdjacencyMatrixRepresentation;
import br.com.xavier.graphs.representation.model.impl.DefaultEdgeListRepresentation;
import br.com.xavier.graphs.representation.model.interfaces.RepresentationParser;
import br.com.xavier.graphs.representation.util.StringUtil;
import br.com.xavier.graphs.representation.util.checkers.GraphRepresentationChecker;
import br.com.xavier.graphs.representation.util.checkers.NullChecker;
import br.com.xavier.jsf.PrimefacesUtil;

@Service
public class GraphPresentationService {
	
	//XXX GRAPH REPRESENTATIONS PROPERTIES
	private DefaultAdjacencyMatrixRepresentation adjMatrixRepresentation;
	private DefaultEdgeListRepresentation edgesListRepresentation;
	
	public GraphPresentationService() {	}
	
	@PostConstruct
	private void initialize(){
		this.adjMatrixRepresentation = new DefaultAdjacencyMatrixRepresentation();
		this.edgesListRepresentation = new DefaultEdgeListRepresentation();
	}
	
	public void processGraph(
		GraphProperties graphProperties, 
		GraphRepresentations graphRepresentationMode,
		String textRepresentation, 
		String representationStartDelimiter, 
		String representationEndDelimiter,
		String representationRowSeparator, 
		String representationRowElementsSeparator
	) {
		
		updateDelimiters(
			graphRepresentationMode, 
			representationStartDelimiter, 
			representationEndDelimiter, 
			representationRowSeparator, 
			representationRowElementsSeparator
		);
		
		
		
		NumberedNodesFactory nnf = new NumberedNodesFactory();
		DefaultSUWGraph<NumberedNode, DefaultWeightedEdge<NumberedNode>> graph = new DefaultSUWGraph<>();
		
		NumberedNode node1 = nnf.createNode();
		NumberedNode node2 = nnf.createNode();
		NumberedNode node3 = nnf.createNode();
		
		graph.addNode(node1);
		graph.addNode(node2);
		graph.addNode(node3);
		
		DefaultWeightedEdge<NumberedNode> edge12 = new DefaultWeightedEdge<NumberedNode>(node1, node2, BigDecimal.ZERO);
		DefaultWeightedEdge<NumberedNode> edge23 = new DefaultWeightedEdge<NumberedNode>(node2, node3, BigDecimal.ONE);
		
		graph.addEdge(edge12);
		graph.addEdge(edge23);
		
		CytoscapeWeightedParser<NumberedNode, DefaultWeightedEdge<NumberedNode>> parser = new CytoscapeWeightedParser<>();
		String parsedStr = parser.parse(graph, "cy", "cy");
		
		System.out.println("-------");
		System.out.println(parsedStr);
		System.out.println("-------");
		
		PrimefacesUtil.executeJavascript(parsedStr);
	}
	
	private void updateDelimiters(
		GraphRepresentations graphRepresentation, 
		String representationStartDelimiter, 
		String representationEndDelimiter,
		String representationRowSeparator, 
		String representationRowElementsSeparator
	){
		RepresentationParser rep = determineRepresentation(graphRepresentation);
		rep.updateDelimiters(representationStartDelimiter, representationEndDelimiter, representationRowSeparator, representationRowElementsSeparator);
	}
	
	private RepresentationParser determineRepresentation(GraphRepresentations graphRepresentation){
		NullChecker.checkNullParameter(graphRepresentation);
		
		switch (graphRepresentation) {
		case ADJACENCY_MATRIX_LIST:
			return adjMatrixRepresentation;
		case EDGES_LIST:
			return edgesListRepresentation;
		default:
			GraphRepresentationChecker.handleUnkwonGraphRepresentation();
			return null;
		}
	}

	//XXX UPLOAD FILE METHODS
	
	public String readFileToString(UploadedFile uploadedFile, Charset charset) {
		byte[] fileContents = uploadedFile.getContents();
		return StringUtil.toString(fileContents, charset);
	}
}
