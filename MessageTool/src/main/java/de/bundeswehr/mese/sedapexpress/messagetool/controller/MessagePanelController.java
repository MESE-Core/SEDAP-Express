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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;

public abstract class MessagePanelController {

    // Dimensions
    public static final List<String> dimensionsList = new ArrayList<>(Arrays.asList("Unknown", "Space", "Air", "Surface", "SubSurface", "Ground"));

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
    public static final List<String> identitiesList = new ArrayList<>(Arrays.asList("Pending", "Unknown", "Hostile", "Suspect", "Neutral", "Assumed friend", "Friend"));

    public static char getIdentityCharFromIndex(int index) {
	return switch (index) {
	case 0 -> 'p';
	case 1 -> 'u';
	case 2 -> 'h';
	case 3 -> 's';
	case 4 -> 'n';
	case 5 -> 'a';
	case 6 -> 'f';
	default -> 'p';
	};
    }

    public abstract SEDAPExpressMessage createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac);

    public abstract boolean isValidFilled();
}
