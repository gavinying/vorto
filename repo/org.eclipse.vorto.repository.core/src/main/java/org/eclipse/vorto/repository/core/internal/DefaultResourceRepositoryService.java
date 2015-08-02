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
package org.eclipse.vorto.repository.core.internal;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.vorto.repository.core.IResourceRepositoryService;
import org.eclipse.vorto.repository.core.dao.IResourceDAO;
import org.eclipse.vorto.repository.core.governance.GovernanceException;
import org.eclipse.vorto.repository.core.governance.GovernanceResult;
import org.eclipse.vorto.repository.core.governance.IGovernance;
import org.eclipse.vorto.repository.core.governance.IGovernanceCallback;
import org.eclipse.vorto.repository.core.governance.Message;
import org.eclipse.vorto.repository.core.model.FileUploadHandle;
import org.eclipse.vorto.repository.core.model.ProjectResource;
import org.eclipse.vorto.repository.core.model.ResourceContent;
import org.eclipse.vorto.repository.core.model.UploadHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Alexander Edelmann
 *
 */
@Service
public class DefaultResourceRepositoryService implements IResourceRepositoryService {

	@Autowired(required = false)
	private IGovernance governance = new IGovernance() {
		
		/**
		 * No-Op Governance, always approving uploads
		 */
		@Override
		public void start(UploadHandle handle, IGovernanceCallback callback) {
			callback.processUploadResult(new GovernanceResult(handle, new ArrayList<Message>(0)));
		}
	};
	
	@Autowired
	private IResourceDAO resourceDao;

	@Override
	public ResourceContent getResourceContent(URI resourceURI) {
		return resourceDao.getResourceContentByURI(resourceURI);
	}

	@Override
	public void uploadResource(UploadHandle... handle) {
		for (UploadHandle uploadHandle : handle) {
			if (uploadHandle instanceof FileUploadHandle) {
				governance.start(uploadHandle, new IGovernanceCallback() {
					
					@Override
					public void processUploadResult(GovernanceResult uploadResult) {
						if (uploadResult.isApproved()) {
							// persist
						} else {
							throw new GovernanceException(uploadResult.getMessages());
						}
					}
				});
			}
		}
	}
}
