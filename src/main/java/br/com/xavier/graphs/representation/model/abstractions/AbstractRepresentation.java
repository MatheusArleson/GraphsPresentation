package br.com.xavier.graphs.representation.model.abstractions;

import java.io.Serializable;

import br.com.xavier.graphs.representation.model.interfaces.RepresentationParser;

public abstract class AbstractRepresentation implements RepresentationParser, Serializable{
	
	private static final long serialVersionUID = 7950621751404803729L;
	
	//XXX DELIMITERS PROPERTIES
	private String representationStartDelimiter;
	private String representationEndDelimiter;
	private String representationRowSeparator;
	private String representationRowElementsSeparator;
	
	//XXX CONSTRUCTOR
	public AbstractRepresentation(
		String representationStartDelimiter, 
		String representationEndDelimiter,
		String representationRowSeparator, 
		String representationRowElementsSeparator
	) {
		super();
		this.representationStartDelimiter = representationStartDelimiter;
		this.representationEndDelimiter = representationEndDelimiter;
		this.representationRowSeparator = representationRowSeparator;
		this.representationRowElementsSeparator = representationRowElementsSeparator;
	}

	//XXX OVERRIDE METHODS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((representationStartDelimiter == null) ? 0 : representationStartDelimiter.hashCode());
		result = prime * result + ((representationEndDelimiter == null) ? 0 : representationEndDelimiter.hashCode());
		result = prime * result + ((representationRowSeparator == null) ? 0 : representationRowSeparator.hashCode());
		result = prime * result + ((representationRowElementsSeparator == null) ? 0 : representationRowElementsSeparator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractRepresentation other = (AbstractRepresentation) obj;
		if (representationStartDelimiter == null) {
			if (other.representationStartDelimiter != null) {
				return false;
			}
		} else if (!representationStartDelimiter.equals(other.representationStartDelimiter)) {
			return false;
		}
		
		if (representationEndDelimiter == null) {
			if (other.representationEndDelimiter != null) {
				return false;
			}
		} else if (!representationEndDelimiter.equals(other.representationEndDelimiter)) {
			return false;
		}
		
		if (representationRowSeparator == null) {
			if (other.representationRowSeparator != null) {
				return false;
			}
		} else if (!representationRowSeparator.equals(other.representationRowSeparator)) {
			return false;
		}
		
		if (representationRowElementsSeparator == null) {
			if (other.representationRowElementsSeparator != null) {
				return false;
			}
		} else if (!representationRowElementsSeparator.equals(other.representationRowElementsSeparator)) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "AbstractRepresentation [" 
			+ "representationStartDelimiter=" + representationStartDelimiter
			+ ", representationEndDelimiter=" + representationEndDelimiter 
			+ ", representationRowSeparator=" + representationRowSeparator 
			+ ", representationRowElementsSeparator=" + representationRowElementsSeparator 
		+ "]";
	}

	//XXX GETTERS/SETTERS
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
}
