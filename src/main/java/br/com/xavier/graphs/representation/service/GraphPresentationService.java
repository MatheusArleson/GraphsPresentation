package br.com.xavier.graphs.representation.service;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Service;

import br.com.xavier.graphs.representation.model.enums.GraphRepresentations;
import br.com.xavier.graphs.representation.model.impl.DefaultAdjacencyMatrixRepresentation;
import br.com.xavier.graphs.representation.model.impl.DefaultEdgeListRepresentation;
import br.com.xavier.graphs.representation.model.interfaces.RepresentationParser;
import br.com.xavier.graphs.representation.util.StringUtil;
import br.com.xavier.graphs.representation.util.checkers.GraphRepresentationChecker;
import br.com.xavier.graphs.representation.util.checkers.NullChecker;

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
	
	public void updateDelimiters(
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
