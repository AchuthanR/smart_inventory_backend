package com.incedo.smart_inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.incedo.smart_inventory.AbstractTest;
import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;

@Order(3)
public class GodownControllerTests extends AbstractTest {
	
	@Autowired
	GodownRepository godownRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Test
	@Order(1)
	public void testCreateGodown1() throws Exception {
		Godown godown = new Godown();
		godown.setId(1);
		godown.setLocation("Bangalore");
		godown.setCapacityInQuintals(1000D);
		godown.setStartDate(LocalDate.now());
		godown.setManager(employeeRepository.findById(2).get());
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/godowns")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(godown)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Godown createdGodown = super.mapFromJson(content, Godown.class);
		assertTrue(createdGodown.getId() == 1);
		
		assertTrue(godownRepository.findById(1).isPresent());
	}
	
	@Test
	@Order(2)
	public void testCreateGodown2() throws Exception {
		Godown godown = new Godown();
		godown.setId(2);
		godown.setLocation("Hyderabad");
		godown.setCapacityInQuintals(2000D);
		godown.setStartDate(LocalDate.now());
		godown.setManager(employeeRepository.findById(3).get());
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/godowns")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(godown)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Godown createdGodown = super.mapFromJson(content, Godown.class);
		assertTrue(createdGodown.getId() == 2);
		
		assertTrue(godownRepository.findById(2).isPresent());
	}
	
	@Test
	@Order(3)
	public void testCreate() throws Exception {
		Godown godown = new Godown();
		godown.setId(3);
		godown.setLocation("Chennai");
		godown.setCapacityInQuintals(1000D);
		godown.setStartDate(LocalDate.now());
		godown.setManager(employeeRepository.findById(4).get());
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/godowns")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(godown)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Godown createdGodown = super.mapFromJson(content, Godown.class);
		assertTrue(createdGodown.getId() == 3);
		
		assertTrue(godownRepository.findById(3).isPresent());
	}
	
	@Test
	@Order(4)
	public void testCreateWithNullValue() throws Exception {
		Godown godown = new Godown();
		godown.setId(4);
		godown.setLocation("Bangalore");
		godown.setCapacityInQuintals(1000D);
		godown.setStartDate(LocalDate.now());
		godown.setManager(null);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/godowns")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(godown)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
		assertTrue(godownRepository.findById(4).isEmpty());
	}
	
	@Test
	@Order(5)
	public void testReadAll() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/godowns")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Godown[] godown = super.mapFromJson(content, Godown[].class);
		assertTrue(godown.length >= 0);
	}
	
	@Test
	@Order(6)
	public void testUpdate() throws Exception {
		Godown godown = new Godown();
		godown.setId(3);
		godown.setLocation("Chennai");
		godown.setCapacityInQuintals(1500D);
		godown.setStartDate(LocalDate.now());
		godown.setManager(employeeRepository.findById(4).get());
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/godowns/3")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(godown))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   Godown updatedGodown = super.mapFromJson(content, Godown.class);
	   assertTrue(updatedGodown.getId() == 3);
	   assertTrue(updatedGodown.getCapacityInQuintals().equals(1500D));
	}
	
	@Test
	@Order(7)
	public void testUpdateNotExistingResource() throws Exception {
		Godown godown = new Godown();
		godown.setId(4);
		godown.setLocation("Noida");
		godown.setCapacityInQuintals(1500D);
		godown.setStartDate(LocalDate.now());
		godown.setManager(employeeRepository.findById(3).get());
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/godowns/4")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(godown))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
	@Test
	@Order(8)
	public void testDelete() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/godowns/3"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
	
	@Test
	@Order(9)
	public void testDeleteNotExistingResource() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/godowns/4"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
}
