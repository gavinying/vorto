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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * The abstract class resource can either be a {@link ProjectResource},
 * {@link FolderResource} or a {@link FileResource}.
 * 
 * @author Alexander Edelmann
 */
public abstract class Resource {
	
	public static final String PROP_URI = "org.eclipse.vorto.repository.core.query.property.uri";
	public static final String PROP_NAME = "org.eclipse.vorto.repository.core.query.property.name";
	public static final String PROP_AUTHOR = "org.eclipse.vorto.repository.core.query.property.author";
	public static final String PROP_VERSION = "org.eclipse.vorto.repository.core.query.property.version";
	public static final String PROP_TYPE = "org.eclipse.vorto.repository.core.query.property.type";
	
    private URI uri;
    private String name;
	private Date creationDate;
    private String author;
    private Date lastModified;
    private String version;
    private Map<String,String> properties = new HashMap<String, String>();

    public void setURI(URI uri) {
        this.uri = uri;
    }

    /**
     * uri of a resource.
     */
    public URI getURI() {
        return uri;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Date a resource was created at.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Author of a resource.
     */
    public String getAuthor() {
        return author;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Date a resource was last modified at.
     */
    public Date getLastModified() {
        return lastModified;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Version of a resource.
     */
    public String getVersion() {
        return version;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Returns the type of the resource.
     */
    public abstract ResourceType getType();
}

/* EOF */
