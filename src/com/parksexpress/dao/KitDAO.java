package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Kit;

public interface KitDAO {
	Kit getKit(String kitNumber);
	List<Kit> search(String criteria);
}