package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.SearchResult;

public interface StoreChainDAO {
	List<SearchResult> search(String criteria);
}
