package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import de.bundeswehr.mese.sedapexpress.messages.CONTACT;
import de.bundeswehr.mese.sedapexpress.messages.CONTACT.Source;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.DeleteFlag;
import de.bundeswehr.mese.sedapexpress.messages.SIDCCodes;
import de.bundeswehr.mese.sedapexpress.messagetool.MessageTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;

public class CONTACTPanelController extends MessagePanelController {

	
	
    @FXML
    private Label altitudeLabel;

    @FXML
    private TextField altitudeTextField;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Label contactIdLabel;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private Label courseLabel;

    @FXML
    private TextField courseTextField;

    @FXML
    private ComboBox<DeleteFlag> deleteFlagComboBox;

    @FXML
    private Label headingLabel;

    @FXML
    private TextField headingTextField;

    @FXML
    private Label heightLabel;

    @FXML
    private TextField heightTextField;

    @FXML
    private Label icaoLabel;

    @FXML
    private TextField icaoTextField;

    @FXML
    private Label latitudeLabel;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private Label lengthLabel;

    @FXML
    private TextField lengthTextField;

    @FXML
    private Button loadImageButton;

    @FXML
    private Label longitudeLabel;

    @FXML
    private TextField longitudeTextField;

    @FXML
    private Label mmsiLabel;

    @FXML
    private TextField mmsiTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label pitchLabel;

    @FXML
    private TextField pitchTextField;

    @FXML
    private Label relatveXdistanceLabel;

    @FXML
    private TextField relatveXdistanceTextField;

    @FXML
    private Label relatveYdistanceLabel;

    @FXML
    private TextField relatveYdistanceTextField;

    @FXML
    private Label relatveZdistanceLabel;

    @FXML
    private TextField relatveZdistanceTextField;

    @FXML
    private Label rollLabel;

    @FXML
    private TextField rollTextField;

    @FXML
    private ComboBox<String> sidcComboBox;

    @FXML
    private ComboBox<String> sidcDimComboBox;

    @FXML
    private ComboBox<String> sidcIDComboBox;
    
    @FXML
    private Label sourceLabel;
    
    @FXML
    private MenuButton sourceMenueButton;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField speedTextField;

    @FXML
    private Label widthLabel;

    @FXML
    private TextField widthTextField;

    @FXML
    void loadImage(ActionEvent event) {
    	
    	
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Graphic File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("Alle Dateien", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(MessageTool.getMainStage());
       
        if (selectedFile != null) {
            try {
               // String fileContent = Files.readString(Path.of(selectedFile.getPath()))[4];
            	// fileContent = Files.readString(Path.of(selectedFile.getPath()), StandardCharsets.ISO_8859_1);
            	 imageData = Files.readAllBytes(Path.of(selectedFile.getPath()));
            } catch (Exception e) {
                System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
            }
        } else {
            System.out.println("Keine Datei ausgewählt.");
        }
        
        /*
        
        if (selectedFile != null) {
            try {
                Path filePath = selectedFile.toPath();
                String fileContent = new String(Files.readString(filePath));
                System.out.println("File content stored in string.");
            } catch (Exception ex) {
                System.out.println("Error reading file: " + ex.getMessage());
            }
        }
*/
    }

    private String fileContent;
    
    @FXML
    void initialize() {
	assert this.altitudeLabel != null : "fx:id=\"altitudeLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.altitudeTextField != null : "fx:id=\"altitudeTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.commentTextArea != null : "fx:id=\"commentTextArea\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.courseLabel != null : "fx:id=\"courseLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.courseTextField != null : "fx:id=\"courseTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.deleteFlagComboBox != null : "fx:id=\"deleteFlagComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.headingLabel != null : "fx:id=\"headingLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.headingTextField != null : "fx:id=\"headingTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.heightLabel != null : "fx:id=\"heightLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.heightTextField != null : "fx:id=\"heightTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.icaoLabel != null : "fx:id=\"icaoLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.icaoTextField != null : "fx:id=\"icaoTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.latitudeTextField != null : "fx:id=\"latitudeTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.lengthLabel != null : "fx:id=\"lengthLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.lengthTextField != null : "fx:id=\"lengthTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.loadImageButton != null : "fx:id=\"loadImageButton\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.longitudeLabel != null : "fx:id=\"longitudeLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.longitudeTextField != null : "fx:id=\"longitudeTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.mmsiLabel != null : "fx:id=\"mmsiLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.mmsiTextField != null : "fx:id=\"mmsiTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.pitchLabel != null : "fx:id=\"pitchLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.pitchTextField != null : "fx:id=\"pitchTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveXdistanceLabel != null : "fx:id=\"relatveXdistanceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveXdistanceTextField != null : "fx:id=\"relatveXdistanceTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveYdistanceLabel != null : "fx:id=\"relatveYdistanceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveYdistanceTextField != null : "fx:id=\"relatveYdistanceTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveZdistanceLabel != null : "fx:id=\"relatveZdistanceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveZdistanceTextField != null : "fx:id=\"relatveZdistanceTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.rollLabel != null : "fx:id=\"rollLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.rollTextField != null : "fx:id=\"rollTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sidcComboBox != null : "fx:id=\"sidcComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sidcDimComboBox != null : "fx:id=\"sidcDimComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sidcIDComboBox != null : "fx:id=\"sidcIDComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
    assert sourceLabel != null : "fx:id=\"sourceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
    assert this.sourceMenueButton != null : "fx:id=\"sourceMenueButton\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.speedLabel != null : "fx:id=\"speedLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.speedTextField != null : "fx:id=\"speedTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.widthLabel != null : "fx:id=\"widthLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.widthTextField != null : "fx:id=\"widthTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";

	
	
	// TOOLTIPS
	Tooltip tooltipID = new Tooltip("A positive identification unique number or free text of the contact chosen by the sender of this message");
	Tooltip tooltipDeleteFlag = new Tooltip("Contact has to be removed");
	Tooltip tooltipICAO = new Tooltip("International Civil Aviation Organization");
	Tooltip tooltipImage = new Tooltip("Image data (JPG, PNG, TIF) encoded in BASE64");
	Tooltip tooltipComment = new Tooltip("Free text to the contact");
	this.icaoTextField.setTooltip(tooltipICAO);
	this.commentTextArea.setTooltip(tooltipComment);
	this.loadImageButton.setTooltip(tooltipImage);
	contactIdTextField.setTooltip(tooltipID);
	this.deleteFlagComboBox.setTooltip(tooltipDeleteFlag);
	
	
	// Contact ID
	contactIdTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			contactIdLabel.setVisible(false);
		}else {
			contactIdLabel.setVisible(true);
		}
		});
	
	
	// DeleteFlag
	this.deleteFlagComboBox.setItems(FXCollections.observableList(Arrays.asList(DeleteFlag.values())));
	this.deleteFlagComboBox.getSelectionModel().select(0);
	
	// Latitude
		latitudeTextField.setTextFormatter(MessagePanelController.createLatitudeFormatter());
		latitudeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty() && !newValue.equals("-")) {
	            try {
	                double value = Double.parseDouble(newValue);
	                latitudeTextField.setText(latitudeFormat.format(value));
	            } catch (NumberFormatException e) {
	                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
	            }
	        }else if (newValue.isEmpty()) {
	        	if(oldValue != null && oldValue.length() > 1 && latitudeTextField.getSelection().getLength() == 0) {
	        		latitudeTextField.setText(oldValue);
	        	}  
	        }
	    });
		latitudeTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
			if(!newValue) {
				latitudeLabel.setVisible(!validateAndFormatLatField(latitudeTextField));	
			}
		});
		
		// Longitude
		longitudeTextField.setTextFormatter(MessagePanelController.createLongitudeFormatter());
		longitudeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.isEmpty() && !newValue.equals("-")) {
	            try {
	                double value = Double.parseDouble(newValue);
	                
	                longitudeTextField.setText(longitudeFormat.format(value));
	            } catch (NumberFormatException e) {
	                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
	            }
	        }else if (newValue.isEmpty()) {
	        	if(oldValue != null && oldValue.length() > 1 && longitudeTextField.getSelection().getLength() == 0) {
	        		longitudeTextField.setText(oldValue);
	        	}  
	        }
	    });
		
		longitudeTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
			if(!newValue) {
				longitudeLabel.setVisible(!validateAndFormatLongField(longitudeTextField));
			}
		});
		
		// Altitude
		altitudeTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
			}
			return null;
		}));
		
		altitudeLabel.setVisible(false);
		
		// Rel. X-distance
		relatveXdistanceTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
				}
			return null;
		}));
		
		relatveXdistanceLabel.setVisible(false);		
		
		// Rel. Y-distance
		relatveYdistanceTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
			}
			return null;
		}));
		
		relatveYdistanceLabel.setVisible(false);
		
		// Rel. Z-distance
		relatveZdistanceTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
			}
			return null;
		}));
		
		relatveZdistanceLabel.setVisible(false);
		
		// Speed
		speedTextField.setTextFormatter(new TextFormatter<>(change -> {
			//String newText = change.getControlNewText();
			if(change.getControlNewText().matches(SEDAPExpressMessage.DOUBLE_MATCHER.toString())|| change.isDeleted()) {
				return change;
			}
			return null;
		}));
		speedLabel.setVisible(false);
		
		// Kurs
		courseTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
		courseTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.isEmpty() && !newValue.equals("")) {
	            try {
	                double value = Double.parseDouble(newValue);
	                courseTextField.setText(bearingFormat.format(value));
	            } catch (NumberFormatException e) {
	                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
	            }
	        }else if (newValue.isEmpty()) {
	        	if(oldValue != null && oldValue.length() > 1 && courseTextField.getSelection().getLength() == 0) {
	        		courseTextField.setText(oldValue);
	        	}  
	        }
	    });
		
		courseTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
			if(!newValue) {
				courseLabel.setVisible(!validateAndFormatCourseField(courseTextField));
			}
		});
		courseLabel.setVisible(false);
		
		// Heading
		headingTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
		headingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty() && !newValue.equals("")) {
	            try {
	                double value = Double.parseDouble(newValue);
	                headingTextField.setText(bearingFormat.format(value));
	            } catch (NumberFormatException e) {
	                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
	            }
	        }else if (newValue.isEmpty()) {
	        	if(oldValue != null && oldValue.length() > 1 && headingTextField.getSelection().getLength() == 0) {
	        		 headingTextField.setText(oldValue);
	        	}
	        }
	    });
		/*
		headingTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
			if(!newValue) {
				headingLabel.setVisible(!validateAndFormatCourseField(headingTextField));
			}
		});
		*/
		headingLabel.setVisible(false);
		
		// Roll
		rollTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
		rollTextField.textProperty().addListener((observable, oldValue,newValue) -> {
			if (!newValue.isEmpty() && !newValue.equals("")) {
	            try {
	                double value = Double.parseDouble(newValue);
	                rollTextField.setText(bearingFormat.format(value));
	            } catch (NumberFormatException e) {
	                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
	            }
	        }else if (newValue.isEmpty()) {
	        	if(oldValue != null && oldValue.length() > 1 && rollTextField.getSelection().getLength() == 0) {
	        		rollTextField.setText(oldValue);
	        	}
	        }
	    });
		/*
		rollTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
			if(!newValue) {
				rollLabel.setVisible(!validateAndFormatCourseField(rollTextField));
			}
		});
		*/
		rollLabel.setVisible(false);
		
		// Pitch
		pitchTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
		pitchTextField.textProperty().addListener((observable, oldValue,newValue) -> {
			if (!newValue.isEmpty() && !newValue.equals("")) {
	            try {
	                double value = Double.parseDouble(newValue);
	                pitchTextField.setText(bearingFormat.format(value));
	            } catch (NumberFormatException e) {
	                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
	            }
	        }else if (newValue.isEmpty()) {
	        	if(oldValue != null && oldValue.length() > 1 && pitchTextField.getSelection().getLength() == 0) {
	        		pitchTextField.setText(oldValue);
	        	}
	        }
	    });
		/*
		pitchTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
			if(!newValue) {
				pitchLabel.setVisible(!validateAndFormatCourseField(pitchTextField));
			}
		});
		*/
		pitchLabel.setVisible(false);
		
		// Name	
		// keine Formatter oder Validatoren
		
		// Source ###############################################################################################################
		
				sourceMenueButton.setText("Sources");
				sourceMenueButton.getItems().clear();
				ObservableList<CheckMenuItem> items = FXCollections.observableArrayList();

		        for (Source source : Source.values()) {
		            CheckMenuItem item = new CheckMenuItem(source.name() + " (" + source.getSourceValue() + ")");
		            item.setUserData(source);
		            items.add(item);
		        }

		        sourceMenueButton.getItems().addAll(items);
		       
		        sourceMenueButton.showingProperty().addListener((observable, oldValue, newValue) -> {
		            String label = "";
		            this.source="";
		        	if (!newValue) {
		            	for (int i = 0; i < sourceMenueButton.getItems().size(); i++) {
		            		if(items.get(i).isSelected()) {
		            		label = label + sourceMenueButton.getItems().get(i).getText().substring(0, sourceMenueButton.getItems().get(i).getText().length()-3);
		            		this.source = this.source + sourceMenueButton.getItems().get(i).getText().charAt(sourceMenueButton.getItems().get(i).getText().length()-2);
		            		}
		            	}
		            	sourceLabel.setText(label);
		            }
		        });
		        
		// Width
		widthTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
				}
			return null;
		}));
				
		widthLabel.setVisible(false);
		
		// Length
		lengthTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
			}
			return null;
		}));
		
		lengthLabel.setVisible(false);
		
		// Height
		heightTextField.setTextFormatter(new TextFormatter<>(change -> {
			String newText = change.getControlNewText();
			if(newText.matches("\\d*")) {
				return change;
			}
			return null;
		}));
		
		heightLabel.setVisible(false);
		
		

		
		
        //SIDC
		this.sidcIDComboBox.setItems(FXCollections.observableList(MessagePanelController.identitiesList));
		this.sidcIDComboBox.getSelectionModel().select(0);
		this.sidcDimComboBox.setItems(FXCollections.observableList(MessagePanelController.DimensionsList));
		this.sidcDimComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, o, n) -> {
		    switch ((int) n) {
		    case 0 -> this.sidcComboBox.setItems(null);
		    case 1 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.spaceCodesList));
		    case 2 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.airCodesList));
		    case 3 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.surfaceCodesList));
		    case 4 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.subsurfaceCodesList));
		    case 5 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.groundCodesList));
		    default -> throw new IllegalArgumentException();
		    }
		    this.sidcComboBox.getSelectionModel().select(0);
		});
		this.sidcDimComboBox.getSelectionModel().select(2);
		this.sidcComboBox.getSelectionModel().select(5);
		setSIDC();
		sidcIDComboBox.valueProperty().addListener((observable, oldvalue, newValue) -> {
			if(newValue != null) {
				setSIDC();
			}else {
				this.sidc = null;
				}		
			});
		
		sidcDimComboBox.valueProperty().addListener((observable, oldvalue, newValue) -> {
			if(newValue != null) {
				setSIDC();
			}else {
				this.sidc = null;
				}		
			});
	    
		sidcComboBox.valueProperty().addListener((observable, oldvalue, newValue) -> {
			if(newValue != null) {
				setSIDC();
			}else {
				this.sidc = null;
				}		
			});
		
		
		// MMSI
		// keine Formatter oder Validatoren
		mmsiLabel.setVisible(false);
		//ICAO
		// keine Formatter oder Validatoren
		icaoLabel.setVisible(false);
		//Comment
		// keine Formatter oder Validatoren
		
    }
    
    DecimalFormat latitudeFormat  = new DecimalFormat("00.0000");
    DecimalFormat longitudeFormat = new DecimalFormat("000.0000");
    DecimalFormat bearingFormat   = new DecimalFormat("000.0000");
    
    
    private byte[] imageData;
    private char[] sidc;  
    private String source ="";
    
    Set<Source> selectedSources = EnumSet.noneOf(Source.class);
    
    private void setSIDC() {
    	StringBuilder sb = new StringBuilder(MessagePanelController.getDimensionCharFromIndex(this.sidcDimComboBox.getSelectionModel().getSelectedIndex()) 
    			+ " " + this.sidcComboBox.getSelectionModel().getSelectedItem());
    	sb.setCharAt(1, MessagePanelController.getIdentiy(sidcIDComboBox.getValue()));
    	int pipeIndex = sb.indexOf("|");
    	    if (pipeIndex != -1) {
    	        sb.setLength(pipeIndex);
    	    }
    	sb.append("-----");
    	String tmp = sb.toString();
    	this.sidc = tmp.toCharArray();
    }
    
    private boolean validateAndFormatCourseField(TextField textField) {
    	try {
    		double value = Double.parseDouble(textField.getText().replace(',', '.'));
    		if(value >= 0.0 && value <= 359.99) {
    			textField.setText(String.format("%06.2f",value).replace(',', '.'));
    			return true;
    		}else {
    			textField.setText("");
    			return false;
    		}
    		}catch (NumberFormatException ex) {
    			textField.setText("");
    			return false;
    		}
    	}
    
    private boolean validateAndFormatLatField(TextField textField) {
    	try {
    		double value = Double.parseDouble(textField.getText().replace(',', '.'));
    		if(value >= -90 && value <= 90) {
    			textField.setText(latitudeFormat.format(value).replace(',', '.'));
    			return true;
    		}else {
    			textField.setText("");
    			return false;
    		}
    		}catch (NumberFormatException ex) {
    			textField.setText("");
    			return false;
    		}
    	}
    	
    private boolean validateAndFormatLongField(TextField textField) {
    	try {
    		double value = Double.parseDouble(textField.getText().replace(',', '.'));
    		if(value >= -180 && value <= 180) {
    			textField.setText(longitudeFormat.format(value).replace(',', '.'));
    			return true;
    		}else {
    			textField.setText("");
    			return false;
    		}
    		}catch (NumberFormatException ex) {
    			textField.setText("");
    			return false;
    		}
    	}

    @Override
    public CONTACT createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

    CONTACT contact = new CONTACT (number, time, sender,
		classification, acknowledgement, mac,
		
    	this.contactIdTextField.getText(),
    	this.deleteFlagComboBox.getValue(),
    	Double.parseDouble(this.latitudeTextField.getText()),
    	Double.parseDouble(this.longitudeTextField.getText()),
    	this.altitudeTextField.getText().isEmpty() ? null: Double.parseDouble(this.altitudeTextField.getText()),
    	this.relatveXdistanceTextField.getText().isEmpty() ? null :Double.parseDouble(this.relatveXdistanceTextField.getText()),
    	this.relatveYdistanceTextField.getText().isEmpty() ? null :Double.parseDouble(this.relatveYdistanceTextField.getText()),
    	this.relatveZdistanceTextField.getText().isEmpty() ? null :Double.parseDouble(this.relatveZdistanceTextField.getText()),
    	this.speedTextField.getText().isEmpty() ? null :Double.parseDouble(this.speedTextField.getText()),
    	this.courseTextField.getText().isEmpty() ? null :Double.parseDouble(this.courseTextField.getText()),
    	this.headingTextField.getText().isEmpty() ? null :Double.parseDouble(this.headingTextField.getText()),
    	this.rollTextField.getText().isEmpty() ? null :Double.parseDouble(this.rollTextField.getText()),
    	this.pitchTextField.getText().isEmpty() ? null :Double.parseDouble(this.pitchTextField.getText()),
    	this.widthTextField.getText().isEmpty() ? null :Double.parseDouble(this.widthTextField.getText()),
    	this.lengthTextField.getText().isEmpty() ? null :Double.parseDouble(this.lengthTextField.getText()),
    	this.heightTextField.getText().isEmpty() ? null :Double.parseDouble(this.heightTextField.getText()),
    	this.nameTextField.getText(),
    	this.source,
    	this.sidc,
    	this.mmsiTextField.getText(),
    	this.icaoTextField.getText(),
    	this.imageData,
    	this.commentTextArea.getText());																										 
  
	return contact;
    }

    @Override
    public boolean isValidFilled() {

    	return (!contactIdLabel.isVisible() && !latitudeLabel.isVisible() && !longitudeLabel.isVisible());
    }
    
}

