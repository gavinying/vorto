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
package org.eclipse.vorto.repository.lucene.extractors;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.vorto.repository.core.model.Resource;
import org.eclipse.vorto.repository.core.model.ResourceType;
import org.eclipse.vorto.repository.lucene.IResourceFieldsExtractor;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class FileResourceExtractor implements IResourceFieldsExtractor {
	
	private static final Set<String> ALL_KEYS = new HashSet<>();
	
	static {
		ALL_KEYS.add(Resource.PROP_URI);
		ALL_KEYS.add(Resource.PROP_NAME);
		ALL_KEYS.add(Resource.PROP_AUTHOR);
		ALL_KEYS.add(Resource.PROP_VERSION);
	}

	@Override
	public Map<String, String> extract(Resource resource) {
		Map<String,String> fields = new HashMap<>();
		if (resource.getType() == ResourceType.FileResource) {
			fields.put(Resource.PROP_URI, resource.getURI().toString());
			fields.put(Resource.PROP_NAME, resource.getName());
			fields.put(Resource.PROP_AUTHOR, resource.getAuthor());
			fields.put(Resource.PROP_VERSION, resource.getVersion());
		}
		return fields;
	}

	@Override
	public Set<String> getExtractedFieldNames() {
		return Collections.unmodifiableSet(ALL_KEYS);
	}

}
