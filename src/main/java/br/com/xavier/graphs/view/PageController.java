package br.com.xavier.graphs.view;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.xavier.matrix.impl.parser.DefaultMatrixParserValues;
import br.com.xavier.matrix.impl.parser.DefaultSquareMatrixParser;

@Scope("view")
@Controller
public class PageController {
	
	private static final String MATRIX_REP_START_DELIMITER= "{"; 
	private static final String MATRIX_REP_END_DELIMITER = "}";
	
	//XXX GRAPH PROPERTIES 
	private boolean directedGraph;
	private boolean weightedGraph;
	private boolean loopsAllowed;
	private boolean multipleEdgesAllowed;
	
	//XXX TEXT REPRESENTATION PROPERTIES
	private GraphRepresentation graphRepresentationMode;
	private String representationText;
	
	//XXX ADJACENCY LIST REPRESENTATION DELIMITERS PROPERTIES
	private String adjListStartDelimiter;
	
	//XXX MATRIX TEXT REPRESENTATION DELIMITERS PROPERTIES
	private String matrixStartDelimiter;
	private String matrixEndDelimiter;
	private String matrixRowSeparator;
	private String matrixRowElementsSeparator;
	
	//XXX INTERNAL PROPERTIES
	
	private DefaultSquareMatrixParser<Integer> matrixParser;
	private DefaultMatrixParserValues defaultMatrixParserValues;
	
	//XXX PRESENTATION PROPERTIES
	private boolean renderEdgeListDelimiters;
	private boolean renderAdjacencyMatrixDelimiters;
	
	//XXX CONSTRUCTOR
	public PageController() {
	}
	
	@PostConstruct
	private void initialize(){
		setupDefaultGraphProperties();
		setupDefaultGraphDelimiters();
		getMatrixParserInstance();
	}

	private void setupDefaultGraphProperties() {
		this.directedGraph = false;
		this.weightedGraph = false;
		this.loopsAllowed = false;
		this.multipleEdgesAllowed = false;
		
		this.graphRepresentationMode = GraphRepresentation.ADJACENCY_MATRIX_LIST;
		processRenderizationDelimiters();
	}
	
	private void setupDefaultGraphDelimiters() {
		this.defaultMatrixParserValues = new DefaultMatrixParserValues();
		this.matrixStartDelimiter = defaultMatrixParserValues.getMatrixRepresentatitionMatrixStartDelimiter();
		this.matrixEndDelimiter = defaultMatrixParserValues.getMatrixRepresentatitionMatrixEndDelimiter();
		this.matrixRowSeparator = defaultMatrixParserValues.getMatrixRepresentationMatrixRowSeparator();
		this.matrixRowElementsSeparator = defaultMatrixParserValues.getMatrixRepresentationMatrixRowElementsSeparator();
	}
	
	private void getMatrixParserInstance(){
		this.matrixParser = new DefaultSquareMatrixParser<>(
			MATRIX_REP_START_DELIMITER, 
			MATRIX_REP_END_DELIMITER, 
			matrixStartDelimiter, 
			matrixEndDelimiter,
			matrixRowSeparator, 
			matrixRowElementsSeparator
		);
	}
	
	//XXX REPRESENTATION MODES METHODs
	
	public GraphRepresentation[] getGraphRepresentationModes(){
		return GraphRepresentation.values();
	}
	
	//XXX PRESENTATION METHODS
	public void processRenderizationDelimiters(){
		switch (graphRepresentationMode) {
		case EDGES_LIST:
			renderEdgeListDelimiters = true;
			renderAdjacencyMatrixDelimiters = false;
			return;
			
		case ADJACENCY_MATRIX_LIST:
			renderEdgeListDelimiters = false;
			renderAdjacencyMatrixDelimiters = true;
			return;
			
		default:
			renderEdgeListDelimiters = false;
			renderAdjacencyMatrixDelimiters = false;
			return;
		}
	}
	
	public void processChangeGraphRepresentationMode(){
		this.representationText = new String();
		
		processRenderizationDelimiters();
	}
	
	//XXX SETTERS/GETTERS
	
	//GRAPH PROPERTIES 
	
	public boolean isDirectedGraph() {
		return directedGraph;
	}
	
	public void setDirectedGraph(boolean directedGraph) {
		this.directedGraph = directedGraph;
	}
	
	public boolean isWeightedGraph() {
		return weightedGraph;
	}
	
	public void setWeightedGraph(boolean weightedGraph) {
		this.weightedGraph = weightedGraph;
	}
	
	public boolean isLoopsAllowed() {
		return loopsAllowed;
	}
	
	public void setLoopsAllowed(boolean loopsAllowed) {
		this.loopsAllowed = loopsAllowed;
	}
	
	public boolean isMultipleEdgesAllowed() {
		return multipleEdgesAllowed;
	}
	
	public void setMultipleEdgesAllowed(boolean multipleEdgesAllowed) {
		this.multipleEdgesAllowed = multipleEdgesAllowed;
	}
	
	//TEXT REPRESENTATION PROPERTIES
	
	public GraphRepresentation getGraphRepresentationMode() {
		return graphRepresentationMode;
	}
	
	public void setGraphRepresentationMode(GraphRepresentation graphRepresentationMode) {
		this.graphRepresentationMode = graphRepresentationMode;
	}
	
	public String getRepresentationText() {
		return representationText;
	}
	
	public void setRepresentationText(String representationText) {
		this.representationText = representationText;
	}
	
	//ADJACENCY LIST REPRESENTATION DELIMITERS PROPERTIES
	public String getAdjListStartDelimiter() {
		return adjListStartDelimiter;
	}
	
	public void setAdjListStartDelimiter(String adjListStartDelimiter) {
		this.adjListStartDelimiter = adjListStartDelimiter;
	}
	
	//MATRIX TEXT REPRESENTATION DELIMITERS PROPERTIES
	public String getMatrixStartDelimiter() {
		return matrixStartDelimiter;
	}
	
	public void setMatrixStartDelimiter(String matrixStartDelimiter) {
		this.matrixStartDelimiter = matrixStartDelimiter;
	}
	
	public String getMatrixEndDelimiter() {
		return matrixEndDelimiter;
	}
	
	public void setMatrixEndDelimiter(String matrixEndDelimiter) {
		this.matrixEndDelimiter = matrixEndDelimiter;
	}
	
	public String getMatrixRowSeparator() {
		return matrixRowSeparator;
	}
	
	public void setMatrixRowSeparator(String matrixRowSeparator) {
		this.matrixRowSeparator = matrixRowSeparator;
	}
	
	public String getMatrixRowElementsSeparator() {
		return matrixRowElementsSeparator;
	}
	
	public void setMatrixRowElementsSeparator(String matrixRowElementsSeparator) {
		this.matrixRowElementsSeparator = matrixRowElementsSeparator;
	}

	//PRESENTATION PROPERTIES
	public boolean isRenderAdjacencyMatrixDelimiters() {
		return renderAdjacencyMatrixDelimiters;
	}
	
	public boolean isRenderEdgeListDelimiters() {
		return renderEdgeListDelimiters;
	}
}