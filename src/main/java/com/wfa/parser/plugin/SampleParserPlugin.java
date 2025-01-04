package com.wfa.parser.plugin;

import com.wfa.parser.spi.ParserPlugin;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wfa.middleware.utils.DataType;
import com.wfa.middleware.utils.Pair;
import com.wfa.parser.spi.IParserPlugin;

@Component
@ParserPlugin(getComponentType = "Sample", getVersion = "0.0.1-SNAPSHOT")
public class SampleParserPlugin implements IParserPlugin {

	@Override
	public Map<String, Pair<Object, DataType>> TokenizeLine() {
		return new HashMap<String, Pair<Object, DataType>>();
	}

	@Override
	public String getComponentName() {
		return "Sample";
	}

	@Override
	public String getComponentSource() {
		return "Sample";
	}
}
