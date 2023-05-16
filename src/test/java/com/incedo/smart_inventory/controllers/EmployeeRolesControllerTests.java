package com.incedo.smart_inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incedo.smart_inventory.AbstractTest;
import com.incedo.smart_inventory.SmartInventoryApplication;
import com.incedo.smart_inventory.entities.EmployeeRoles;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;

public class EmployeeRolesControllerTests extends AbstractTest {
	
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	@Test
	public void testReadAll() {
		try {
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employeeRoles")
					.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		   
			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			EmployeeRoles[] employeeRoles = super.mapFromJson(content, EmployeeRoles[].class);
			assertTrue(employeeRoles.length >= 0);
		}
		catch (Exception ex) {
			
		}
	}
	
}
