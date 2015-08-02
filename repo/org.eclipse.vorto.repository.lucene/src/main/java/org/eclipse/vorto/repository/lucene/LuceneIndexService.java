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
package org.eclipse.vorto.repository.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.eclipse.vorto.repository.core.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Lucene Index service indexes resources for faster search performance.
 * To determine the fields that are supposed to be indexed, please implement
 * {@link IResourceFieldsExtractor} and register the extractor to this service.
 * 
 * @author Alexander Edelmann
 */
@Service
public class LuceneIndexService implements IIndexService {

	private static final Logger log = LoggerFactory
			.getLogger(LuceneIndexService.class);

	@Autowired
	private LuceneHelper luceneHelper;
	
	private List<IResourceFieldsExtractor> extractors = new ArrayList<>();

	@Value("${repository.index.rebuildOnBoot}")
	private String rebuildOnBoot;

	@Override
	public void index(Resource... resources) {
		for(Resource resource : resources) {
			indexSingle(resource);
		}
	}
	
	private void indexSingle(Resource resource) {
		log.debug("Indexing resource : "
				+ resource);
		IndexWriter indexWriter = luceneHelper.getIndexWriter();
		try {
			if (indexWriter != null) {
				Document doc = createLuceneDocument(resource);
				indexWriter.addDocument(doc);
				indexWriter.commit();
			} else {
				log.warn("Index writer is not yet initiated");
			}
		} catch (IOException e) {
			log.error("Could not index resource", e);
			throw new RuntimeException(e);
		}
	}

	private Document createLuceneDocument(Resource resource) {
		Document document = new Document();
		
		for (IResourceFieldsExtractor extractor : extractors) {
			Map<String,String> indexableFields = extractor.extract(resource);
			for (String key : indexableFields.keySet()) {
				Field field = new StringField(key,indexableFields.get(key),Field.Store.YES);
				document.add(field);
			}
		}
		return document;
	}
	
	public void addFieldExtractor(IResourceFieldsExtractor extractor) {
		this.extractors.add(extractor);
	}

	public List<IResourceFieldsExtractor> getExtractors() {
		return extractors;
	}

	public void setExtractors(List<IResourceFieldsExtractor> extractors) {
		this.extractors = extractors;
	}

	@Override
	public Set<String> getAllExtractedFieldNames() {
		Set<String> extractedFields = new HashSet<>();
		for (IResourceFieldsExtractor extractor : extractors) {
			extractedFields.addAll(extractor.getExtractedFieldNames());
		}
		return extractedFields;
	}
	
	
}