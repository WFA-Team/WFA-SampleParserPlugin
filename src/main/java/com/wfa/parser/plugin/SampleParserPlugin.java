package com.wfa.parser.plugin;

import com.wfa.parser.spi.ParserPlugin;
import com.wfa.parser.spi.api.IComponent;
import com.wfa.parser.spi.impl.ComponentBean;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wfa.middleware.utils.beans.data.impl.FileMeta;
import com.wfa.parser.api.IParserEngine;
import com.wfa.parser.spi.IFileCompatibilityEvaluator;
import com.wfa.parser.spi.IFileTokenizer;
import com.wfa.parser.spi.IFileVisitorStub;
import com.wfa.parser.spi.IParserPlugin;

@Component
@ParserPlugin(getId = "SamplePlugin", getComponentTypes = {"Sample", "Smpl"}, getVersion = "0.0.1-SNAPSHOT")
public class SampleParserPlugin implements IParserPlugin {
	private static final String STARTS_WITH_PREFIX = "^";
	private static final String HEADER = "Sample Parser Plugin Logs";
	private boolean canParse = false;
	private final IParserEngine engine;
	private final IFileTokenizer fileTokenizer;

	@Autowired
	public SampleParserPlugin(IParserEngine engine) {
		this.engine = engine;
		this.fileTokenizer = new FileTokenizer();
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

	@Override
	public IFileCompatibilityEvaluator getFileCompatibilityEvaluator() {
		return new IFileCompatibilityEvaluator() {

			@Override
			public boolean canParse(FileMeta file) {
				engine.visitFile(file, new IFileVisitorStub() {

					@Override
					public boolean visitLine(String line) {
						if (line.contains(HEADER)) {
							canParse = true;
							return false;
						}
						return true;
					}
				}, null);
				
				return canParse;
			}
		};
	}

	@Override
	public IFileTokenizer getFileTokenizer() {
		return this.fileTokenizer;
	}
}
