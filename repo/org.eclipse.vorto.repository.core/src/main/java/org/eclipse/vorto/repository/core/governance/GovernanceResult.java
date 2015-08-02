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

import java.util.List;

import org.eclipse.vorto.repository.core.governance.Message.Severity;
import org.eclipse.vorto.repository.core.model.UploadHandle;

public class GovernanceResult {
	
	private UploadHandle handle;
	
	private List<Message> messages;
	
	public GovernanceResult(UploadHandle handle, List<Message> messages) {
		this.handle = handle;
		this.messages = messages;
	}

	public UploadHandle getHandle() {
		return handle;
	}

	public boolean isApproved() {
		for (Message message : messages) {
			if (message.getSeverity() == Severity.CRITICAL) {
				return false;
			}
		}
		
		return true;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	
 }
