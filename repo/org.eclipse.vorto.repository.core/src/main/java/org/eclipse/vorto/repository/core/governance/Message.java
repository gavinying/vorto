/*******************************************************************************
 * Copyright (c) 2015 Bosch Software Innovations GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *   
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * The Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *   
 * Contributors:
 * Bosch Software Innovations GmbH - Please refer to git log
 *******************************************************************************/
package org.eclipse.vorto.repository.core.governance;

/**
 * @author Alexander Edelmann
 *
 */
public class Message {

	public enum Severity {
		INFO, WARNING, CRITICAL
	}
	
	private Severity severity;
	
	private String comment;
	
	private String author;
	
	public Message(String comment, String author, Severity severity) {
		this.severity = severity;
		this.comment = comment;
		this.author = author;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getComment() {
		return comment;
	}

	public String getAuthor() {
		return author;
	}
	
	
}
