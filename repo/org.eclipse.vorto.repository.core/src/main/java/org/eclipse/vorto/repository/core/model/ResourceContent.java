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
 * The class ResourceContent provides the content for a resource.
 * @author Alexander Edelmann
 */
public class ResourceContent {

    public byte[] content;
    public URI uri;

    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * content of a resource.
     */
    public byte[] getContent() {
        return content;
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }

    /**
     * uri of a resource.
     */
    public URI getURI() {
        return uri;
    }
}

/* EOF */
