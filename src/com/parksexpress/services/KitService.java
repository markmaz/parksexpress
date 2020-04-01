package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Kit;

public interface KitService {
	List<Kit> search(String criteria);
	Kit getKit(String kitNumber);
}
