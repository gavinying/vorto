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
package org.eclipse.vorto.repository.infomodel.extractors;

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
 * Extracts model specific data for the indexer
 *
 */
public class ModelResourceExtractor implements IResourceFieldsExtractor {
	
	public static final String MODEL_TYPE = "org.eclipse.vorto.repository.infomodel.type";
	public static final String MODEL_DESCRIPTION = "org.eclipse.vorto.repository.infomodel.description";
	
	private static final Set<String> ALL_KEYS = new HashSet<>();
	
	static {
		ALL_KEYS.add(MODEL_TYPE);
		ALL_KEYS.add(MODEL_DESCRIPTION);
	}

	@Override
	public Map<String, String> extract(Resource resource) {
		Map<String,String> fields = new HashMap<>();
		if (resource.getType() == ResourceType.FileResource) {
			fields.put(MODEL_TYPE, resource.getProperties().get(MODEL_TYPE));
			fields.put(MODEL_DESCRIPTION, resource.getProperties().get(MODEL_DESCRIPTION));
		}
		return fields;
	}

	@Override
	public Set<String> getExtractedFieldNames() {
		return Collections.unmodifiableSet(ALL_KEYS);
	}

}

