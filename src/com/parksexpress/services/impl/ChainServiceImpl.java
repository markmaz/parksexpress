package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.ChainDAO;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.RoundingCode;
import com.parksexpress.services.ChainService;

public class ChainServiceImpl implements ChainService {
	private ChainDAO chainDAO;
	
	public ChainServiceImpl(){}
	
	public void setChainDAO(final ChainDAO chainDAO){
		this.chainDAO = chainDAO;
	}
	
	public Chain getChain(final String chainCode) {
		return this.chainDAO.getChain(chainCode);
	}

	public List<Chain> search(final String criteria) {
		return this.chainDAO.search(criteria);
	}

	public RoundingCode getRoundingCode(final String chainCode) {
		return this.chainDAO.getRoundingCode(chainCode);
	}

	@Override
	public boolean isChain(String chainCode) {
		return this.chainDAO.isChain(chainCode);
	}

}
