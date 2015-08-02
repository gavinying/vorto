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
package org.eclipse.vorto.repository.lucene.query;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.eclipse.vorto.repository.core.query.IQueryFilter;
import org.eclipse.vorto.repository.core.query.ResourceQuery;
import org.eclipse.vorto.repository.core.query.filters.LogicalExpressionFilter;
import org.eclipse.vorto.repository.lucene.query.mapper.LuceneMapper;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class ResourceQueryLucene extends ResourceQuery {
	
	private LuceneMapper luceneMapper = null;
	
	public ResourceQueryLucene(LuceneMapper luceneMapper) {
		this.luceneMapper = luceneMapper;
	}

	@SuppressWarnings("unchecked")
	public <QueryObject> QueryObject build(Class<QueryObject> clazz) {
		Query luceneQuery = null;
		if (getFilters().isEmpty()) {
			luceneQuery = new MatchAllDocsQuery();
		} else {
			LogicalExpressionFilter andFilter = new LogicalExpressionFilter("and", getFilters().toArray(new IQueryFilter[getFilters().size()]));
			luceneQuery = luceneMapper.map(andFilter);
		}
		
		return (QueryObject)luceneQuery;
	}

}
