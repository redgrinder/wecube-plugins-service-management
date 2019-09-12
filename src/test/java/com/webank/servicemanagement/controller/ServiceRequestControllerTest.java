package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.webank.servicemanagement.mock.MockCoreServiceStub;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("test")
public class ServiceRequestControllerTest extends AbstractControllerTest {

	@Autowired
	MockCoreServiceStub coreServiceStub;

	@Test
	public void createServiceRequestTest() throws Exception {
		mvc.perform(post("/service-requests/create").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "  \"description\": \"desc-test\",\"emergency\": \"emergency-test\","
						+ "  \"name\": \"name-createServiceRequestTest\"," + "  \"reporter\": \"reporter-test\","
						+ "\"templateId\": 1," + "\"attachFileId\":1 } "))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(post("/service-requests/query").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "  \"filters\": [\r\n" + "    {\r\n" + "      \"name\": \"name\",\r\n"
						+ "      \"operator\": \"eq\",\r\n" + "      \"value\": \"name-createServiceRequestTest\"\r\n"
						+ "    }\r\n" + "  ]\r\n" + "}"))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)));
	}

	@Test
	public void updateServiceRequestWithValidDataTest() throws Exception {
		mvc.perform(put("/service-requests/9999/done").contentType(MediaType.APPLICATION_JSON)
				.content("{\"result\": \"Approved\"}")).andExpect(jsonPath("$.status", is("OK")));
	}

	@Test
	public void updateServiceRequestWithInvalidDataTest() throws Exception {
		mvc.perform(put("/service-requests/9998/done").contentType(MediaType.APPLICATION_JSON)
				.content("{\"result\": \"Approved\"}")).andExpect(jsonPath("$.status", is("ERROR")));
	}
}
