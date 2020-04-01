package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.RoundingCode;

public interface ChainDAO {
	List<Chain> search(String criteria);
	Chain getChain(String chainCode);
	RoundingCode getRoundingCode(String chainCode);
	boolean isChain(String chainCode);
}