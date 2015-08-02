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

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.eclipse.vorto.repository.core.query.IQueryFilter;
import org.eclipse.vorto.repository.core.query.filters.LogicalExpressionFilter;
import org.eclipse.vorto.repository.core.query.mapper.QueryFilterMapper;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class LogicalExpressionMapper implements QueryFilterMapper<Query> {

	private QueryFilterMapper<Query> filterMapper = null;

    public LogicalExpressionMapper(QueryFilterMapper<Query> filterMapper) {
        this.filterMapper = filterMapper;
    }
	
	@Override
	public Query map(IQueryFilter filter) {
		LogicalExpressionFilter logicalExpressionFilter = (LogicalExpressionFilter)filter;
		BooleanQuery logicalQuery = new BooleanQuery();
        if (logicalExpressionFilter.getOp().equalsIgnoreCase("or")) {
        	for (Query query : getMappedFilters(logicalExpressionFilter.getFilters())) {
        		logicalQuery.add(query, Occur.SHOULD);
        	}
        } else if (logicalExpressionFilter.getOp().equalsIgnoreCase("and")) {
        	for (Query query : getMappedFilters(logicalExpressionFilter.getFilters())) {
        		logicalQuery.add(query, Occur.MUST);
        	}
        } else {
            throw new UnsupportedOperationException("Operator must either be 'or' or 'and'");
        }
        
        return logicalQuery;
	}
	
	
	private Query[] getMappedFilters(List<IQueryFilter> filters) {
        List<Query> mappedFilters = new ArrayList<Query>();

        for (IQueryFilter filter : filters) {
            mappedFilters.add(filterMapper.map(filter));
        }

        return mappedFilters.toArray(new Query[mappedFilters.size()]);
    }
}
