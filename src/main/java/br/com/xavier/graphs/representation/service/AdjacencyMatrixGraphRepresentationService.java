package br.com.xavier.graphs.representation.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.springframework.stereotype.Service;

import br.com.xavier.graphs.abstractions.AbstractGraph;
import br.com.xavier.graphs.impl.edges.DefaultUnweightedEdge;
import br.com.xavier.graphs.impl.edges.DefaultWeightedEdge;
import br.com.xavier.graphs.impl.nodes.NumberedNode;
import br.com.xavier.graphs.impl.nodes.NumberedNodesFactory;
import br.com.xavier.graphs.impl.parser.CytoscapeUnweightedParser;
import br.com.xavier.graphs.impl.parser.CytoscapeWeightedParser;
import br.com.xavier.graphs.impl.simple.directed.matrix.MatrixSDUGraph;
import br.com.xavier.graphs.impl.simple.directed.matrix.MatrixSDWGraph;
import br.com.xavier.graphs.impl.simple.undirected.matrix.MatrixSUUGraph;
import br.com.xavier.graphs.impl.simple.undirected.matrix.MatrixSUWGraph;
import br.com.xavier.graphs.representation.model.Delimiters;
import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.util.RandomBigDecimal;
import br.com.xavier.matrix.impl.BitSquareMatrix;
import br.com.xavier.matrix.impl.parser.BitSquareMatrixParser;
import br.com.xavier.matrix.interfaces.Matrix;

@Service
public class AdjacencyMatrixGraphRepresentationService {
	
	//XXX PARSER PROPERTY
	private BitSquareMatrixParser matrixParser;
	
	//XXX CONSTRUCTOR
	public AdjacencyMatrixGraphRepresentationService() {
		this.matrixParser = new BitSquareMatrixParser();
	}

	//XXX GENERATE SCRIPT METHODS
	public String generateAdjacencyMatrixGraphScript(
		GraphProperties graphProperties, 
		String htmlElementContainer,
		String graphWidgetVar,
		String textRepresentation,
		Delimiters delimiters
	) {
		
		updateDelimiters(delimiters);
		
		textRepresentation = matrixParser.getMatrixRepresentationStartDelimiter() + textRepresentation + matrixParser.getMatrixRepresentationEndDelimiter();
		
		BitSquareMatrix matrix = (BitSquareMatrix) matrixParser.fromMatrixString(textRepresentation);
		int numberOfNodes = matrix.getRowCount();
		
		NumberedNodesFactory nnf = new NumberedNodesFactory();
		LinkedHashSet<NumberedNode> nodesSet = nnf.createNodeSet(numberOfNodes);
		
		if(graphProperties.isWeightedGraph()){
			return generateWeightedAdjacencyMatrixGraphScript(graphProperties, htmlElementContainer, graphWidgetVar, nodesSet, matrix);
		} else {
			return generateUnweightedAdjacencyMatrixGraphScript(graphProperties,htmlElementContainer, graphWidgetVar, nodesSet, matrix);
		}
	}

	private String generateWeightedAdjacencyMatrixGraphScript(
		GraphProperties graphProperties, 
		String htmlElementContainer,
		String graphWidgetVar,
		LinkedHashSet<NumberedNode> nodesSet, 
		Matrix<Integer> matrix
	) {
		AbstractGraph<NumberedNode, DefaultWeightedEdge<NumberedNode>> graph = null;
		
		if(graphProperties.isDirectedGraph()){
			graph = new MatrixSDWGraph<>();			
		} else {
			graph = new MatrixSUWGraph<>();
		}
		
		for (NumberedNode numberedNode : nodesSet) {
			graph.addNode(numberedNode);
		}
		
		ArrayList<NumberedNode> nodesList = new ArrayList<NumberedNode>(nodesSet);
		
		for (int row = 0; row < matrix.getRowCount(); row++) {
			NumberedNode rowObj = nodesList.get(row);
			
			for (int column = 0; column < matrix.getColumnCount(); column++) {
				NumberedNode columnObj = nodesList.get(column);
				
				Integer value = matrix.get(column, row);
				boolean isEmpty = matrix.checkEmpty(value);
				if(!isEmpty){
					DefaultWeightedEdge<NumberedNode> edge = new DefaultWeightedEdge<NumberedNode>(rowObj, columnObj, RandomBigDecimal.generateRandom());
					graph.addEdge(edge);
				}
			}
		}
		
		CytoscapeWeightedParser<NumberedNode, DefaultWeightedEdge<NumberedNode>> parser = new CytoscapeWeightedParser<>();
		String parsedStr = parser.parse(graph, htmlElementContainer, graphWidgetVar);
		return parsedStr;
	}
	
	private String generateUnweightedAdjacencyMatrixGraphScript(
		GraphProperties graphProperties,
		String htmlElementContainer,
		String graphWidgetVar,
		LinkedHashSet<NumberedNode> nodesSet, 
		Matrix<Integer> matrix
	) {
		AbstractGraph<NumberedNode, DefaultUnweightedEdge<NumberedNode>> graph = null;
		
		if(graphProperties.isDirectedGraph()){
			graph = new MatrixSDUGraph<>();			
		} else {
			graph = new MatrixSUUGraph<>();
		}
		
		for (NumberedNode numberedNode : nodesSet) {
			graph.addNode(numberedNode);
		}
		
		ArrayList<NumberedNode> nodesList = new ArrayList<NumberedNode>(nodesSet);
		
		for (int row = 0; row < matrix.getRowCount(); row++) {
			NumberedNode rowObj = nodesList.get(row);
			
			for (int column = 0; column < matrix.getColumnCount(); column++) {
				NumberedNode columnObj = nodesList.get(column);
				
				Integer value = matrix.get(column, row);
				//Integer value = matrix.get(column, row);
				boolean isEmpty = matrix.checkEmpty(value);
				if(!isEmpty){
					DefaultUnweightedEdge<NumberedNode> edge = new DefaultUnweightedEdge<NumberedNode>(rowObj, columnObj);
					graph.addEdge(edge);
				}
			}
		}
		
		CytoscapeUnweightedParser<NumberedNode, DefaultUnweightedEdge<NumberedNode>> parser = new CytoscapeUnweightedParser<>();
		String parsedStr = parser.parse(graph, htmlElementContainer, graphWidgetVar);
		return parsedStr;
	}
	
	//XXX DELIMITERS METHODS
	private void updateDelimiters(Delimiters delimiters){
		this.matrixParser.setMatrixRepresentationMatrixStartDelimiter(delimiters.getStartDelimiter());
		this.matrixParser.setMatrixRepresentationMatrixEndDelimiter(delimiters.getEndDelimiter());
		this.matrixParser.setMatrixRepresentationMatrixRowSeparator(delimiters.getRowDelimiter());
		this.matrixParser.setMatrixRepresentationMatrixRowElementsSeparator(delimiters.getRowElementsDelimiter());
	}
}
