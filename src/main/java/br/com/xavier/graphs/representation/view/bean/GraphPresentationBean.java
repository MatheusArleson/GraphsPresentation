package br.com.xavier.graphs.representation.view.bean;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.model.enums.GraphRepresentations;
import br.com.xavier.graphs.representation.service.GraphPresentationService;
import br.com.xavier.graphs.representation.util.StringUtil;
import br.com.xavier.jsf.JsfUtil;
import br.com.xavier.jsf.PrimefacesUtil;

@Scope("view")
@Controller
public class GraphPresentationBean {
	
	private static final String WV_FILE_UPLOAD_DIALOG = "uploadFileDialog";
	
	@Autowired
	private GraphPresentationService graphPresentationService;
	
	//XXX TEXT REPRESENTATION PROPERTIES
	private GraphRepresentations graphRepresentationMode;
	
	//XXX GRAPH PROPERTIES
	private GraphProperties graphProperties;
	
	//XXX DELIMITERS PROPERTIES
	private String representationStartDelimiter;
	private String representationEndDelimiter;
	private String representationRowSeparator;
	private String representationRowElementsSeparator;
	
	//XXX REPRESENTATION PROPERTIES
	private String textRepresentation;
	private UploadedFile uploadedFile;
	
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
	}
	
	//XXX REPRESENTATION MODES METHODs
	
	public GraphRepresentations[] getGraphRepresentationModes(){
		return GraphRepresentations.values();
	}
	
	//XXX PRESENTATION METHODS
	public void processChangeGraphRepresentationMode(){
		this.textRepresentation = new String();
		this.uploadedFile = null;
	}
	
	//XXX UPLOAD FILE PROPERTIES
	public void clearFileUpload(){
		this.uploadedFile = null;
	}
	
	public void fileUploadListener(FileUploadEvent event){
		this.uploadedFile = event.getFile();
		String fileText = graphPresentationService.readFileToString(uploadedFile, Charset.defaultCharset());
		
		if(StringUtil.isNullOrEmpty(fileText)){
			JsfUtil.addErrorMessage("Empty file content.");
			PrimefacesUtil.hideByWidgetVar(WV_FILE_UPLOAD_DIALOG);
			return;
		}
		
		this.textRepresentation = fileText;
		PrimefacesUtil.hideByWidgetVar(WV_FILE_UPLOAD_DIALOG);
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
	public String getRepresentationStartDelimiter() {
		return representationStartDelimiter;
	}
	
	public void setRepresentationStartDelimiter(String representationStartDelimiter) {
		this.representationStartDelimiter = representationStartDelimiter;
	}
	
	public String getRepresentationEndDelimiter() {
		return representationEndDelimiter;
	}
	
	public void setRepresentationEndDelimiter(String representationEndDelimiter) {
		this.representationEndDelimiter = representationEndDelimiter;
	}
	
	public String getRepresentationRowSeparator() {
		return representationRowSeparator;
	}
	
	public void setRepresentationRowSeparator(String representationRowSeparator) {
		this.representationRowSeparator = representationRowSeparator;
	}
	
	public String getRepresentationRowElementsSeparator() {
		return representationRowElementsSeparator;
	}
	
	public void setRepresentationRowElementsSeparator(String representationRowElementsSeparator) {
		this.representationRowElementsSeparator = representationRowElementsSeparator;
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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
}