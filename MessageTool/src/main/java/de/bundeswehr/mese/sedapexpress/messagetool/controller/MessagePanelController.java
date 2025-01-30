/*******************************************************************************
 * Copyright (C)2012-2024, German Federal Armed Forces - All rights reserved.
 *
 * MUKdo II
 * Wibbelhofstraße 3
 * 26384 Wilhelmshaven
 * Germany
 *
 * This source code is part of the MEDAS/SEDAP Project.
 * Person of contact (POC): Volker Voß, MUKdo II A, Wilhelmshaven
 *
 * Unauthorized use, modification, redistributing, copying, selling and
 * printing of this file in source and binary form including accompanying
 * materials is STRICTLY prohibited.
 *
 * This source code and it's parts is classified as OFFEN / NATO UNCLASSIFIED!
 *******************************************************************************/
package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;



public abstract class MessagePanelController {

    public abstract SEDAPExpressMessage createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac);

    public abstract boolean isValidFilled();
    
    public static boolean isValid8BitHex(String input) {
        if (input == null || input.length() != 2) {
            return false;
        }
        try {
            int value = Integer.parseInt(input, 16);
            return value >= 0 && value <= 255;
            	
        } catch (NumberFormatException e) {
           return false;
        }
    }
    
    public static boolean isValid7BitHex(String input) {
        if (input == null || input.length() != 2) {
            return false;
        }
        try {
        	
            int value = Integer.parseInt(input, 16);
            return value >= 0 && value <= 127;
            	
        } catch (NumberFormatException e) {
           return false;
        }
    }
    
    public static TextFormatter<Integer> createHexFormatter(){
    	
    	StringConverter<Integer> hexConverter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ? Integer.toHexString(object).toUpperCase() : "";
            }

            @Override
            public Integer fromString(String string) {
                return string.isEmpty() ? null : Integer.parseInt(string, 16);
            }
        };
        
        UnaryOperator<TextFormatter.Change> hexFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches(SEDAPExpressMessage.NUMBER_MATCHER.pattern()) || newText.equals("")) {
            	return change;
            }
            return null;
        };
    	
    	 return new TextFormatter<>(hexConverter,null,hexFilter);
    }
    
    public static TextFormatter<String> createRGBAHexFormatter() {
    	
    	UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText().toUpperCase();
           if (newText.matches("^#?[0-9A-Fa-f]{0,8}$")) {
           // if (newText.matches(SEDAPExpressMessage.NUMBER_MATCHER.pattern())) {
            	change.setText(change.getText().toUpperCase());
            	return change;
            }
            return null;
        };

        return new TextFormatter<>(filter);
    }

    
    public static TextFormatter<Double> createCourseFormatter(){
    	
    	Pattern validEditigState = Pattern.compile("^\\d{0,3}(\\.\\d{0,2})?$");
        
        StringConverter<Double> courseConverter = new StringConverter<Double>() {
            @Override
            public Double fromString( String s) {
            	if (s.isEmpty()) return 0.0;
            	return (Double.valueOf(s));    	
            }

            @Override
            public String toString(Double d) {
                if(d == null) return "";
                return String.format("%.2f",d);
            }
        };
        
        UnaryOperator<TextFormatter.Change> courseFilter = change -> {
            String newText = change.getControlNewText();
          
            if (validEditigState.matcher(newText).matches() ) {
                try {
                	double value = Double.parseDouble(newText);
                	if(value >= 0.0 && value <= 359.99) {
                		return change;
                	}
                }catch (NumberFormatException ex) {
                	if(newText.isEmpty()) {
                		return change;
                	}
                }
            	
            	return change;
            }else {
            	return null;
            }
        };
    
        return new TextFormatter<>(courseConverter,null,courseFilter);
    }
    
    public static TextFormatter<Double> createLatitudeFormatter(){
    	
    	DecimalFormat format = new DecimalFormat("00.0000");
    	
    	
    	 StringConverter<Double> latitudeConverter = new StringConverter<Double>() {
             @Override
             public String toString(Double value) {
            	 if (value == null) {
                     return "";
                 }
                 return format.format(value);
             }

             @Override
             public Double fromString(String string) {
            	 if (string == null || string.isEmpty()) {
                     return null;
                 }
                 ParsePosition parsePosition = new ParsePosition(0);
                 Double result = format.parse(string, parsePosition).doubleValue();
                 return parsePosition.getIndex() == string.length() ? result : null;
             }
         };

         UnaryOperator<TextFormatter.Change> latitudeFilter = change -> {
             String newText = change.getControlNewText();

            	if (newText.matches("-?\\d*\\.?\\d*")) {
                 try {
                     double value = Double.parseDouble(newText);
                     if (value >= -90 && value <= 90) {
                         return change;
                     }
                 } catch (NumberFormatException e) {
                     if (newText.isEmpty() || newText.equals("-")) {
                         return change;
                     }
                 }
             }
             return null;
         };
         return new TextFormatter<>(latitudeConverter,null,latitudeFilter);
    }
    
    public static TextFormatter<Double> createLongitudeFormatter(){
    	
    	DecimalFormat format = new DecimalFormat("000.0000");
    	
    	
    	 StringConverter<Double> longitudeConverter = new StringConverter<Double>() {
             @Override
             public String toString(Double value) {
            	 if (value == null) {
                     return "";
                 }
                 return format.format(value);
             }

             @Override
             public Double fromString(String string) {
            	 if (string == null || string.isEmpty()) {
                     return null;
                 }
                 ParsePosition parsePosition = new ParsePosition(0);
                 Double result = format.parse(string, parsePosition).doubleValue();
                 return parsePosition.getIndex() == string.length() ? result : null;
             }
         };

         UnaryOperator<TextFormatter.Change> longitudeFilter = change -> {
             String newText = change.getControlNewText();

            	if (newText.matches("-?\\d*\\.?\\d*")) {
                 try {
                     double value = Double.parseDouble(newText);
                     if (value >= -180 && value <= 180) {
                         return change;
                     }
                 } catch (NumberFormatException e) {
                     if (newText.isEmpty() || newText.equals("-")) {
                         return change;
                     }
                 }
             }
             return null;
         };
         return new TextFormatter<>(longitudeConverter,null,longitudeFilter);
    }
    
    public static TextFormatter<Double> createBearingFormatter(){
    	
    	DecimalFormat format = new DecimalFormat("000.0000");
    	format.setDecimalSeparatorAlwaysShown(true);
    	
    	 StringConverter<Double> bearingConverter = new StringConverter<Double>() {
    		 @Override
             public String toString(Double value) {
                 if (value == null) {
                     return "";
                 }
                 return format.format(value);
             }

             @Override
             public Double fromString(String string) {
                 if (string == null || string.isEmpty()) {
                     return null;
                 }
                 ParsePosition parsePosition = new ParsePosition(0);
                 Double result = format.parse(string, parsePosition).doubleValue();
                 if (parsePosition.getIndex() < string.length() || result < 0 || result > 360) {
                     return null;
                 }
                 return result;
             }
         };

         UnaryOperator<TextFormatter.Change> bearingFilter = change -> {
             String newText = change.getControlNewText();

            	if (newText.matches("\\d{0,3}(\\.\\d{0,4})?")) {
                 try {
                     double value = Double.parseDouble(newText);
                     if (value >= 0 && value <= 359.9999) {
                         return change;
                     }
                 } catch (NumberFormatException e) {
                     if (newText.isEmpty() || newText.equals("-")) {
                         return change;
                     }
                 }
             }
             return null;
         };
         return new TextFormatter<>(bearingConverter,null,bearingFilter);
    }
    
    public static TextFormatter<Integer> posIntegerFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
        String newText = change.getControlNewText();
        if (newText.matches("([1-9][0-9]*)?")) {
            return change;
        }
        return null;
    });
    
    // Dimension
     public static final List<String> DimensionsList = new ArrayList<>(Arrays.asList(
    		 "Unknwon", 
    		 "Space", 
    		 "Air", 
    		 "Surface", 
    		 "SubSurface",
    		 "Ground"));
     
     public static char getDimensionCharFromIndex(int index) {
    	 return switch (index) {
    		 case 0 -> 'z';
    		 case 1 -> 'p';
    		 case 2 -> 'a';
    		 case 3 -> 's';
    		 case 4 -> 'u';
    		 case 5 -> 'g';
    		 default -> 'z';
    	 };
    		 
     }
     
     // Identity
     public static final List<String> identitiesList = new ArrayList<>(Arrays.asList(
    			    "Pending",
    			    "Unknown",
    			    "Hostile",
    			    "Suspect",
    			    "Neutral",
    			    "Assumed friend",
    			    "Friend"));
     
     public static final char getIdentiy(String ident) {
     	return switch (ident) {
  	    case "Pending" -> 'p';
  	    case "Unknown" -> 'u';
  	    case "Hostile" -> 'h';
  	    case "Suspect" -> 's';
  	    case "Neutral" -> 'n';
  	    case "Assumed friend" -> 'a';
  	    case "Friend" -> 'f';
  	    default -> 'p';
  	    };
     }
}


     
