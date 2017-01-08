package com.whiletrue.xproj.xstock.xdata.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataFrameVo {

	private ObjectMapper mapper = new ObjectMapper();

	private List<List<String>> oriDataList = null;

	private List<Map<String, String>> df = new ArrayList<Map<String, String>>();
	
	private String[] keyArr = null;

	public DataFrameVo(String json, List<String> keyList)
			throws JsonParseException, JsonMappingException, IOException {

		this(json, keyList.toArray(new String[] {}));
	}

	public DataFrameVo(String json, String[] keyArr) throws JsonParseException,
			JsonMappingException, IOException {

		TypeReference<List<List<String>>> tRef = new TypeReference<List<List<String>>>() {
		};
		this.keyArr = keyArr;
		this.oriDataList = mapper.readValue(json, tRef);

		for (List<String> list : oriDataList) {
			int i = 0;
			Map<String, String> tMap = new HashMap<String, String>();
			for (String key : this.keyArr) {
				tMap.put(key, list.get(i++));
			}
			this.df.add(tMap);
		}

	}
	
	public List<Map<String, String>> getDf() {
		return df;
	}

	public Map<String, String> getRow(int index) {

		return df.get(index);
	}

	public String getValue(int index, String key) {
		return df.get(index).get(key);
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("DataFrameVo :\n");
		if(this.keyArr != null){
			sb.append(StringUtils.join(this.keyArr, "\t"));
		}
		sb.append("\n");
		if(this.df.size() > 0){
			for(Map<String, String> map : this.df){
				for(String str: this.keyArr){
					sb.append(map.get(str)).append("\t");
				}
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}
	

}
