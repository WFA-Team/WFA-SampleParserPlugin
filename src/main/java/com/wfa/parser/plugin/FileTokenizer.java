package com.wfa.parser.plugin;

import java.util.Map;

import com.wfa.crosstalk.core.model.Constants;
import com.wfa.middleware.utils.DataType;
import com.wfa.middleware.utils.Pair;
import com.wfa.parser.spi.IFileTokenizer;

public class FileTokenizer implements IFileTokenizer{

	private static final String HEADER = "Sample Parser Plugin Logs";
	private static final String PREFIX = "Sample:";
	private static final String TOPIC = "Sample";
	private static final String COMMA = ",";
	
	@Override
	public boolean tokenizeLine(String line, Map<String, Pair<Object, DataType>> tokenizedLine) {
		if (line.contains(HEADER)) 
			return true; // return true but no publish
		else if (line.startsWith(PREFIX)) {
			String kv = line.substring(PREFIX.length()).strip();
			String[] keyValueArr = kv.split(COMMA);
			if (keyValueArr.length % 2 != 0) {
				return false;
			}
			
			tokenizedLine.put(Constants.TOPIC, new Pair<Object, DataType>(TOPIC, DataType.STRING));
			
			for (int i = 0; i < keyValueArr.length; i = i + 2) {
				String key = keyValueArr[i].strip();
				String value = keyValueArr[i + 1].strip();
				
				try {
					int intVal = Integer.parseInt(value);
					tokenizedLine.put(key, new Pair<Object, DataType>(intVal, DataType.INTEGER));
				} catch(NumberFormatException e1) {
					try {
						double realVal = Double.parseDouble(value);
						tokenizedLine.put(key, new Pair<Object, DataType>(realVal, DataType.REAL));
					} catch(NumberFormatException e2) {
						tokenizedLine.put(key, new Pair<Object, DataType>(value, DataType.STRING));
					}
				}
			}	
			return true;
		}
		return false;	
	}
}
