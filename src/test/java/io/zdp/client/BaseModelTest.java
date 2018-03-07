package io.zdp.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import junit.framework.TestCase;

public class BaseModelTest extends TestCase {

	protected final ObjectMapper objectMapper = new ObjectMapper();

	{
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	protected void out(Object o) {
		try {
			System.out.println(objectMapper.writeValueAsString(o));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
