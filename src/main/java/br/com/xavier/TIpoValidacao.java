package br.com.xavier;

import br.com.xavier.jsf.ViewScope;

public enum TIpoValidacao {

	MATRIZ{

		@Override
		public ViewScope getTipoVAlidacao() {
			return null ;
		}
		
	},
	
	LISTA{

		@Override
		public ViewScope getTipoVAlidacao() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	};
	
	public abstract ViewScope getTipoVAlidacao();
}
