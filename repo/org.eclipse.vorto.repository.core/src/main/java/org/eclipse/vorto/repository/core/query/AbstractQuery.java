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

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Alexander Edelmann
 *
 * @param <QueryBuilder>
 */
public abstract class AbstractQuery<QueryBuilder> implements IQuery<QueryBuilder> {

    protected Set<IQueryFilter> filters = new HashSet<IQueryFilter>();
    protected String orderByProperty = null;

    @Override
    public IQuery<QueryBuilder> custom(IQueryFilter filter) {
        addFilter(filter);
        return this;
    }


    protected void addFilter(IQueryFilter filter) {
        filters.add(filter);
    }


    @Override
    public IQuery<QueryBuilder> orderBy(String property) {
        this.orderByProperty = property;
        return this;
    }


    @Override
    public Set<IQueryFilter> getFilters() {
        return filters;
    }
    
	@Override
	public IQuery<QueryBuilder> asc() {
		return this;
	}

	@Override
	public IQuery<QueryBuilder> desc() {
		return this;
	}
}
