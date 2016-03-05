package br.com.xavier.graphs.representation.view.bean;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.model.enums.GraphRepresentations;
import br.com.xavier.graphs.representation.model.impl.DefaultAdjacencyMatrixRepresentation;
import br.com.xavier.graphs.representation.model.impl.DefaultEdgeListRepresentation;
import br.com.xavier.jsf.JsfUtil;

@Scope("view")
@Controller
public class GraphPresentationBean {
	
	//XXX TEXT REPRESENTATION PROPERTIES
	private GraphRepresentations graphRepresentationMode;
	
	//XXX GRAPH PROPERTIES
	private GraphProperties graphProperties;
	
	//XXX GRAPH REPRESENTATIONS PROPERTIES
	private DefaultAdjacencyMatrixRepresentation adjMatrixRepresentation;
	private DefaultEdgeListRepresentation edgesListRepresentation;
	
	//XXX REPRESENTATION PROPERTIES
	private String textRepresentation;
	
	//XXX PRESENTATION PROPERTIES
	private boolean renderEdgeListDelimitersPanel;
	private boolean renderAdjacencyMatrixDelimitersPanel;
	
	//XXX CONSTRUCTOR
	public GraphPresentationBean() {}
	
	@PostConstruct
	private void initialize(){
		setupDefaultGraphProperties();
		setupDefaultOnGraphRepresentations();
	}

	private void setupDefaultGraphProperties() {
		boolean directedGraph = false;
		boolean weightedGraph = false;
		boolean loopsAllowed = false;
		boolean multipleEdgesAllowed = false;
		
		this.graphProperties = new GraphProperties(directedGraph, weightedGraph, loopsAllowed, multipleEdgesAllowed);
	}
	
	private void setupDefaultOnGraphRepresentations() {
		this.graphRepresentationMode = GraphRepresentations.ADJACENCY_MATRIX_LIST;
		this.adjMatrixRepresentation = new DefaultAdjacencyMatrixRepresentation();
		this.edgesListRepresentation = new DefaultEdgeListRepresentation();
		
		processRenderizationDelimitersPanel();
	}
	
	//XXX REPRESENTATION MODES METHODs
	
	public GraphRepresentations[] getGraphRepresentationModes(){
		return GraphRepresentations.values();
	}
	
	//XXX PRESENTATION METHODS
	public void processChangeGraphRepresentationMode(){
		processRenderizationDelimitersPanel();
	}
	
	private void processRenderizationDelimitersPanel(){
		switch (graphRepresentationMode) {
		case EDGES_LIST:
			renderEdgeListDelimitersPanel = true;
			renderAdjacencyMatrixDelimitersPanel = false;
			return;
			
		case ADJACENCY_MATRIX_LIST:
			renderEdgeListDelimitersPanel = false;
			renderAdjacencyMatrixDelimitersPanel = true;
			return;
			
		default:
			JsfUtil.addErrorMessage("Unknow representation.");
		}
	}
	
	//XXX GETTERS/SETTERS
	
	//GRAPH PROPERTIES
	public GraphProperties getGraphProperties() {
		return graphProperties;
	}
	
	public void setGraphProperties(GraphProperties graphProperties) {
		this.graphProperties = graphProperties;
	}
	
	//REPRESENTATIONS
	public GraphRepresentations getGraphRepresentationMode() {
		return graphRepresentationMode;
	}
	
	public void setGraphRepresentationMode(GraphRepresentations graphRepresentationMode) {
		this.graphRepresentationMode = graphRepresentationMode;
	}
	
	public DefaultAdjacencyMatrixRepresentation getAdjMatrixRepresentation() {
		return adjMatrixRepresentation;
	}
	
	public void setAdjMatrixRepresentation(DefaultAdjacencyMatrixRepresentation adjMatrixRepresentation) {
		this.adjMatrixRepresentation = adjMatrixRepresentation;
	}
	
	public DefaultEdgeListRepresentation getEdgesListRepresentation() {
		return edgesListRepresentation;
	}
	
	public void setEdgesListRepresentation(DefaultEdgeListRepresentation edgesListRepresentation) {
		this.edgesListRepresentation = edgesListRepresentation;
	}
	
	//REPRESENTATION PROPERTIES
	public String getTextRepresentation() {
		return textRepresentation;
	}
	
	public void setTextRepresentation(String textRepresentation) {
		this.textRepresentation = textRepresentation;
	}

	//PRESENTATION PROPERTIES
	public boolean isRenderAdjacencyMatrixDelimitersPanel() {
		return renderAdjacencyMatrixDelimitersPanel;
	}
	
	public boolean isRenderEdgeListDelimitersPanel() {
		return renderEdgeListDelimitersPanel;
	}
}