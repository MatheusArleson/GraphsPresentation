package br.com.xavier.graphs.view;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("view")
@Controller
public class PageController {
	
	private String text;
	
	//XXX CONSTRUCTOR
	public PageController() {
	}
	
	@PostConstruct
	private void initialize(){
		
	}
	
	//XXX METHODS
	
	public void go(){
		
	}
	
	//XXX SETTERS/GETTERS
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}