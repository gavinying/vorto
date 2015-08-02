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
package org.eclipse.vorto.repository.lucene.query.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.eclipse.vorto.repository.core.query.IQueryFilter;
import org.eclipse.vorto.repository.core.query.filters.LikeExpressionFilter;
import org.eclipse.vorto.repository.core.query.filters.LogicalExpressionFilter;
import org.eclipse.vorto.repository.core.query.filters.SimpleExpressionFilter;
import org.eclipse.vorto.repository.core.query.mapper.QueryFilterMapper;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class LuceneMapper implements QueryFilterMapper<Query> {

	private static final Map<String, QueryFilterMapper<Query>> mappers = new HashMap<String, QueryFilterMapper<Query>>();

	private static final LuceneMapper LUCENE_MAPPER = new LuceneMapper();

	static {
		mappers.put(SimpleExpressionFilter.KEY, new SimpleExpressionMapper());
		mappers.put(LikeExpressionFilter.KEY, new LikeExpressionMapper());
		mappers.put(LogicalExpressionFilter.KEY, new LogicalExpressionMapper(LUCENE_MAPPER));
	}

	@Override
	public Query map(IQueryFilter filter) {

		QueryFilterMapper<Query> mapper = mappers.get(filter.getKey());

		if (mapper == null) {
			throw new IllegalArgumentException("No filter query mapper found for filter key " + filter.getKey());
		}

		return mapper.map(filter);
	}

}
