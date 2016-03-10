package br.com.xavier.graphs.representation.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.com.xavier.graphs.abstractions.AbstractGraph;
import br.com.xavier.graphs.impl.edges.DefaultUnweightedEdge;
import br.com.xavier.graphs.impl.edges.DefaultWeightedEdge;
import br.com.xavier.graphs.impl.nodes.NumberedNode;
import br.com.xavier.graphs.impl.nodes.NumberedNodesFactory;
import br.com.xavier.graphs.impl.parser.CytoscapeUnweightedParser;
import br.com.xavier.graphs.impl.parser.CytoscapeWeightedParser;
import br.com.xavier.graphs.impl.simple.directed.DefaultSDUGraph;
import br.com.xavier.graphs.impl.simple.directed.DefaultSDWGraph;
import br.com.xavier.graphs.impl.simple.undirected.DefaultSUUGraph;
import br.com.xavier.graphs.impl.simple.undirected.DefaultSUWGraph;
import br.com.xavier.graphs.representation.exceptions.InvalidGraphRepresentation;
import br.com.xavier.graphs.representation.model.Delimiters;
import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.util.StringUtil;

@Service
public class EdgeListGraphRepresentationService {
	
	private static final String ELEMENTS_SEPARATOR_BASE_REGEXP = "^([A-Za-z0-9]+)(#[A-Za-z0-9]+)*$";
	
	public EdgeListGraphRepresentationService() {}

	public String generateEdgesListGraphScript(
		GraphProperties graphProperties, 
		String htmlElementContainer,
		String graphWidgetVar,
		String textRepresentation,
		String weightRepresentation,
		Delimiters delimiters
	){
		String rowDelimiterRegexp = Pattern.compile(delimiters.getRowDelimiter()).toString();
		String rowElementsDelimiterRegexp = Pattern.compile(delimiters.getRowElementsDelimiter()).toString();
		
		validate(textRepresentation, delimiters, rowDelimiterRegexp, rowElementsDelimiterRegexp);
		LinkedHashSet<NumberedNode> nodesSet = generateNodeSet(textRepresentation, delimiters, rowDelimiterRegexp, rowElementsDelimiterRegexp);
		if(nodesSet.isEmpty()){
			return "";
		}
		
		Map<NumberedNode, List<NumberedNode>> graphMap = generateGraphMap(textRepresentation, delimiters, rowDelimiterRegexp, rowElementsDelimiterRegexp, nodesSet);
		
		boolean isWeighted = graphProperties.isWeightedGraph();
		if(!isWeighted){
			return generateUnweightedEdgesListGraphScript(graphProperties, htmlElementContainer, graphWidgetVar, graphMap);
		} else {
			if(StringUtil.isNullOrEmpty(weightRepresentation)){
				throw new InvalidGraphRepresentation("Empty weight representation.");
			}
			
			weightRepresentation = weightRepresentation.replaceAll("\r", "");
			
			validate(weightRepresentation, delimiters, rowDelimiterRegexp, rowElementsDelimiterRegexp);
			String workStr = weightRepresentation.replaceAll(rowDelimiterRegexp, rowElementsDelimiterRegexp);
			String[] weights = workStr.split(rowElementsDelimiterRegexp);
			
			int numberOfWeights = weights.length;
			int numberOfEdges = getNumberOfEdges(graphMap);
			
			if(numberOfEdges != numberOfWeights){
				throw new InvalidGraphRepresentation("Number of Weights must be equal to the number of Edges.");
			}
			
			return generateWeightedEdgesListGraphScript(graphProperties, htmlElementContainer, graphWidgetVar, graphMap, weights);
		}
	}

	private Map<NumberedNode, List<NumberedNode>> generateGraphMap(
		String textRepresentation, Delimiters delimiters, 
		String rowDelimiterRegexp, String rowElementsDelimiterRegexp,
		LinkedHashSet<NumberedNode> nodesSet
	) {
		Map<NumberedNode, List<NumberedNode>> graphMap = new LinkedHashMap<>();
		
		for (NumberedNode node : nodesSet) {
			graphMap.put(node, new ArrayList<NumberedNode>());
		}
		
		ArrayList<NumberedNode> nodesList = new ArrayList<>(nodesSet);
		String[] rows = textRepresentation.split(rowDelimiterRegexp);
		
		for (int i = 0; i < rows.length; i++) {
			String workStr = new String(rows[i]);
			boolean endsWithDelimiter = workStr.endsWith(delimiters.getRowElementsDelimiter());
			if(endsWithDelimiter){
				workStr = workStr.substring(0, workStr.length() - 1);
			}
			
			NumberedNode node = nodesList.get(i);
			
			String[] nodesNumber = workStr.split(rowElementsDelimiterRegexp);
			for (String nodeNumber : nodesNumber) {
				Integer nodeNumberFromStr = Integer.valueOf(nodeNumber);
				Integer nodeNumberIndex = nodeNumberFromStr - 1;
				NumberedNode relatedNode = nodesList.get(nodeNumberIndex);
				graphMap.get(node).add(relatedNode);
			}
		}
		
		return graphMap;
	}

	private void validate(String textRepresentation, Delimiters delimiters, String rowDelimiterRegexp, String rowElementsDelimiterRegexp) {
		String workStr = null;
		//contains more than one line?
		boolean containsRowDelimiter = textRepresentation.contains(delimiters.getRowDelimiter());
		if(containsRowDelimiter){ 
			workStr = textRepresentation.replaceAll(rowDelimiterRegexp, rowElementsDelimiterRegexp);
		} else {
			workStr = new String(textRepresentation);
		}
		
		//check if it pass in the elements separator test
		String regexp = ELEMENTS_SEPARATOR_BASE_REGEXP.replace("#", delimiters.getRowElementsDelimiter());
		Pattern elementsSeparatorPattern = Pattern.compile(regexp);
		
		Matcher matcher = elementsSeparatorPattern.matcher(workStr);
		boolean isValid = matcher.matches();
		if(!isValid){
			throw new InvalidGraphRepresentation();
		}
	}
	
	private LinkedHashSet<NumberedNode> generateNodeSet(
		String textRepresentation, Delimiters delimiters, 
		String rowDelimiterRegexp, String rowElementsDelimiterRegexp
	) {
		int maxNodeNumber = determineMaximumNodeNumber(textRepresentation, delimiters, rowDelimiterRegexp, rowElementsDelimiterRegexp);
		NumberedNodesFactory nnf = new NumberedNodesFactory();
		LinkedHashSet<NumberedNode> nodesSet = nnf.createNodeSet(maxNodeNumber);
		return nodesSet;
	}
	
	private int determineMaximumNodeNumber(String textRepresentation, Delimiters delimiters, String rowDelimiterRegexp, String rowElementsDelimiterRegexp) {
		String workStr = textRepresentation.replaceAll(rowDelimiterRegexp, rowElementsDelimiterRegexp);
		int max = StringUtil.getMaximumNumber(workStr, rowElementsDelimiterRegexp);
		return max;
	}
	
	private int getNumberOfEdges(Map<NumberedNode, List<NumberedNode>> graphMap){
		int count = 0;
		
		if(graphMap.isEmpty()){
			return count;
		}
		
		for (NumberedNode nodeKey : graphMap.keySet()) {
			count += graphMap.get(nodeKey).size();
		}
		
		return count;
	}
	
	//XXX GENERATE SCRIPT METHODS
	
	private String generateWeightedEdgesListGraphScript(
		GraphProperties graphProperties,
		String htmlElementContainer,
		String graphWidgetVar,
		Map<NumberedNode, List<NumberedNode>> graphMap,
		String[] weights
	) {
		AbstractGraph<NumberedNode, DefaultWeightedEdge<NumberedNode, String>> graph = null;
		
		if(graphProperties.isDirectedGraph()){
			graph = new DefaultSDWGraph<>();			
		} else {
			graph = new DefaultSUWGraph<>();
		}
		
		Set<NumberedNode> graphMapNodesSet = graphMap.keySet();
		for (NumberedNode node : graphMapNodesSet) {
			graph.addNode(node);
		}
		
		int weightCount = 0;
		
		for (NumberedNode node : graphMapNodesSet) {
			List<NumberedNode> relatedNodesList = graphMap.get(node);
			if(relatedNodesList.isEmpty()){
				continue;
			}
			
			for (NumberedNode relatedNode : relatedNodesList) {
				String weight = weights[weightCount];
				DefaultWeightedEdge<NumberedNode, String> edge = new DefaultWeightedEdge<NumberedNode, String>(node, relatedNode, weight);
				
				graph.addEdge(edge);
				weightCount++;
			}
		}
		
		CytoscapeWeightedParser<NumberedNode, DefaultWeightedEdge<NumberedNode, String>, String> parser = new CytoscapeWeightedParser<>();
		String parsedStr = parser.parse(graph, htmlElementContainer, graphWidgetVar);
		return parsedStr;
	}
	
	private String generateUnweightedEdgesListGraphScript(
		GraphProperties graphProperties,
		String htmlElementContainer,
		String graphWidgetVar,
		Map<NumberedNode, List<NumberedNode>> graphMap
	) {
		AbstractGraph<NumberedNode, DefaultUnweightedEdge<NumberedNode>> graph = null;
		
		if(graphProperties.isDirectedGraph()){
			graph = new DefaultSDUGraph<>();			
		} else {
			graph = new DefaultSUUGraph<>();
		}
		
		Set<NumberedNode> graphMapNodesSet = graphMap.keySet();
		for (NumberedNode node : graphMapNodesSet) {
			graph.addNode(node);
		}
		
		for (NumberedNode node : graphMapNodesSet) {
			List<NumberedNode> relatedNodesList = graphMap.get(node);
			if(relatedNodesList.isEmpty()){
				continue;
			}
			
			for (NumberedNode relatedNode : relatedNodesList) {
				DefaultUnweightedEdge<NumberedNode> edge = new DefaultUnweightedEdge<NumberedNode>(node, relatedNode);
				graph.addEdge(edge);
			}
		}
		
		CytoscapeUnweightedParser<NumberedNode, DefaultUnweightedEdge<NumberedNode>> parser = new CytoscapeUnweightedParser<>();
		String parsedStr = parser.parse(graph, htmlElementContainer, graphWidgetVar);
		return parsedStr;
	}
	

}
