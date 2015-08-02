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
package org.eclipse.vorto.repository.core.validation.validators;

import org.eclipse.vorto.repository.core.model.FileUploadHandle;
import org.eclipse.vorto.repository.core.validation.IResourceValidator;
import org.eclipse.vorto.repository.core.validation.ValidationException;

/**
 * Validates if an upload contains actual data to work with
 * 
 * @author Alexander Edelmann
 *
 */
public class HasContentValidation implements IResourceValidator {

	@Override
	public void validate(FileUploadHandle uploadHandle) throws ValidationException {
		if (uploadHandle.getContent() == null || uploadHandle.getContent().length == 0) {
			throw new ValidationException("Uploaded content does not contain any data!");
		}
	}

}
