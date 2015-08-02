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
package org.eclipse.vorto.repository.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.vorto.repository.core.dao.IResourceDAO;
import org.eclipse.vorto.repository.core.model.FileResource;
import org.eclipse.vorto.repository.core.model.FolderResource;
import org.eclipse.vorto.repository.core.model.Resource;
import org.eclipse.vorto.repository.core.model.ResourceContent;
import org.springframework.beans.factory.annotation.Required;

/**
 * 
 * File System based implementation of {@link IResourceDAO}
 * 
 * @author Alexander Edelmann
 */
public class ResourceDaoFS implements IResourceDAO {
	
	protected String baseDirectory = null;
	
    protected  static final String META_PROPERTY_FILENAME = ".metadata";
    
    private static final Log log = LogFactory.getLog(ResourceDaoFS.class);

	@Override
	public ResourceContent getResourceContentByURI(URI uri) {
		log.info("Getting content for resource with uri : " + uri);
		
		File file = new File(uri);
		if (file.isDirectory()) {
			throw new IllegalArgumentException("Provided URI is a directory");
		}
		
		ResourceContent result = new ResourceContent();
		try {
			result.setContent(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			log.error("Could not read file content",e);
		}
		result.setURI(uri);
		return result;
	}

	@Override
	public List<Resource> getResources() {
		final List<Resource> result = new ArrayList<Resource>();
		walkDirectoryTree(new ModelFoundHandler() {
			public void handle(Path file) {
				Resource resource = null;
				if (file.toFile().isDirectory()) {
					resource = new FolderResource(file.getParent().toUri());
					resource.setURI(file.toUri());
				} else {
					resource = createFileResourceFromFile(file.toFile());
				}
				result.add(resource);
			}
		});

		return result;
	}
	
	private FileResource createFileResourceFromFile(File file) {
		FileResource resource = new FileResource();
		Properties metadataProperties = loadFileProperties(file);
		resource.setAuthor(readMetadata(metadataProperties,Resource.PROP_AUTHOR));
		resource.setVersion(readMetadata(metadataProperties,Resource.PROP_VERSION));
		resource.getProperties().putAll(readCustomMetadata(metadataProperties));
		resource.setURI(file.toURI());
		return resource;
	}
	
	private String readMetadata(Properties properties, String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	private Map<String,String> readCustomMetadata(Properties properties) {
		Map<String,String> customMetadata = new HashMap<>();
		for (Object key : properties.keySet()) {
			if (!(key instanceof String)) {
				continue;
			}
			String propertyName = (String)key;
			customMetadata.put(propertyName, properties.getProperty(propertyName));
			
		}
		return customMetadata;
	}
	
	private Properties loadFileProperties(File file) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(file, META_PROPERTY_FILENAME)));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		return props;
	}
	
	private interface ModelFoundHandler {
		void handle(Path file);
	}

	private void walkDirectoryTree(final ModelFoundHandler handler) {
		try {
			Files.walkFileTree(Paths.get(getBaseDirectory()),
					new SimpleFileVisitor<Path>() {
						public FileVisitResult visitFile(Path file,
								BasicFileAttributes attrs) throws IOException {
							handler.handle(file);
							return FileVisitResult.CONTINUE;
						}
					});
		} catch (IOException e) {
			throw new RuntimeException("An I/O error was thrown", e);
		}
	}
	
	public String getBaseDirectory() {
		return baseDirectory;
	}
	
	@Required
	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = FilenameUtils.normalize(new File(
				baseDirectory).getAbsolutePath());
	}
	
	@PostConstruct
	public void createProjectsDirectory() {
		File baseDir = new File(baseDirectory);

		if (!baseDir.exists()) {
			baseDir.mkdirs();
			log.info("Base directory " + baseDirectory + " created.");
		}
	}

	@Override
	public List<FileResource> getFileResources() {
		final List<FileResource> result = new ArrayList<FileResource>();
		walkDirectoryTree(new ModelFoundHandler() {
			public void handle(Path file) {
				if (!file.toFile().isDirectory()) {
					result.add(createFileResourceFromFile(file.toFile()));
				}
			}
		});

		return result;
	}

}
