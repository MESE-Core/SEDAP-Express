package de.bundeswehr.mese.sedapexpress.messagetool;

import javafx.scene.control.TextField;

public abstract class SEDAPExpressValidator {

	protected final TextField textfield;
	
	public SEDAPExpressValidator (TextField textfield) {
		this.textfield = textfield;
		setupValidator();
	}
	
	protected abstract boolean isValid(String text);
		
	private void setupValidator() {
		
		textfield.textProperty().addListener((observable, oldValuse, newValue) -> {
			if(isValid(newValue)) {
				textfield.setStyle("-fx-border-color: green;");
			}else {
				textfield.setStyle("-fx-border-color: red;");
			}
		});
	}
}
