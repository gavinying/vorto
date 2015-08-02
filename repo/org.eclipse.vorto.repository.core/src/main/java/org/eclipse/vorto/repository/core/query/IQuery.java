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

import java.util.Set;


/**
 * Represents a query for various domain objects
 * 
 * @author Alexander Edelmann
 */
public interface IQuery<QueryBuilder> {


    /**
     * creates a new custom query with the specified filter
     *
     * @param filter
     *         custom query filter
     * @return new query
     */
    public IQuery<QueryBuilder> custom(IQueryFilter filter);


    /**
     * orders the resultset by the specified property
     *
     * @param property
     *         to order by
     * @return
     */
    public IQuery<QueryBuilder> orderBy(String property);


    /**
     * orders the resultset ascending
     *
     * @return
     */
    public IQuery<QueryBuilder> asc();


    /**
     * orders the resultset descending
     *
     * @return
     */
    public IQuery<QueryBuilder> desc();


    /**
     * @return
     */
    Set<IQueryFilter> getFilters();

    /**
     * builds the actual query object from this query
     * @param queryObjectClass
     * @return
     */
    <QueryObject> QueryObject build(Class<QueryObject> queryObjectClass);
}
