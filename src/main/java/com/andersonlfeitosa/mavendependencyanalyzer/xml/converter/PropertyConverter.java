package com.andersonlfeitosa.mavendependencyanalyzer.xml.converter;

import java.util.HashMap;
import java.util.Map;

import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Property;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PropertyConverter implements Converter {

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return type.equals(HashMap.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		// not used
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map<String, Property> properties = new HashMap<String, Property>();
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			properties.put(reader.getNodeName(), 
					new Property(reader.getNodeName(), reader.getValue()));
			reader.moveUp();
		}
		
		return properties;
	}


}
