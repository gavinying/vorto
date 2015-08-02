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
package org.eclipse.vorto.repository.core.model;

import java.net.URI;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class FolderResource extends Resource {

    public static final String PROP_PARENT = "org.eclipse.vorto.repository.core.query.property.parent";;
	private URI parent;

    public FolderResource(URI parentURI) {
    	this.parent = parentURI;
    }
    
    /**
     * Parent of a FolderResource.
     */
    public URI getParent() {
        return parent;
    }

    /**
     * Returns the type of the resource.
     */
    @Override public ResourceType getType() {
        return ResourceType.FolderResource;
    }
}
