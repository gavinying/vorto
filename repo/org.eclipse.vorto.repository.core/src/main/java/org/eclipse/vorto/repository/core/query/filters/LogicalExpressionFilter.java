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
package org.eclipse.vorto.repository.core.query.filters;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.vorto.repository.core.query.IQueryFilter;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class LogicalExpressionFilter implements IQueryFilter {

    public static final String KEY = "org.eclipse.vorto.repository.core.filter.logical";

    private IQueryFilter[] filters;

    private String op;

    public LogicalExpressionFilter(String op, IQueryFilter... expressions) {
        if (expressions.length < 2) {
            throw new IllegalArgumentException("Must be at least 2 expressions");
        }
        this.filters = expressions;
        this.op = op;
    }

    @Override
    public String getKey() {
        return KEY;
    }


    public List<IQueryFilter> getFilters() {
        return Collections.unmodifiableList(Arrays.asList(filters));
    }


    public String getOp() {
        return op;
    }
}
