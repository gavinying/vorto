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

import org.eclipse.vorto.repository.core.query.filters.LikeExpressionFilter;
import org.eclipse.vorto.repository.core.query.filters.LogicalExpressionFilter;
import org.eclipse.vorto.repository.core.query.filters.NotExpressionFilter;
import org.eclipse.vorto.repository.core.query.filters.SimpleExpressionFilter;


/**
 * Factory for creating search criteria
 * 
 * @author Alexander Edelmann
 */
public class Criteria {

    public static IQueryFilter eq(String property, Object value) {
        return new SimpleExpressionFilter(property,value,"=");
    }

    public static IQueryFilter greaterThan(String property, Object value) {
        return new SimpleExpressionFilter(property, value, ">");
    }

    public static IQueryFilter lessThan(String property, Object value) {
        return new SimpleExpressionFilter(property, value, "<");
    }

    public static IQueryFilter like(String property, Object value) {
        return new LikeExpressionFilter(property,value);
    }

    public static IQueryFilter contains(String property, Object value) {
        return new SimpleExpressionFilter(property,value,"contains");
    }

    public static IQueryFilter not(String property, Object value) {
        return new NotExpressionFilter(eq(property,value));
    }

    public static IQueryFilter not(IQueryFilter filter) {
        return new NotExpressionFilter(filter);
    }

    public static IQueryFilter or(IQueryFilter... expressions) {
        return new LogicalExpressionFilter("or",expressions);
    }

}
