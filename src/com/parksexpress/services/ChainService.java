package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.RoundingCode;

public interface ChainService {
	List<Chain> search(String criteria);
	Chain getChain(String chainCode);
	RoundingCode getRoundingCode(String chainCode);
	boolean isChain(String chainCode);
}
