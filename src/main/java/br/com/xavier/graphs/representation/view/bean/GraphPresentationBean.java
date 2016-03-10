package br.com.xavier.graphs.representation.view.bean;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.xavier.graphs.representation.model.Delimiters;
import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.model.enums.GraphAlgorithms;
import br.com.xavier.graphs.representation.model.enums.GraphRepresentations;
import br.com.xavier.graphs.representation.service.GraphPresentationService;
import br.com.xavier.graphs.representation.util.StringUtil;
import br.com.xavier.jsf.JsfUtil;
import br.com.xavier.jsf.PrimefacesUtil;

@Scope("view")
@Controller
public class GraphPresentationBean {
	
	private static final String WV_GRAPH_FILE_UPLOAD_DIALOG = "graphFileUploadDialog";
	private static final String WV_WEIGHTS_FILE_UPLOAD_DIALOG = "weightsFileUploadDialog";
	
	@Autowired
	private GraphPresentationService graphPresentationService;
	
	//XXX TEXT REPRESENTATION PROPERTIES
	private GraphRepresentations graphRepresentationMode;
	
	//XXX GRAPH PROPERTIES
	private GraphProperties graphProperties;
	
	//XXX DELIMITERS PROPERTIES
	private Delimiters delimiters;
	
	//XXX REPRESENTATION PROPERTIES
	private String textRepresentation;
	private String weightsRepresentation;
	private UploadedFile graphUploadedFile;
	private UploadedFile weightsUploadedFile;
	
	//XXX ALGORITMS PROPERTIES
	private Integer algorithmNodeNumber;
	private GraphAlgorithms graphAlgorithm;
	
	//XXX CONSTRUCTOR
	public GraphPresentationBean() {}
	
	@PostConstruct
	private void initialize(){
		setupDefaultGraphProperties();
	}

	private void setupDefaultGraphProperties() {
		boolean directedGraph = false;
		boolean weightedGraph = false;
		boolean loopsAllowed = false;
		boolean multipleEdgesAllowed = false;
		
		this.graphRepresentationMode = GraphRepresentations.ADJACENCY_MATRIX_LIST;
		this.graphProperties = new GraphProperties(directedGraph, weightedGraph, loopsAllowed, multipleEdgesAllowed);
		
		clearDelimiters();
		
		this.graphAlgorithm = GraphAlgorithms.BFS;
		clearAlgorithmNodeNumber();
	}
	
	//XXX PAGE METHODS
	public void processChangeGraphRepresentationMode(){
		clearTextRepresentation();
		clearFileUploads();
		
		JsfUtil.addSucessMessage("Graph representation mode switched.");
		
		switch (graphRepresentationMode) {
		case EDGES_LIST:
			processToEdgeListModeChange();
			return;

		default:
			return;
		}
	}

	public void clearGraph(){
		setupDefaultGraphProperties();
		clearDelimiters();
		clearTextRepresentation();
		clearWeightRepresentation();
		clearFileUploads();
		clearAlgorithmNodeNumber();
	}
	
	public void processGraph(){
		boolean isDelsValid = validateDelimiters(delimiters);
		if(!isDelsValid){
			return;
		}
		
		this.textRepresentation = textRepresentation.replace("\r", "");
		
		graphPresentationService.processGraphScript(
			graphProperties, 
			graphRepresentationMode,
			"cy",
			"cy",
			textRepresentation,
			weightsRepresentation,
			delimiters
		);
	}
	
	//XXX DELIMITERS METHODS
	private void clearDelimiters(){
		this.delimiters = new Delimiters("\n", ",", "0");
	}
	
	private boolean validateDelimiters(Delimiters delimiters){
		if(delimiters == null){
			JsfUtil.addErrorMessage("Delimiters are required. Please inform them.");
			return false;
		}
		
		String rowDelimiter = delimiters.getRowDelimiter();
		if(StringUtil.isNullOrEmpty(rowDelimiter)){
			rowDelimiter = "\n";
			delimiters.setRowDelimiter(rowDelimiter);
		}
		
		String rowElementsDelimiter = delimiters.getRowElementsDelimiter();
		
		boolean anyEmpty = StringUtil.anyNullOrEmpty(rowDelimiter, rowElementsDelimiter);
		if(anyEmpty){
			JsfUtil.addErrorMessage("Delimiters are required. Please inform them.");
			return false;
		}
		
		return true;
	}
	
	//XXX REPRESENTATION METHODS
	private void processToEdgeListModeChange(){
		boolean isWeighted = graphProperties.isWeightedGraph();
		if(isWeighted){
			clearWeightRepresentation();
		}
	}
	
	private void clearTextRepresentation(){
		this.textRepresentation = "";
	}
	
	private void clearWeightRepresentation(){
		this.weightsRepresentation = "";
	}
	
	//XXX ALGORITHMS METHODS
	public void clearAlgorithmNodeNumber(){
		this.algorithmNodeNumber = null;
	}
	
	public void processChangeGraphAlgorithm(){
		System.out.println("");
	}
	
	public void processGraphAlgorithm(){
		graphPresentationService.doGraphAlgorithm(graphAlgorithm, algorithmNodeNumber, graphProperties.isDirectedGraph());
	}
	
	//XXX UPLOAD FILE METHODS
	public void clearFileUploads(){
		clearGraphUploadedFile();
		clearWeightsUploadedFile();
	}

	public void clearGraphUploadedFile() {
		this.graphUploadedFile = null;
	}
	
	public void graphFileUploadListener(FileUploadEvent event){
		this.graphUploadedFile = event.getFile();
		String fileText = graphPresentationService.readFileToString(graphUploadedFile, Charset.defaultCharset());
		
		if(StringUtil.isNullOrEmpty(fileText)){
			JsfUtil.addErrorMessage("Empty file content.");
			PrimefacesUtil.hideByWidgetVar(WV_GRAPH_FILE_UPLOAD_DIALOG);
			return;
		}
		
		this.textRepresentation = fileText;
		JsfUtil.addSucessMessage("File content loaded.");
		PrimefacesUtil.hideByWidgetVar(WV_GRAPH_FILE_UPLOAD_DIALOG);
	}
	
	public void clearWeightsUploadedFile() {
		this.weightsUploadedFile = null;
	}
	
	public void weightsFileUploadListener(FileUploadEvent event){
		this.weightsUploadedFile = event.getFile();
		String fileText = graphPresentationService.readFileToString(weightsUploadedFile, Charset.defaultCharset());
		
		if(StringUtil.isNullOrEmpty(fileText)){
			JsfUtil.addErrorMessage("Empty file content.");
			PrimefacesUtil.hideByWidgetVar(WV_WEIGHTS_FILE_UPLOAD_DIALOG);
			return;
		}
		
		this.weightsRepresentation = fileText;
		JsfUtil.addSucessMessage("File content loaded.");
		PrimefacesUtil.hideByWidgetVar(WV_WEIGHTS_FILE_UPLOAD_DIALOG);
	}
	
	//XXX RENDERING METHODS
	public GraphRepresentations[] getGraphRepresentationModes(){
		return GraphRepresentations.values();
	}
	
	public boolean isRenderWeightRepresentationInput(){
		boolean isWeighted = graphProperties.isWeightedGraph();
		boolean isEdgeListMode = graphRepresentationMode.equals(GraphRepresentations.EDGES_LIST);
		
		return isEdgeListMode && isWeighted;
	} 
	
	public GraphAlgorithms[] getGraphAlgorithms(){
		return GraphAlgorithms.values();
	}
	
	//XXX GETTERS/SETTERS
	
	//GRAPH PROPERTIES
	public GraphProperties getGraphProperties() {
		return graphProperties;
	}
	
	public void setGraphProperties(GraphProperties graphProperties) {
		this.graphProperties = graphProperties;
	}
	
	//DELIMITERS PROPERTIES
	public Delimiters getDelimiters() {
		return delimiters;
	}
	
	public void setDelimiters(Delimiters delimiters) {
		this.delimiters = delimiters;
	}
	
	//REPRESENTATIONS
	public GraphRepresentations getGraphRepresentationMode() {
		return graphRepresentationMode;
	}
	
	public void setGraphRepresentationMode(GraphRepresentations graphRepresentationMode) {
		this.graphRepresentationMode = graphRepresentationMode;
	}
	
	//REPRESENTATION PROPERTIES
	public String getTextRepresentation() {
		return textRepresentation;
	}
	
	public void setTextRepresentation(String textRepresentation) {
		this.textRepresentation = textRepresentation;
	}

	public String getWeightsRepresentation() {
		return weightsRepresentation;
	}
	
	public void setWeightsRepresentation(String weightsRepresentation) {
		this.weightsRepresentation = weightsRepresentation;
	}
	
	public UploadedFile getGraphUploadedFile() {
		return graphUploadedFile;
	}
	
	public void setGraphUploadedFile(UploadedFile graphUploadedFile) {
		this.graphUploadedFile = graphUploadedFile;
	}
	
	public UploadedFile getWeightsUploadedFile() {
		return weightsUploadedFile;
	}
	
	public void setWeightsUploadedFile(UploadedFile weightsUploadedFile) {
		this.weightsUploadedFile = weightsUploadedFile;
	}

	//XXX ALGORITMS PROPERTIES
	public Integer getAlgorithmNodeNumber() {
		return algorithmNodeNumber;
	}
	
	public void setAlgorithmNodeNumber(Integer algorithmNodeNumber) {
		this.algorithmNodeNumber = algorithmNodeNumber;
	}
	
	public GraphAlgorithms getGraphAlgorithm() {
		return graphAlgorithm;
	}
	
	public void setGraphAlgorithm(GraphAlgorithms graphAlgorithm) {
		this.graphAlgorithm = graphAlgorithm;
	}
}