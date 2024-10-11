/**
 * Note: This license has also been called the “Simplified BSD License” and the “FreeBSD License”.
 *
 * Copyright 2024 MESE POC: Volker Voß, Federal Armed Forces of Germany
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSEnARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BEn LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package de.bundeswehr.mese.sedapexpress.messages;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Volker Voß
 */
public abstract class SEDAPExpressMessage implements Comparable<SEDAPExpressMessage>, Serializable {

    private static final long serialVersionUID = 6080930577547088687L;

    protected static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static {
	SEDAPExpressMessage.logger.setLevel(Level.ALL);
    }

    public enum MessageType {

	ACKNOWLEDGE, COMMAND, CONTACT, EMISSION, GENERIC, GRAPHIC, HEARTBEAT, TIMESYNC, KEYEXCHANGE, METEO, OWNUNIT, RESEND, STATUS, TEXT;

	public static MessageType valueOfMessageType(String type) {
	    return MessageType.valueOf(type.toUpperCase());
	}
    }

    public static final NumberFormat numberFormatter = new DecimalFormat("##.############", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    public enum TextEncoding {
	NONE, BASE64;

	public static TextEncoding valueOfTextEncoding(String type) {
	    if ("BASE64".equalsIgnoreCase(type)) {
		return BASE64;
	    } else {
		return NONE;
	    }
	}

	public static TextEncoding valueOfTextEncoding(int type) {
	    if (type == 1) {
		return BASE64;
	    } else {
		return NONE;
	    }
	}

    }

    public enum Classification {
	NONE('-'), PUBLIC('P'), UNCLAS('U'), RESTRICTED('R'), CONFIDENTIAL('C'), SECRET('S'), TOP_SECRET('T');

	public static Classification getValueOfClassificationChar(char character) {
	    return switch (character) {
	    case '-' -> Classification.NONE;
	    case 'P', 'p' -> Classification.PUBLIC;
	    case 'U', 'u' -> Classification.UNCLAS;
	    case 'R', 'r' -> Classification.RESTRICTED;
	    case 'C', 'c' -> Classification.CONFIDENTIAL;
	    case 'S', 's' -> Classification.SECRET;
	    case 'T', 't' -> Classification.TOP_SECRET;
	    default -> Classification.NONE;
	    };
	}

	char value;

	public char getValue() {
	    return this.value;
	}

	Classification(char classification) {
	    this.value = classification;
	}

    }

    public static final Classification NATO_RESTRICTED = Classification.RESTRICTED;
    public static final Classification NATO_CONFIDENTIAL = Classification.CONFIDENTIAL;
    public static final Classification NATO_SECRET = Classification.SECRET;
    public static final Classification COSMIC_TOP_SECRET = Classification.TOP_SECRET;

    public static final Classification EU_RESTRICTED = Classification.RESTRICTED;
    public static final Classification EU_CONFIDENTIAL = Classification.CONFIDENTIAL;
    public static final Classification EU_SECRET = Classification.SECRET;
    public static final Classification EU_TOP_SECRET = Classification.TOP_SECRET;

    public static final Classification RESTREINT_UE = Classification.RESTRICTED;
    public static final Classification CONFIDENTIEL_UE = Classification.CONFIDENTIAL;
    public static final Classification SECRET_UE = Classification.SECRET;
    public static final Classification TRES_SECRET_UE = Classification.TOP_SECRET;

    public static final Classification KEINE = Classification.NONE;
    public static final Classification OEFFENTLICH = Classification.PUBLIC;
    public static final Classification OFFEN = Classification.UNCLAS;
    public static final Classification VS_NFD = Classification.RESTRICTED;
    public static final Classification VS_VERTRAULICH = Classification.CONFIDENTIAL;
    public static final Classification GEHEIM = Classification.SECRET;
    public static final Classification STRENG_GEHEIM = Classification.TOP_SECRET;

    public enum Acknowledgement {
	NO, YES;

	public static Acknowledgement valueOfAcknowledgement(String acknowledgement) {

	    if ("YES".equalsIgnoreCase(acknowledgement)) {
		return YES;
	    } else {
		return NO;
	    }
	}

	public static Acknowledgement valueOfAcknowledgement(int acknowledgement) {

	    if (acknowledgement == 1) {
		return YES;
	    } else {
		return NO;
	    }
	}

    }

    public static final HexFormat HEXFOMATER = HexFormat.of().withUpperCase();

    // Environment
    public static final char ENVIRONMENT_UNKNOWN = 'z';
    public static final char ENVIRONMENT_SPACE = 'p';
    public static final char ENVIRONMENT_AIR = 'a';
    public static final char ENVIRONMENT_SURFACE = 's';
    public static final char ENVIRONMENT_SUBSURFACE = 'u';
    public static final char ENVIRONMENT_GROUND = 'g';

    public static final List<String> dimensionsList = new ArrayList<>(Arrays.asList("Unknown", "Space", "Air", "Surface", "SubSurface", "Ground"));

    // Identity
    public static final char IDENT_PENDING = 'p';
    public static final char IDENT_UNKNOWN = 'u';
    public static final char IDENT_HOSTILE = 'h';
    public static final char IDENT_SUSPECT = 's';
    public static final char IDENT_NEUTRAL = 'n';
    public static final char IDENT_ASSFRIEND = 'a';
    public static final char IDENT_FRIEND = 'f';

    public static final List<String> identitiesList = new ArrayList<>(Arrays.asList("Pending", "Unknown", "Hostile", "Suspect", "Neutral", "Assumed friend", "Friend"));

    public static final Pattern NAME_MATCHER = Pattern.compile("^[a-zA-Z]+$"); // Name
    public static final Pattern NUMBER_MATCHER = Pattern.compile("^[A-Fa-f0-9]{1,2}$"); // Number
    public static final Pattern TIME_MATCHER = Pattern.compile("^[A-Fa-f0-9]{8,16}$"); // Time
    public static final Pattern MAC_MATCHER = Pattern.compile("^[A-Fa-f0-9]{1,32}$"); // HexNumber
    public static final Pattern HEXNUMBER_MATCHER = SEDAPExpressMessage.MAC_MATCHER;
    public static final Pattern TEXTTYPE_MATCHER = Pattern.compile("^[0-4]$"); // Text type

    public static final Pattern DOUBLE_MATCHER = Pattern.compile("^-?\\d+.?\\d*$"); // Double
    public static final Pattern POSITIVE_DOUBLE_MATCHER = Pattern.compile("^\\d+.?\\d*$"); // Double
    public static final Pattern DOUBLE_LIST_MATCHER = Pattern.compile("^[\\d.?\\d?#]*$"); // Double
    public static final Pattern POS_INTEGER_MATCHER = Pattern.compile("^\\d+$"); // Positive Integer
    public static final Pattern INTEGER_MATCHER = Pattern.compile("^-?\\d+$"); // Integer
    public static final Pattern BIGINTEGER_MATCHER = Pattern.compile("^([A-Fa-f0-9][A-Fa-f0-9])+$"); // Hexadecimal BigInteger

    public static final Pattern BEARING_MATCHER = Pattern.compile("^(\\d+\\.?\\d*|[012]\\d\\d\\.\\d*|3[0-5]\\d\\\\.\\d*)$"); // 000.00-359.999
    public static final Pattern SIDC_MATCHER = Pattern.compile("^[a-zA-Z-]{15}$"); // SIDC
    public static final Pattern MMSI_MATCHER = SEDAPExpressMessage.INTEGER_MATCHER; // Integer
    public static final Pattern ICAO_MATCHER = Pattern.compile("^[A-F0-9]{1,6}$"); // ICAO
    public static final Pattern SOURCE_MATCHER = Pattern.compile("^[R,A,I,S,E,O,Y,M]+$"); // Source type
    public static final Pattern CMDTYPE_MATCHER = Pattern.compile("^[A-Fa-f0-9]+$"); // Command type
    public static final Pattern GRAPHICTYPE_MATCHER = Pattern.compile("^[0-14]$"); // Graphic type
    public static final Pattern RGBA_MATCHER = Pattern.compile("^[0-9A-F]{8}$"); // RGBA Format´
    public static final Pattern TECSTATUS_MATCHER = Pattern.compile("^[0-5]$"); // TecStatus
    public static final Pattern OPSSTATUS_MATCHER = Pattern.compile("^[0-4]$"); // OpsStatus
    public static final Pattern PERCENT_MATCHER = Pattern.compile("^([A-Za-z0-9]*#(100(\\\\.0+)?|(\\d{1,2}(.\\d+)*))#*)+$"); // Percent
    public static final Pattern ENCODING_MATCHER = Pattern.compile("^[BASE64]|[ASCII]|[BINARY]$");

    public static boolean matchesPattern(Pattern pattern, String value) {
	return pattern.matcher(value).matches();
    }

    private Short number;

    private Long time;

    private String sender;

    private Classification classification;

    private Acknowledgement acknowledgement;

    private String mac;

    public static final HexFormat formatter = HexFormat.of().withUpperCase();

    public Short getNumber() {
	return this.number;
    }

    public void setNumber(Short number) {
	this.number = number;
    }

    public Long getTime() {
	return this.time;
    }

    public void setTime(Long time) {
	this.time = time;
    }

    public String getSender() {
	return this.sender;
    }

    public void setSender(String sender) {
	this.sender = sender;
    }

    public Classification getClassification() {
	return this.classification;
    }

    public void setClassification(Classification classification) {
	this.classification = classification;
    }

    public Acknowledgement getAcknowledgement() {
	return this.acknowledgement;
    }

    public void setAcknowledgement(Acknowledgement acknowledgement) {
	this.acknowledgement = acknowledgement;
    }

    public String getMAC() {
	return this.mac;
    }

    public void setMAC(String mac) {
	this.mac = mac;
    }

    public MessageType getMessageType() {
	return MessageType.valueOfMessageType(this.getClass().getSimpleName());
    }

    @Override
    public int compareTo(final SEDAPExpressMessage otherMessage) {

	return this.getClass().getSimpleName().compareTo(otherMessage.getClass().getSimpleName());
    }

    @Override
    public boolean equals(final Object obj) {

	return super.equals(obj);
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    /**
     * If one is using for instance a ComboBox with the available classifications,
     * this method can be used to get the matching classification depending on the
     * selected index.
     *
     * @param selectedIndex of for instance a List or a ComboBox
     * @return classification code
     */
    public static char getClassificationFromIndex(final int selectedIndex) {

	return switch (selectedIndex) {
	case 0 -> Classification.PUBLIC.value;
	case 1 -> Classification.UNCLAS.value;
	case 2 -> Classification.RESTRICTED.value;
	case 3 -> Classification.CONFIDENTIAL.value;
	case 4 -> Classification.SECRET.value;
	case 5 -> Classification.TOP_SECRET.value;
	default -> '-';
	};
    }

    /**
     * Returns the higher classification of two given classifications.
     *
     * @param classification1 first classification code
     * @param classification2 second classification code
     * @return the higher classification
     */
    public static char getMaxClassification(final char classification1, final char classification2) {

	return switch (Classification.getValueOfClassificationChar(classification1)) {
	case NONE, PUBLIC -> classification2;
	case UNCLAS -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC -> classification1;
	default -> classification2;
	};
	case RESTRICTED -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC, UNCLAS -> classification1;
	default -> classification2;
	};
	case CONFIDENTIAL -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC, UNCLAS, RESTRICTED -> classification1;
	default -> classification2;
	};
	case SECRET -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC, UNCLAS, RESTRICTED, CONFIDENTIAL -> classification1;
	default -> classification2;
	};
	case TOP_SECRET -> classification1;
	default -> classification1;
	};
    }

    /**
     * Returns the lower classification of two given classifications.
     *
     * @param classification1 first classification code
     * @param classification2 second classification code
     * @return the lower classification
     */
    public static char getMinClassification(final char classification1, final char classification2) {

	return switch (Classification.getValueOfClassificationChar(classification1)) {
	case PUBLIC -> classification1;
	case UNCLAS -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC -> classification2;
	default -> classification1;
	};
	case RESTRICTED -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC, UNCLAS -> classification2;
	default -> classification1;
	};
	case CONFIDENTIAL -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC, UNCLAS, RESTRICTED -> classification2;
	default -> classification1;
	};
	case SECRET -> switch (Classification.getValueOfClassificationChar(classification2)) {
	case PUBLIC, UNCLAS, RESTRICTED, CONFIDENTIAL -> classification2;
	default -> classification1;
	};
	case TOP_SECRET -> classification2;
	default -> classification2;
	};
    }

    /**
     * Returns the textual representation of the given classification code.
     *
     * @param classification
     * @return the matching textual representation
     */
    public static String getClassificationFullNameFromClassification(final char classification) {

	return switch (Classification.getValueOfClassificationChar(classification)) {
	case PUBLIC -> "public";
	case UNCLAS -> "unclas";
	case RESTRICTED -> "restricted";
	case CONFIDENTIAL -> "confidential";
	case SECRET -> "secret";
	case TOP_SECRET -> "top secret";
	default -> "-";
	};
    }

    /**
     * Returns the textual representation of the given classification code.
     *
     * @param classification
     * @return the matching textual representation
     */
    public static String getClassificationFullNameFromClassification(final Classification classification) {

	return switch (classification) {
	case PUBLIC -> "public";
	case UNCLAS -> "unclas";
	case RESTRICTED -> "restricted";
	case CONFIDENTIAL -> "confidential";
	case SECRET -> "secret";
	case TOP_SECRET -> "top secret";
	default -> "-";
	};
    }

    /**
     * Returns the classification code from a given textual representation
     *
     * @param classificationFullName Textual classification
     * @return the matching classification code
     */
    public static char getClassificationFromFullName(final String classificationFullName) {

	return switch (classificationFullName.trim().toUpperCase().replace("NATO", "").replace("EU", "").replace(" ", "")) {
	case "OEFFENTLICH", "PUBLIC" -> Classification.PUBLIC.value;
	case "OFFEN", "UNCLAS" -> Classification.UNCLAS.value;
	case "VS-NFD", "RESTRICTED" -> Classification.RESTRICTED.value;
	case "VS-VERTRAULICH", "CONFIDENTIAL" -> Classification.CONFIDENTIAL.value;
	case "GEHEIM", "VS-GEHEIM", "SECRET" -> Classification.SECRET.value;
	case "STRENGGEHEIM", "VS-STRENGGEHEIM", "TOP SECRET" -> Classification.TOP_SECRET.value;
	default -> '-';
	};
    }

    /**
     * Compares two classifications with each other and returns whether the given
     * classification is within the maximum permissible classification range.
     *
     * @param classification    classification to text
     * @param maxClassification Maximal permitted classification
     * @return true=classification permitted, false=classification NOT permitted
     */
    public static boolean isClassificationIncluded(final char classification, final char maxClassification) {

	Classification classificationEnum = Classification.getValueOfClassificationChar(classification);
	Classification maxClassificationEnum = Classification.getValueOfClassificationChar(maxClassification);

	return switch (classificationEnum) {
	case Classification.NONE -> false;
	case Classification.PUBLIC -> true; // Always permitted
	case Classification.UNCLAS -> (maxClassificationEnum == Classification.UNCLAS) || (maxClassificationEnum == Classification.RESTRICTED) || (maxClassificationEnum == Classification.CONFIDENTIAL)
		|| (maxClassificationEnum == Classification.SECRET) || (maxClassificationEnum == Classification.TOP_SECRET);
	case Classification.RESTRICTED ->
	    (maxClassificationEnum == Classification.RESTRICTED) || (maxClassificationEnum == Classification.CONFIDENTIAL) || (maxClassificationEnum == Classification.SECRET) || (maxClassificationEnum == Classification.TOP_SECRET);
	case Classification.CONFIDENTIAL -> (maxClassificationEnum == Classification.CONFIDENTIAL) || (maxClassificationEnum == Classification.SECRET) || (maxClassificationEnum == Classification.TOP_SECRET);
	case Classification.SECRET -> (maxClassificationEnum == Classification.SECRET) || (maxClassificationEnum == Classification.TOP_SECRET);
	case Classification.TOP_SECRET -> maxClassificationEnum == Classification.TOP_SECRET;
	default -> throw new IllegalArgumentException("Unexpected value: " + classificationEnum);
	};
    }

    /**
     * Split a serialized SEDAP-Express message into a list if values.
     *
     * @param message SEDAP-Express message
     * @return Array with the content of the SEDAP-Express message
     */
    public static List<String> splitMessage(final String message) {

	final var sb = new StringBuilder();
	final var words = new ArrayList<String>();
	words.ensureCapacity(6); // Minimal size
	final var strArray = message.toCharArray();
	for (final char c : strArray) {
	    if (c == ';') {
		if (sb.length() != 0) {
		    words.add(sb.toString());
		    sb.delete(0, sb.length());
		} else {
		    words.add("");
		}
	    } else {
		sb.append(c);
	    }
	}

	if (sb.length() != 0) {
	    words.add(sb.toString());
	} else {
	    words.add("");
	}

	return words;
    }

    /**
     * Splits a #-list in a serialized SEDAP-Express message into a list if string
     * values.
     *
     * @param list #-list
     * @return Array with the content of the #-list
     */
    public static List<String> splitStringDataHashTag(final String list) {

	final var sb = new StringBuilder();
	final var words = new ArrayList<String>();
	final var strArray = list.toCharArray();
	for (final char c : strArray) {
	    if (c == '#') {
		if (sb.length() != 0) {
		    words.add(sb.toString());
		    sb.delete(0, sb.length());
		} else {
		    words.add(null);
		}
	    } else {
		sb.append(c);
	    }
	}

	if (sb.length() != 0) {
	    words.add(sb.toString());
	} else {
	    words.add(null);
	}

	return words;
    }

    /**
     * Splits a #-list in a serialized SEDAP-Express message into a list if integer
     * values.
     *
     * @param list #-list
     * @return Array with the content of the #-list
     */
    public static List<Integer> splitIntegerDataHashTag(final String list) {

	final var sb = new StringBuilder();
	final var words = new ArrayList<Integer>();
	final var strArray = list.toCharArray();
	for (final char c : strArray) {
	    if (c == '#') {
		if (sb.length() != 0) {
		    words.add(Integer.valueOf(sb.toString()));
		    sb.delete(0, sb.length());
		} else {
		    words.add(null);
		}
	    } else {
		sb.append(c);
	    }
	}

	if (sb.length() != 0) {
	    words.add(Integer.valueOf(sb.toString()));
	} else {
	    words.add(null);
	}

	return words;
    }

    /**
     * Splits a #-list in a serialized SEDAP-Express message into a list if double
     * values.
     *
     * @param list #-list
     * @return Array with the content of the #-list
     */
    public static List<Double> splitDoubleDataHashTag(final String list) {

	final var sb = new StringBuilder();
	final var words = new ArrayList<Double>();
	final var strArray = list.toCharArray();
	for (final char c : strArray) {
	    if (c == '#') {
		if (sb.length() != 0) {
		    words.add(Double.valueOf(sb.toString()));
		    sb.delete(0, sb.length());
		} else {
		    words.add(null);
		}
	    } else {
		sb.append(c);
	    }
	}

	if (sb.length() != 0) {
	    words.add(Double.valueOf(sb.toString()));
	} else {
	    words.add(null);
	}

	return words;
    }

    /**
     *
     * @param list List of elements
     * @return String with #-separated list elements
     */
    public String getStringFromList(List<?> list) {

	StringBuilder str = new StringBuilder();
	list.forEach(freq -> str.append('#').append(freq));
	str.deleteCharAt(0);

	return str.toString();
    }

    /**
     *
     */
    private static final ConcurrentHashMap<String, Constructor<?>> messageNameToConstructor = new ConcurrentHashMap<>();
    static {

	final Class<?>[] paramTypes = new Class[] { Iterator.class };

	Arrays.asList(

		ACKNOWLEDGE.class, COMMAND.class, CONTACT.class, EMISSION.class, GENERIC.class, GRAPHIC.class, HEARTBEAT.class, TIMESYNC.class, KEYEXCHANGE.class, METEO.class, OWNUNIT.class, RESEND.class, STATUS.class, TEXT.class)
		.forEach(clazz -> {
		    try {
			SEDAPExpressMessage.messageNameToConstructor.put(clazz.getSimpleName(), clazz.getConstructor(paramTypes));
		    } catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		    }
		});
    }

    static {
	System.out.println("""
		***************************************************
		SEDAP-Express v1.0, ©2024 - Bundeswehr, Volker Voss
		***************************************************""");
    }

    /**
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param mac             Message Authentification Code
     */
    protected SEDAPExpressMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {
	super();
	this.number = number;
	this.time = time;
	this.sender = sender;
	this.classification = classification;
	this.acknowledgement = acknowledgement;
	this.mac = mac;
    }

    /**
     * Deserializes the header of a serialized SEDAP-Express message.
     *
     * @param message
     */
    protected SEDAPExpressMessage(Iterator<String> message) {

	try {

	    String value;
	    if (message.hasNext()) {
		value = message.next();
		if (value.isEmpty()) {
		    SEDAPExpressMessage.logger.logp(Level.INFO, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Optional field \"number\" is empty!", value);
		} else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.NUMBER_MATCHER, value)) {
		    this.number = Short.parseShort(value, 16);
		} else if (!value.isBlank()) {
		    SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Optional field \"number\" contains invalid value!", value);
		}
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if (value.isEmpty()) {
		    SEDAPExpressMessage.logger.logp(Level.INFO, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Optional field \"time\" is empty!", value);
		} else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.TIME_MATCHER, value)) {
		    this.time = Long.parseLong(value, 16);
		} else if (!value.isBlank()) {
		    SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Optional field \"time\" contains invalid value!", value);
		}
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if (value.isBlank()) {
		    SEDAPExpressMessage.logger.logp(Level.INFO, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Optional field \"sender\" is empty!");
		} else {
		    this.sender = value;
		}
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Incomplete message!");
	    }

	    if (message.hasNext()) {
		String classChar = message.next().trim();
		if (!classChar.isBlank()) {
		    this.classification = Classification.getValueOfClassificationChar(classChar.charAt(0));
		}
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if ("true".equalsIgnoreCase(value)) {
		    this.acknowledgement = Acknowledgement.YES;
		} else if ("false".equalsIgnoreCase(value) || value.isBlank()) {
		    this.acknowledgement = Acknowledgement.NO;
		} else {
		    SEDAPExpressMessage.logger.severe("Optional field \"acknowledgement\" invalid value: \"" + value + "\"");
		}
	    } else if (this instanceof HEARTBEAT) {
		// allowed
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.MAC_MATCHER, value)) {
		    this.mac = value;
		} else if (!value.isBlank()) {
		    SEDAPExpressMessage.logger.logp(Level.INFO, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Optional field \"mac\" contains not a valid 32bit mac number!", value);
		}
	    } else if (this instanceof HEARTBEAT) {
		// allowed
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "SEDAPExpressMessage", "SEDAPExpressMessage(Iterator<String> message)", "Incomplete message!");
	    }

	} catch (Exception e) {
	    e.printStackTrace();

	}
    }

    /*
     *
     */
    protected SEDAPExpressMessage() {
	// abstract, hide constructor
    }

    /**
     * Deserializes a SEDAP-Express message and return the matching concrete
     * implementation object.
     *
     * @param receivedMessage serialized SEDAP-Message
     *
     * @return deserialized SEDAP-Message object
     */
    public static SEDAPExpressMessage deserialize(String receivedMessage) {

	final Iterator<String> messageArray = SEDAPExpressMessage.splitMessage(receivedMessage).iterator();

	if (messageArray.hasNext()) {

	    try {

		Constructor<?> constructor = SEDAPExpressMessage.messageNameToConstructor.get(messageArray.next());

		if (constructor != null) {
		    return (SEDAPExpressMessage) constructor.newInstance(messageArray);
		} else {
		    return null;
		}

	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		SEDAPExpressMessage.logger.throwing("SEDAPExpressMessage", "deserialize(String receivedMessage)", e);
		return null;
	    }

	} else {
	    return null;
	}
    }

    /**
     * Serializes a SEDAP-Express message and return a String object.
     *
     * @param message SEDAP-Message object
     *
     * @return SEDAP-Express message serialized as String object
     */
    public static String serialize(SEDAPExpressMessage message) {
	return message.toString() + '\n';
    }

    protected StringBuilder serializeHeader() {

	StringBuilder result = new StringBuilder(this.getClass().getSimpleName()).append(';');

	if (this.number != null) {
	    result.append(SEDAPExpressMessage.HEXFOMATER.toHexDigits((this.number)).substring(2));
	}
	result.append(';');

	if (this.time != null) {
	    result.append(SEDAPExpressMessage.HEXFOMATER.toHexDigits(this.time));
	}
	result.append(';');

	if (this.sender != null) {
	    result.append(this.sender);
	}
	result.append(';');

	if (this.classification != null) {
	    result.append(this.classification.getValue());
	}
	result.append(';');

	if ((this.acknowledgement != null) && (this.acknowledgement == Acknowledgement.YES)) {
	    result.append(this.acknowledgement);
	}
	result.append(';');

	if (this.mac != null) {
	    result.append(this.mac);
	}

	result.append(';');

	return result;
    }

    /**
     * Remove useless semicolons
     *
     * @param message Original message
     * @return Shortend message
     */
    protected static String removeSemicolons(String message) {
	while (message.endsWith(";")) {
	    message = message.substring(0, message.length() - 1);
	}
	return message;
    }
}
