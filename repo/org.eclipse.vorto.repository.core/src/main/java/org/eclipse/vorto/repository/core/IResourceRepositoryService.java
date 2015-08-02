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
package org.eclipse.vorto.repository.core;

import java.net.URI;

import org.eclipse.vorto.repository.core.model.ResourceContent;
import org.eclipse.vorto.repository.core.model.UploadHandle;

/**
 * Repo class that provide query access to model, and also retrieves model content
 * 
 * @author Alexander Edelmann
 */
public interface IResourceRepositoryService {

	/**
	 * Get content of a resource.
	 * 
	 * @param fileResource
	 *            the file the content should be got from
	 * 
	 * @return {@link ResourceContent}
	 */
	public ResourceContent getResourceContent(URI resourceUri);

	/**
	 * Upload files and folders.
	 * 
	 * @param handle
	 *            ... the uploadHandles for each resource to be commited
	 */
	public void uploadResource(UploadHandle... handle);
}
