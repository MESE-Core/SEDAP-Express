package de.bundeswehr.mese.sedapexpress.messagetool;

import javafx.scene.control.TextField;

public class SEDAPExpressNumberValidator  extends SEDAPExpressValidator{

	public SEDAPExpressNumberValidator(TextField textField) {
		super(textField);	
	}
	
	@Override
	protected boolean isValid(String text) {
		try {
			Double.parseDouble(text);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}
}
