package com.wfa.parser.plugin;

import com.wfa.parser.spi.ParserPlugin;
import com.wfa.parser.spi.api.IComponent;
import com.wfa.parser.spi.impl.ComponentBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wfa.middleware.utils.DataType;
import com.wfa.middleware.utils.Pair;
import com.wfa.parser.spi.IParserPlugin;

@Component
@ParserPlugin(getId = "SamplePlugin", getComponentTypes = {"Sample", "Smpl"}, getVersion = "0.0.1-SNAPSHOT")
public class SampleParserPlugin implements IParserPlugin {
	private static final String STARTS_WITH_PREFIX = "^";

	@Override
	public Map<String, Pair<Object, DataType>> TokenizeLine() {
		return new HashMap<String, Pair<Object, DataType>>();
	}

	@Override
	public String[] getFileNameRegexes() {
		List<String> regexCollection = new ArrayList<String>();
		for (int i = 0; i < getComponents().length ; i++) {
			regexCollection.add(STARTS_WITH_PREFIX + getComponents()[i].getComponentName());
		}
		
		return regexCollection.stream().toArray(String[]::new);
	}

	@Override
	public IComponent[] getComponents() {
		IComponent[] comps = new IComponent[2];
		comps[0] = new ComponentBean("Sample", "Sample", "Sample");
		comps[1] = new ComponentBean("Smple", "Smple", "Smple");
		return comps;
	}
}
