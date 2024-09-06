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

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;

public abstract class MessagePanelController {

    public abstract SEDAPExpressMessage createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac);

    public abstract boolean isValidFilled();
}
