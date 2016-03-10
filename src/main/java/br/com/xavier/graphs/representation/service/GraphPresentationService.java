package br.com.xavier.graphs.representation.service;

import java.nio.charset.Charset;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xavier.graphs.representation.exceptions.InvalidGraphRepresentation;
import br.com.xavier.graphs.representation.model.Delimiters;
import br.com.xavier.graphs.representation.model.GraphProperties;
import br.com.xavier.graphs.representation.model.enums.GraphRepresentations;
import br.com.xavier.graphs.representation.util.StringUtil;
import br.com.xavier.graphs.representation.util.checkers.GraphRepresentationChecker;
import br.com.xavier.graphs.representation.util.checkers.NullChecker;
import br.com.xavier.jsf.JsfUtil;
import br.com.xavier.jsf.PrimefacesUtil;
import br.com.xavier.matrix.exception.InvalidMatrixRepresentation;
import br.com.xavier.matrix.exception.InvalidMatrixRepresentationDelimiter;

@Service
public class GraphPresentationService {
	
	@Autowired
	private AdjacencyMatrixGraphRepresentationService adjacencyMatrixGraphRepresentationService;
	
	@Autowired
	private EdgeListGraphRepresentationService edgeListGraphRepresentationService;
	
	public GraphPresentationService() {	}
	
	//XXX UPLOAD FILE METHODS
	public String readFileToString(UploadedFile uploadedFile, Charset charset) {
		byte[] fileContents = uploadedFile.getContents();
		return StringUtil.toString(fileContents, charset);
	}
	
	//XXX PROCESS GRAPH SCRIPT METHODS
	public void processGraphScript(
		GraphProperties graphProperties, 
		GraphRepresentations graphRepresentationMode,
		String htmlElementContainer,
		String graphWidgetVar,
		String textRepresentation,
		String weightsRepresentation, 
		Delimiters delimiters
	) {
		try {
			
			validate(
				graphRepresentationMode, 
				graphProperties, 
				htmlElementContainer, 
				graphWidgetVar, 
				textRepresentation,
				weightsRepresentation,
				delimiters
			);
			
			String graphScript = generateGraphScript(
				graphRepresentationMode, 
				graphProperties, 
				htmlElementContainer, 
				graphWidgetVar, 
				textRepresentation,
				weightsRepresentation,
				delimiters
			);
			
			System.out.println("-------");
			System.out.println(graphScript);
			System.out.println("-------");
			
			PrimefacesUtil.executeJavascript(graphScript);
			JsfUtil.addSucessMessage("Graph parsed.");
			
		} catch(InvalidMatrixRepresentation | InvalidMatrixRepresentationDelimiter e){
			JsfUtil.addErrorMessage("Invalid Matrix representation. Check the text representation.");
			e.printStackTrace();
			return;
		} catch(Exception e){
			JsfUtil.addErrorMessage("Error generating Graph.");
			e.printStackTrace();
			return;
		}
	}

	private void validate(
		GraphRepresentations graphRepresentationMode, 
		GraphProperties graphProperties,
		String htmlElementContainer, 
		String graphWidgetVar, 
		String textRepresentation, 
		String weightsRepresentation,
		Delimiters delimiters
	) throws Exception {
		
		NullChecker.checkNullParameter(
			graphRepresentationMode, graphProperties, 
			htmlElementContainer, graphWidgetVar, 
			textRepresentation, delimiters
		);
		
		if(StringUtil.isNullOrEmpty(htmlElementContainer)){
			throw new InvalidGraphRepresentation("Invalid htmlElementContainer.");
		}
		
		if(StringUtil.isNullOrEmpty(graphWidgetVar)){
			throw new InvalidGraphRepresentation("Invalid graphWidgetVar.");
		}
		
		if(StringUtil.isNullOrEmpty(textRepresentation)){
			throw new InvalidGraphRepresentation("Invalid textRepresentation.");
		}
		
		boolean isWeightedGraph = graphProperties.isWeightedGraph();
		boolean isEdgeListRep = graphRepresentationMode.equals(GraphRepresentations.EDGES_LIST);
		if(isWeightedGraph && isEdgeListRep && StringUtil.isNullOrEmpty(weightsRepresentation)){
			throw new InvalidGraphRepresentation("Invalid weightsRepresentation.");
		}
	}

	private String generateGraphScript(
		GraphRepresentations graphRepresentationMode, 
		GraphProperties graphProperties, 
		String htmlElementContainer,
		String graphWidgetVar,
		String textRepresentation,
		String weightRepresentation,
		Delimiters delimiters
	) {
		textRepresentation = textRepresentation.replaceAll(" ", "");
		
		switch (graphRepresentationMode) {
		case ADJACENCY_MATRIX_LIST:
			return adjacencyMatrixGraphRepresentationService.generateAdjacencyMatrixGraphScript(
				graphProperties, 
				htmlElementContainer, 
				graphWidgetVar,
				textRepresentation,
				weightRepresentation,
				delimiters
			);
			
		case EDGES_LIST:
			return edgeListGraphRepresentationService.generateEdgesListGraphScript(
				graphProperties, 
				htmlElementContainer, 
				graphWidgetVar,
				textRepresentation,
				weightRepresentation,
				delimiters
			);
			
		default:
			GraphRepresentationChecker.handleUnkwonGraphRepresentation();
			return null;
		}
	}
}
