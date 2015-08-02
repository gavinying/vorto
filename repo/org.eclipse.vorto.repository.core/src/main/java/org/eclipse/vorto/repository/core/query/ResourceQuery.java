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
package org.eclipse.vorto.repository.core.query;

import org.eclipse.vorto.repository.core.model.Resource;
import org.eclipse.vorto.repository.core.model.ResourceType;

/**
 * @author Alexander Edelmann
 */
public abstract class ResourceQuery extends AbstractQuery<ResourceQuery>{

	public ResourceQuery uri(String uri) {
		this.filters.add(Criteria.eq(Resource.PROP_URI,uri));
        return this;
	}

	public ResourceQuery nameLike(String name) {
		this.filters.add(Criteria.like(Resource.PROP_URI,name));
        return this;
	}

	public ResourceQuery name(String name) {
		this.filters.add(Criteria.eq(Resource.PROP_NAME,name));
        return this;
	}
	
	public ResourceQuery version(String version) {
		this.filters.add(Criteria.eq(Resource.PROP_VERSION,version));
        return this;
	}

	public ResourceQuery type(ResourceType type) {
		this.filters.add(Criteria.eq(Resource.PROP_TYPE,type));
        return this;
	}


}
