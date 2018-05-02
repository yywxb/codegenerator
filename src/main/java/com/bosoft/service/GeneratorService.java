package com.bosoft.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosoft.dao.GeneratorMapper;

@Service
public class GeneratorService {

	@Autowired
	private GeneratorMapper generatorMapper;

	public Map<String, String> queryTable(String tableName) {
		return generatorMapper.getTableList(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return generatorMapper.getTableColumns(tableName);
	}

	public String generatorCode(String tableName, String part, String moduleName, String tablePrefix,
			String tableNameCN) {
		Map<String, String> table = queryTable(tableName);
		if (table == null || !table.containsKey("tableName")) {
			return "Bad table name " + tableName;
		}
		List<Map<String, String>> columns = queryColumns(tableName);
		try {
			return GenUtils.generatorCode(table, columns, part, moduleName, tablePrefix, tableNameCN);
		} catch (Exception ex) {
			return ex.toString();
		}
	}
}
