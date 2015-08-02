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
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.eclipse.vorto.repository.core.ISearchService;
import org.eclipse.vorto.repository.core.model.FileResource;
import org.eclipse.vorto.repository.core.model.FolderResource;
import org.eclipse.vorto.repository.core.model.ProjectResource;
import org.eclipse.vorto.repository.core.model.Resource;
import org.eclipse.vorto.repository.core.model.ResourceType;
import org.eclipse.vorto.repository.core.query.ResourceQuery;
import org.eclipse.vorto.repository.lucene.query.ResourceQueryLucene;
import org.eclipse.vorto.repository.lucene.query.mapper.LuceneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SearchService using lucene and lucene query expression
 * @author Alexander Edelmann
 */
@Service
public class LuceneSearchService implements ISearchService {

	private static final int MAX_RESULT_COUNT = 30;
	
	@Autowired
	private LuceneHelper luceneHelper;
	
	@Autowired
	private IIndexService indexService;
	
	private static final LuceneMapper MAPPER = new LuceneMapper();
	
	private static final Logger log = LoggerFactory
			.getLogger(LuceneSearchService.class);
	

	@Override
	public ResourceQuery createQuery() {
		return new ResourceQueryLucene(MAPPER);
	}

	@Override
	public List<Resource> search(ResourceQuery query) {
		try {
			Query luceneQuery = query.build(Query.class);
			TopDocs hits = luceneHelper.getIndexSearcher().search(luceneQuery, MAX_RESULT_COUNT);
			return convertToSearchResults(hits);
		} catch (IOException e) {
			log.error("Error occured during search", e);
		}

		return Collections.emptyList();
	}

	private List<Resource> convertToSearchResults(TopDocs hits) {

		List<Resource> searchResultsList = new ArrayList<Resource>();
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document document;
			try {
				document = luceneHelper.getIndexSearcher().doc(scoreDoc.doc);
				searchResultsList.add(convertToResource(document));
			} catch (Exception e) {
				throw new RuntimeException(
						"Error while converting lucene result", e);
			}
		}

		return searchResultsList;
	}

	private Resource convertToResource(Document document) {

		String resourceURI = document.get(Resource.PROP_URI);
		String resourceVersion = document.get(Resource.PROP_VERSION);
		String resourceAuthor = document.get(Resource.PROP_AUTHOR);
		String resourceType = document.get(Resource.PROP_TYPE);
		
		Resource resource = null;
		if (ResourceType.valueOf(resourceType) == ResourceType.FileResource) {
			resource = new FileResource();
		} else if (ResourceType.valueOf(resourceType) == ResourceType.FolderResource) {
			resource = new FolderResource(URI.create(document.get(FolderResource.PROP_PARENT)));
		} else {
			resource = new ProjectResource();
		}
		resource.setAuthor(resourceAuthor);
		resource.setURI(URI.create(resourceURI));
		resource.setVersion(resourceVersion);
		
		for (String fieldName : indexService.getAllExtractedFieldNames()) {
			resource.getProperties().put(fieldName, document.get(fieldName));
		}
		
		return resource;
	}

	@Override
	public List<Resource> search(String expression) {
		log.info("Parsing expression '" + expression + "' using Lucene ");

		Set<String> allIndexedFields = indexService.getAllExtractedFieldNames();
		QueryParser queryParser = new MultiFieldQueryParser(allIndexedFields.toArray(new String[allIndexedFields.size()]), new StandardAnalyzer());
		try {
			final Query luceneQuery = queryParser.parse(expression);
			TopDocs hits = luceneHelper.getIndexSearcher().search(luceneQuery, MAX_RESULT_COUNT);
			return convertToSearchResults(hits);
		} catch (IOException e) {
			log.error("Error occured during search", e);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Expression is not a valid lucene query",e);
		}
		return Collections.emptyList();
	}

}
