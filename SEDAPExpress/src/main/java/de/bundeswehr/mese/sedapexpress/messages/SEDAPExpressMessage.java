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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

	ACKNOWLEDGE("ACKNOWLEDGE"),
	COMMAND("COMMAND"),
	CONTACT("CONTACT"),
	EMISSION("EMISSION"),
	GENERIC("GENERIC"),
	GRAPHIC("GRAPHIC"),
	HEARTBEAT("HEARTBEAT"),
	KEYEXCHANGE("KEYEXCHANGE"),
	METEO("METEO"),
	OWNUNIT("OWNUNIT"),
	RESEND("RESEND"),
	STATUS("STATUS"),
	TEXT("TEXT"),;

	private static final Map<String, MessageType> types = new HashMap<>();

	static {
	    for (MessageType e : MessageType.values()) {
		MessageType.types.put(e.type, e);
	    }
	}

	public static MessageType valueOfType(String type) {
	    return MessageType.types.get(type);
	}

	public final String type;

	private MessageType(String type) {
	    this.type = type;
	}
    }

    public static final String ENCODING_BASE64 = "BASE64";
    public static final String ENCODING_NONE = "NONE";

    public static final char NONE = '-';
    public static final char PUBLIC = 'P';
    public static final char UNCLAS = 'U';
    public static final char RESTRICTED = 'R';
    public static final char CONFIDENTIAL = 'C';
    public static final char SECRET = 'S';
    public static final char TOP_SECRET = 'T';

    public static final char NATO_RESTRICTED = SEDAPExpressMessage.RESTRICTED;
    public static final char NATO_CONFIDENTIAL = SEDAPExpressMessage.CONFIDENTIAL;
    public static final char NATO_SECRET = SEDAPExpressMessage.SECRET;
    public static final char COSMIC_TOP_SECRET = SEDAPExpressMessage.TOP_SECRET;

    public static final char EU_RESTRICTED = SEDAPExpressMessage.RESTRICTED;
    public static final char EU_CONFIDENTIAL = SEDAPExpressMessage.CONFIDENTIAL;
    public static final char EU_SECRET = SEDAPExpressMessage.SECRET;
    public static final char EU_TOP_SECRET = SEDAPExpressMessage.TOP_SECRET;

    public static final char RESTREINT_UE = SEDAPExpressMessage.RESTRICTED;
    public static final char CONFIDENTIEL_UE = SEDAPExpressMessage.CONFIDENTIAL;
    public static final char SECRET_UE = SEDAPExpressMessage.SECRET;
    public static final char TRES_SECRET_UE = SEDAPExpressMessage.TOP_SECRET;

    public static final char KEINE = SEDAPExpressMessage.NONE;
    public static final char OEFFENTLICH = SEDAPExpressMessage.PUBLIC;
    public static final char OFFEN = SEDAPExpressMessage.UNCLAS;
    public static final char VS_NFD = SEDAPExpressMessage.RESTRICTED;
    public static final char VS_VERTRAULICH = SEDAPExpressMessage.CONFIDENTIAL;
    public static final char GEHEIM = SEDAPExpressMessage.SECRET;
    public static final char STRENG_GEHEIM = SEDAPExpressMessage.TOP_SECRET;

    public static final boolean ACKNOWLEDGE_YES = true;
    public static final boolean ACKNOWLEDGE_NO = false;

    public static final HexFormat HEXFOMATER = HexFormat.of().withUpperCase();

    public static final Pattern NAME_MATCHER = Pattern.compile("^[a-zA-Z]+$"); // Name
    public static final Pattern NUMBER_MATCHER = Pattern.compile("^[A-Fa-f0-9]{1,2}$"); // Number
    public static final Pattern TIME_MATCHER = Pattern.compile("^[A-Fa-f0-9]{8,16}$"); // Time
    public static final Pattern SENDER_MATCHER = Pattern.compile("^[A-Fa-f0-9]{1,4}$"); // Sender-Recipient
    public static final Pattern TEXTTYPE_MATCHER = Pattern.compile("^[0-4]$"); // Text type

    public static final Pattern DOUBLE_MATCHER = Pattern.compile("^-?\\d+.?\\d*$"); // Double
    public static final Pattern POSITIVE_DOUBLE_MATCHER = Pattern.compile("^\\d+.?\\d*$"); // Double
    public static final Pattern DOUBLE_LIST_MATCHER = Pattern.compile("^[\\d.?\\d?#]*$"); // Double
    public static final Pattern INTEGER_MATCHER = Pattern.compile("^-?\\d+$"); // Integer
    public static final Pattern BIGINTEGER_MATCHER = Pattern.compile("^([A-Fa-f0-9][A-Fa-f0-9])+$"); // Hexadecimal BigInteger

    public static final Pattern BEARING_MATCHER = Pattern.compile("^(\\d+\\.?\\d*|[012]\\d\\d\\.\\d*|3[0-5]\\d\\\\.\\d*)$"); // 000.00-359.999
    public static final Pattern SIDC_MATCHER = Pattern.compile("^[a-zA-Z-]{15}$"); // SIDC
    public static final Pattern MMSI_MATCHER = SEDAPExpressMessage.INTEGER_MATCHER; // Integer
    public static final Pattern ICAO_MATCHER = Pattern.compile("^[A-Z]{1}[A-Z0-9]{1,3}$"); // ICAO
    public static final Pattern SOURCE_MATCHER = Pattern.compile("^[R,A,I,S,E,O,Y,M]+$"); // Source type
    public static final Pattern CMDTYPE_MATCHER = Pattern.compile("^[0-255]$"); // Command type
    public static final Pattern GRAPHICTYPE_MATCHER = Pattern.compile("^[0-14]$"); // Graphic type
    public static final Pattern RGBA_MATCHER = Pattern.compile("^[0-9A-F]{8}$"); // RGBA Format´
    public static final Pattern TECSTATUS_MATCHER = Pattern.compile("^[0-5]$"); // TecStatus
    public static final Pattern OPSSTATUS_MATCHER = Pattern.compile("^[0-4]$"); // OpsStatus
    public static final Pattern PERCENT_MATCHER = Pattern.compile("^(100(\\.0+)?|(\\d{1,2})(\\.\\d+)?)$"); // Percent
    public static final Pattern ENCODING_MATCHER = Pattern.compile("^[BASE64]|[ASCII]|[BINARY]$");

    public static boolean matchesPattern(Pattern pattern, String value) {
	return pattern.matcher(value).matches();
    }

    private Byte number;

    private Long time;

    private String sender;

    private Character classification;

    private Boolean acknowledgement;

    private String mac;

    public static HexFormat formatter = HexFormat.of().withUpperCase();

    public Byte getNumber() {
	return this.number;
    }

    public void setNumber(Byte number) {
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

    public Character getClassification() {
	return this.classification;
    }

    public void setClassification(Character classification) {
	this.classification = classification;
    }

    public Boolean getAcknowledgement() {
	return this.acknowledgement;
    }

    public void setAcknowledgement(Boolean acknowledgement) {
	this.acknowledgement = acknowledgement;
    }

    public String getMAC() {
	return this.mac;
    }

    public void setMAC(String mac) {
	this.mac = mac;
    }

    public MessageType getMessageType() {
	return MessageType.valueOfType(this.getClass().getSimpleName());
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
	case 0 -> SEDAPExpressMessage.PUBLIC;
	case 1 -> SEDAPExpressMessage.UNCLAS;
	case 2 -> SEDAPExpressMessage.RESTRICTED;
	case 3 -> SEDAPExpressMessage.CONFIDENTIAL;
	case 4 -> SEDAPExpressMessage.SECRET;
	case 5 -> SEDAPExpressMessage.TOP_SECRET;
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

	return switch (classification1) {
	case NONE, PUBLIC -> classification2;
	case UNCLAS -> switch (classification2) {
	case PUBLIC -> classification1;
	default -> classification2;
	};
	case RESTRICTED -> switch (classification2) {
	case PUBLIC, UNCLAS -> classification1;
	default -> classification2;
	};
	case CONFIDENTIAL -> switch (classification2) {
	case PUBLIC, UNCLAS, RESTRICTED -> classification1;
	default -> classification2;
	};
	case SECRET -> switch (classification2) {
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

	return switch (classification1) {
	case PUBLIC -> classification1;
	case UNCLAS -> switch (classification2) {
	case PUBLIC -> classification2;
	default -> classification1;
	};
	case RESTRICTED -> switch (classification2) {
	case PUBLIC, UNCLAS -> classification2;
	default -> classification1;
	};
	case CONFIDENTIAL -> switch (classification2) {
	case PUBLIC, UNCLAS, RESTRICTED -> classification2;
	default -> classification1;
	};
	case SECRET -> switch (classification2) {
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

	return switch (classificationFullName
		.trim()
		.toUpperCase()
		.replace("NATO", "")
		.replace("EU", "")
		.replace(
			 " ",
			 "")) {
	case "OEFFENTLICH", "PUBLIC" -> SEDAPExpressMessage.PUBLIC;
	case "OFFEN", "UNCLAS" -> SEDAPExpressMessage.UNCLAS;
	case "VS-NFD", "RESTRICTED" -> SEDAPExpressMessage.RESTRICTED;
	case "VS-VERTRAULICH", "CONFIDENTIAL" -> SEDAPExpressMessage.CONFIDENTIAL;
	case "GEHEIM", "VS-GEHEIM", "SECRET" -> SEDAPExpressMessage.SECRET;
	case "STRENGGEHEIM", "VS-STRENGGEHEIM", "TOP SECRET" -> SEDAPExpressMessage.TOP_SECRET;
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

	return switch (classification) {
	case NONE -> false;
	case PUBLIC -> true; // Always permitted
	case UNCLAS ->
	    (maxClassification == SEDAPExpressMessage.UNCLAS) || (maxClassification == SEDAPExpressMessage.RESTRICTED)
		    || (maxClassification == SEDAPExpressMessage.CONFIDENTIAL)
		    || (maxClassification == SEDAPExpressMessage.SECRET)
		    || (maxClassification == SEDAPExpressMessage.TOP_SECRET);
	case RESTRICTED -> (maxClassification == SEDAPExpressMessage.RESTRICTED)
		|| (maxClassification == SEDAPExpressMessage.CONFIDENTIAL)
		|| (maxClassification == SEDAPExpressMessage.SECRET)
		|| (maxClassification == SEDAPExpressMessage.TOP_SECRET);
	case CONFIDENTIAL ->
	    (maxClassification == SEDAPExpressMessage.CONFIDENTIAL) || (maxClassification == SEDAPExpressMessage.SECRET)
		    || (maxClassification == SEDAPExpressMessage.TOP_SECRET);
	case SECRET ->
	    (maxClassification == SEDAPExpressMessage.SECRET) || (maxClassification == SEDAPExpressMessage.TOP_SECRET);
	case TOP_SECRET -> maxClassification == SEDAPExpressMessage.TOP_SECRET;
	default -> throw new IllegalArgumentException("Unexpected value: " + classification);
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

	Arrays
		.asList(
			OWNUNIT.class,
			CONTACT.class,
			EMISSION.class,
			METEO.class,
			TEXT.class,
			COMMAND.class,
			GRAPHIC.class,
			STATUS.class,
			ACKNOWLEDGE.class,
			RESEND.class,
			HEARTBEAT.class,
			GENERIC.class)
		.forEach(clazz -> {
		    try {
			SEDAPExpressMessage.messageNameToConstructor
				.put(
				     clazz.getSimpleName(),
				     clazz.getConstructor(paramTypes));
		    } catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		    }
		});
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
    protected SEDAPExpressMessage(Byte number, Long time, String sender, Character classification,
	    Boolean acknowledgement, String mac) {
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
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.INFO,
				  "SEDAPExpressMessage",
				  "SEDAPExpressMessage(Iterator<String> message)",
				  "Optional field \"number\" is empty!",
				  value);
		} else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.NUMBER_MATCHER, value)) {
		    this.number = Byte.parseByte(value, 16);
		} else if (!value.isBlank()) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.SEVERE,
				  "SEDAPExpressMessage",
				  "SEDAPExpressMessage(Iterator<String> message)",
				  "Optional field \"number\" contains invalid value!",
				  value);
		}
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if (value.isEmpty()) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.INFO,
				  "SEDAPExpressMessage",
				  "SEDAPExpressMessage(Iterator<String> message)",
				  "Optional field \"time\" is empty!",
				  value);
		} else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.TIME_MATCHER, value)) {
		    this.time = Long.parseLong(value, 16);
		} else if (!value.isBlank()) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.SEVERE,
				  "SEDAPExpressMessage",
				  "SEDAPExpressMessage(Iterator<String> message)",
				  "Optional field \"time\" contains invalid value!",
				  value);
		}
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.SENDER_MATCHER, value)) {
		    this.sender = String.valueOf(Integer.parseInt(value, 16));
		} else if (!value.isBlank()) {
		    this.sender = value;
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.INFO,
				  "SEDAPExpressMessage",
				  "SEDAPExpressMessage(Iterator<String> message)",
				  "Optional field \"sender\" contains not a valid number, but free text is allowed!",
				  value);
		}
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Incomplete message!");
	    }

	    if (message.hasNext()) {
		String classChar = message.next().trim();
		if (!classChar.isBlank()) {
		    this.classification = classChar.charAt(0);
		}
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if ("true".equalsIgnoreCase(value)) {
		    this.acknowledgement = Boolean.TRUE;
		} else if ("false".equalsIgnoreCase(value) || value.isBlank()) {
		    this.acknowledgement = Boolean.FALSE;
		} else {
		    SEDAPExpressMessage.logger.severe("Optional field \"acknowledgement\" invalid value: \"" + value + "\"");
		}
	    } else if (this instanceof HEARTBEAT) {
		// allowed
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Incomplete message!");
	    }

	    if (message.hasNext()) {
		value = message.next();
		if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.SENDER_MATCHER, value)) {
		    this.mac = value;
		} else if (!value.isBlank()) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.INFO,
				  "SEDAPExpressMessage",
				  "SEDAPExpressMessage(Iterator<String> message)",
				  "Optional field \"mac\" contains not a valid number, but free text is allowed!",
				  value);
		}
	    } else if (this instanceof HEARTBEAT) {
		// allowed
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Incomplete message!");
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

	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
		    | InvocationTargetException e) {
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
	    result.append(SEDAPExpressMessage.HEXFOMATER.toHexDigits((this.number)));
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
	    result.append(this.classification);
	}
	result.append(';');

	if ((this.acknowledgement != null) && this.acknowledgement.booleanValue()) {
	    result.append(this.acknowledgement);
	}
	result.append(';');

	if (this.mac != null) {
	    result.append(this.mac);
	}

	result.append(';');

	return result;
    }

}
