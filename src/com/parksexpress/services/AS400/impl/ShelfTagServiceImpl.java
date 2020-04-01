package com.parksexpress.services.AS400.impl;

import com.parksexpress.as400.util.RpgShelfLableRequest;
import com.parksexpress.domain.ShelfTag;
import com.parksexpress.services.ShelfTagService;

public class ShelfTagServiceImpl implements ShelfTagService {
	private RpgShelfLableRequest shelfLabelRequest;
	
	public ShelfTagServiceImpl() {
	}

	@Override
	public void requestShelfTag(ShelfTag shelfTag) throws Exception {
		this.shelfLabelRequest.requestShelfLabel(shelfTag);
	}
	
	public void setShelfLabelRequest(RpgShelfLableRequest shelfLabelRequest){
		this.shelfLabelRequest = shelfLabelRequest;
	}
}
