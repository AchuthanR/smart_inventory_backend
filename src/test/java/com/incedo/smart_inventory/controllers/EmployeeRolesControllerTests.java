package com.incedo.smart_inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Order;
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

@Order(1)
public class EmployeeRolesControllerTests extends AbstractTest {
	
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	@Test
	@Order(1)
	public void testCreateEmployeeRole1() throws Exception {
		EmployeeRoles employeeRoles = new EmployeeRoles();
		employeeRoles.setId(1);
		employeeRoles.setRole("superadmin");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employeeRoles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employeeRoles)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		EmployeeRoles createdEmployeeRoles = super.mapFromJson(content, EmployeeRoles.class);
		assertTrue(createdEmployeeRoles.getId() == 1);
		
		assertTrue(employeeRolesRepository.findById(1).isPresent());
	}
	
	@Test
	@Order(2)
	public void testCreateEmployeeRole2() throws Exception {
		EmployeeRoles employeeRoles = new EmployeeRoles();
		employeeRoles.setId(2);
		employeeRoles.setRole("manager");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employeeRoles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employeeRoles)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		EmployeeRoles createdEmployeeRoles = super.mapFromJson(content, EmployeeRoles.class);
		assertTrue(createdEmployeeRoles.getId() == 2);
		
		assertTrue(employeeRolesRepository.findById(2).isPresent());
	}
	
	@Test
	@Order(3)
	public void testCreateEmployeeRole3() throws Exception {
		EmployeeRoles employeeRoles = new EmployeeRoles();
		employeeRoles.setId(3);
		employeeRoles.setRole("employee");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employeeRoles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employeeRoles)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		EmployeeRoles createdEmployeeRoles = super.mapFromJson(content, EmployeeRoles.class);
		assertTrue(createdEmployeeRoles.getId() == 3);
		
		assertTrue(employeeRolesRepository.findById(3).isPresent());
	}
	
	@Test
	@Order(3)
	public void testCreate() throws Exception {
		EmployeeRoles employeeRoles = new EmployeeRoles();
		employeeRoles.setId(4);
		employeeRoles.setRole("admin");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employeeRoles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employeeRoles)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		EmployeeRoles createdEmployeeRoles = super.mapFromJson(content, EmployeeRoles.class);
		assertTrue(createdEmployeeRoles.getId() == 4);
		
		assertTrue(employeeRolesRepository.findById(4).isPresent());
	}
	
	@Test
	@Order(5)
	public void testCreateWithNullValue() throws Exception {
		EmployeeRoles employeeRoles = new EmployeeRoles();
		employeeRoles.setId(5);
		employeeRoles.setRole(null);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employeeRoles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employeeRoles)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
		assertTrue(employeeRolesRepository.findById(5).isEmpty());
	}
	
	@Test
	@Order(6)
	public void testReadAll() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employeeRoles")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		EmployeeRoles[] employeeRoles = super.mapFromJson(content, EmployeeRoles[].class);
		assertTrue(employeeRoles.length >= 0);
	}
	
	@Test
	@Order(7)
	public void testUpdate() throws Exception {
	   EmployeeRoles employeeRoles = new EmployeeRoles();
	   employeeRoles.setRole("user");
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/employeeRoles/4")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(employeeRoles))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   EmployeeRoles updatedEmployeeRoles = super.mapFromJson(content, EmployeeRoles.class);
	   assertTrue(updatedEmployeeRoles.getId() == 4);
	   assertTrue(updatedEmployeeRoles.getRole().equals("user"));
	}
	
	@Test
	@Order(8)
	public void testUpdateNotExistingResource() throws Exception {
	   EmployeeRoles employeeRoles = new EmployeeRoles();
	   employeeRoles.setRole("user");
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/employeeRoles/5")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(employeeRoles))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
	@Test
	@Order(9)
	public void testDelete() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employeeRoles/4"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
	
	@Test
	@Order(10)
	public void testDeleteNotExistingResource() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employeeRoles/5"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(400, status);
	}
	
}
