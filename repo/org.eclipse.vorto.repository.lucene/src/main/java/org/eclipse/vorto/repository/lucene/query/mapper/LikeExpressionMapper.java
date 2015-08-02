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

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.eclipse.vorto.repository.core.query.IQueryFilter;
import org.eclipse.vorto.repository.core.query.filters.LikeExpressionFilter;
import org.eclipse.vorto.repository.core.query.mapper.QueryFilterMapper;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class LikeExpressionMapper implements QueryFilterMapper<Query> {

	@Override
	public Query map(IQueryFilter filter) {
		LikeExpressionFilter lef = (LikeExpressionFilter)filter;
		return new WildcardQuery(new Term(lef.getPropertyName(), "*" + lef.getValue().toString() + "*"));
	}

}
