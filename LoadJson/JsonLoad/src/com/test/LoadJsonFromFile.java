package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadJsonFromFile {
	
	public static void main(String args[]) throws IOException {
		File xmlFile = new File("D:\\Tombraider\\Samplejson.txt");
		BufferedReader buffReader = new BufferedReader(new FileReader(xmlFile));
		StringBuilder sb = new StringBuilder();
		String line = buffReader.readLine();
		while(line != null) {
			sb.append(line).append("\n");
			line=  buffReader.readLine();
		}
		buffReader.close();
		Map<String,Object> stdFields = new LinkedHashMap<String, Object>();
		stdFields.put("id",1);
		stdFields.put("insertdate",new Timestamp(System.currentTimeMillis()));
		prepareAndLoadJsonTable(sb.toString(),"Employee", stdFields);
	}
	
	public static void prepareAndLoadJsonTable(String jsonData,String tableName,Map<String,Object> stdFields) {
		
		//Read Json ad Data
		//Some other fields like id etc
		
	    Map<String,Object> jsonMap = parseJson(jsonData);
	    jsonMap.putAll(stdFields);
	    String columnQuery ="select lower(column_name) from user_tab_columns where table_name= :tableName";
		/*
		 * Query query =
		 * HibernateUtil.getCurrentSession().createSQLQuery(columnQuery.toString());
		 * query.setParameter("tableName",tableName); 
		 * List<String> tableColumns = query.list();
		 */
	    List<String> tableColumns = Arrays.asList("id", "insertdate", "name","age","zip","height");
	    

		StringBuilder inserQuery = new StringBuilder("insert into ").append(tableName).append(" (");
		StringBuilder placeHolders = new StringBuilder();
		
		String col = "";
		for(Iterator<String> iter = jsonMap.keySet().iterator();iter.hasNext();) {
			col = iter.next();
			if(tableColumns.contains(col)) {
				inserQuery.append(col);
				placeHolders.append(":"+col);
				
				if(iter.hasNext()) {
					inserQuery.append(",");
					placeHolders.append(",");				
				}
			}
		}
		
		inserQuery.append(") values (").append(placeHolders).append(")");
		System.out.println(inserQuery);
		try {
			
			/*
			 * query =
			 * HibernateUtil.getCurrentSession().createSQLQuery(inserQuery.toString());
			 * for(String key=jsonMap.keySet()) {
			 * query.setParameter("key",jsonMap.get(key)); }
			 * 
			 * query.executeUpdate();
			 */
			
		}catch(Exception e) {
			System.out.println("Something terrible happened");
		}
	}
	
	public static Map<String,Object> parseJson(String json) {
		Map<String,Object> map = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(json, new TypeReference<LinkedHashMap<String,Object>>(){});					
		}catch(Exception e) {
			
		}
		return map;
		
	}

}

