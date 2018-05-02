package com.bosoft.dao;

import java.util.List;
import java.util.Map;

public interface GeneratorMapper {
	Map<String, String> getTableList(String tableName);
	
	List<Map<String, String>> getTableColumns(String tableName);
}
