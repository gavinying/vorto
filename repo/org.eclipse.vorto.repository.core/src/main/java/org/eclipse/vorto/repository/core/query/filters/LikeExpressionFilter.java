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

import org.eclipse.vorto.repository.core.query.IQueryFilter;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public class LikeExpressionFilter implements IQueryFilter  {

    public static final String KEY = "org.eclipse.vorto.repository.core.filter.like";

    private String propertyName;

    private Object value;

    public LikeExpressionFilter(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }


    public String getPropertyName() {
        return propertyName;
    }


    public Object getValue() {
        return value;
    }


    @Override
    public String getKey() {
        return KEY;
    }
}
