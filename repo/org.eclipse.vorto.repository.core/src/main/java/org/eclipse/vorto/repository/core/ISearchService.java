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
package org.eclipse.vorto.repository.core;

import java.util.List;

import org.eclipse.vorto.repository.core.model.Resource;
import org.eclipse.vorto.repository.core.query.ResourceQuery;

/**
 * 
 * @author Alexander Edelmann
 *
 */
public interface ISearchService {

	/**
	 * creates a new resource query builder
	 * @return
	 */
	ResourceQuery createQuery();
	
	/**
	 * Searches resources for the given query builder
	 * @param query
	 * @return
	 */
	List<Resource> search(ResourceQuery query);
	
	/**
	 * Searches resources for the given expression
	 * @param expression
	 * @return
	 */
	List<Resource> search(String expression);

}
