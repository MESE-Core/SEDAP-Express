package de.bundeswehr.mese.sedapexpress.messagetool;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public abstract class SEDAPExpressFormatter {

	private static final String ALLOWED_CHARACTERS = "[a-zA-Z]*";
	private static final String ALLOWED_HEX = "[a-FA-F]0-9*";
	
	protected static UnaryOperator<TextFormatter.Change> getDigitFilter(int max){
			
		return change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				if((newText.length() <= max) || (max == 0)) {
				return change;
				}else {
					return null;
				}
			}
			return null;
		};
	}
		/**
		 * 
		 * @param max = Anzahl der max erlaubten Stellen, bei 0 = unbegrenzt
		 * @return Formatter, der nur Ziffern zulässt bis zur max Anzahl
		 */
	public static TextFormatter<String> getDigitFormatter(int max){
		return new TextFormatter<>(getDigitFilter(max));
	}
		
	protected static UnaryOperator<TextFormatter.Change> getCharFilter(int max){
			
		return change -> {
			String newText = change.getControlNewText();
			if(newText.matches(ALLOWED_CHARACTERS)) {
				if((newText.length() <= max) || (max == 0)) {
				return change;
				}else {
					return null;
				}
			}
			return null;
		};
	}
		/**
		 * 
		 * @param max = Anzahl der max erlaubten Stellen, bei 0 = unbegrenzt
		 * @return Formatter, der nur hexadezimale Werte zulässt bis zur max Anzahl
		 */
	public static TextFormatter<String> getCharFormatter(int max){
		return new TextFormatter<>(getCharFilter(max));
	}
		
	protected static UnaryOperator<TextFormatter.Change> getHexFilter(int max){
			
		return change -> {
			String newText = change.getControlNewText();
			if(newText.matches(ALLOWED_HEX)) {
				if((newText.length() <= max) || (max == 0)) {
					change.setText(change.getText().toUpperCase());
					return change;
				}else {
					return null;
				}
			}
				return null;
		};
	}
		/**
		 * 
		 * @param max = Anzahl der max erlaubten Stellen, bei 0 = unbegrenzt
		 * @return Formatter, der nur Ziffern zulässt bis zur max Anzahl
		 */
	public static TextFormatter<String> getHexFormatter(int max){
		return new TextFormatter<>(getHexFilter(max));
	}
	
}
