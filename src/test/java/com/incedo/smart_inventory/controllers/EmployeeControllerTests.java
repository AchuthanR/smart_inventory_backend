package com.incedo.smart_inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.incedo.smart_inventory.AbstractTest;
import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;

@Order(2)
public class EmployeeControllerTests extends AbstractTest {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	@Test
	@Order(1)
	public void testCreateEmployee1() throws Exception {
		Employee employee = new Employee();
		employee.setId(1);
		employee.setName("Abhinash");
		employee.setUsername("abhi123");
		employee.setPassword("abhi123");
		employee.setRole(employeeRolesRepository.findById(1).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employee)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee createdEmployee = super.mapFromJson(content, Employee.class);
		assertTrue(createdEmployee.getId() == 1);
		
		assertTrue(employeeRepository.findById(1).isPresent());
	}
	
	@Test
	@Order(2)
	public void testCreateEmployee2() throws Exception {
		Employee employee = new Employee();
		employee.setId(2);
		employee.setName("Adarsh");
		employee.setUsername("adarsh123");
		employee.setPassword("adarsh123");
		employee.setRole(employeeRolesRepository.findById(2).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employee)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee createdEmployee = super.mapFromJson(content, Employee.class);
		assertTrue(createdEmployee.getId() == 2);
		
		assertTrue(employeeRepository.findById(2).isPresent());
	}
	
	@Test
	@Order(3)
	public void testCreateEmployee3() throws Exception {
		Employee employee = new Employee();
		employee.setId(3);
		employee.setName("Ajith");
		employee.setUsername("ajith123");
		employee.setPassword("ajith123");
		employee.setRole(employeeRolesRepository.findById(2).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employee)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee createdEmployee = super.mapFromJson(content, Employee.class);
		assertTrue(createdEmployee.getId() == 3);
		
		assertTrue(employeeRepository.findById(3).isPresent());
	}
	
	@Test
	@Order(4)
	public void testCreateEmployee4() throws Exception {
		Employee employee = new Employee();
		employee.setId(4);
		employee.setName("Santosh");
		employee.setUsername("santosh123");
		employee.setPassword("santosh123");
		employee.setRole(employeeRolesRepository.findById(2).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employee)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee createdEmployee = super.mapFromJson(content, Employee.class);
		assertTrue(createdEmployee.getId() == 4);
		
		assertTrue(employeeRepository.findById(4).isPresent());
	}
	
	@Test
	@Order(5)
	public void testCreate() throws Exception {
		Employee employee = new Employee();
		employee.setId(5);
		employee.setName("Bob");
		employee.setUsername("bob123");
		employee.setPassword("bob123");
		employee.setRole(employeeRolesRepository.findById(3).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employee)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee createdEmployee = super.mapFromJson(content, Employee.class);
		assertTrue(createdEmployee.getId() == 5);
		
		assertTrue(employeeRepository.findById(5).isPresent());
	}
	
	@Test
	@Order(6)
	public void testCreateWithNullValue() throws Exception {
		Employee employee = new Employee();
		employee.setId(6);
		employee.setName("Robin");
		employee.setUsername("robin123");
		employee.setPassword("robin123");
		employee.setRole(null);
		employee.setGodown(null);
		employee.setIsLocked(false);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(employee)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
		assertTrue(employeeRepository.findById(6).isEmpty());
	}
	
	@Test
	@Order(7)
	public void testReadAll() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Employee[] employee = super.mapFromJson(content, Employee[].class);
		assertTrue(employee.length >= 0);
	}
	
	@Test
	@Order(8)
	public void testUpdate() throws Exception {
		Employee employee = new Employee();
		employee.setId(5);
		employee.setName("Bob Iger");
		employee.setUsername("bob123");
		employee.setPassword("bob123");
		employee.setRole(employeeRolesRepository.findById(3).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/5")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(employee))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   Employee updatedEmployee = super.mapFromJson(content, Employee.class);
	   assertTrue(updatedEmployee.getId() == 5);
	   assertTrue(updatedEmployee.getName().equals("Bob Iger"));
	}
	
	@Test
	@Order(9)
	public void testUpdateNotExistingResource() throws Exception {
		Employee employee = new Employee();
		employee.setId(6);
		employee.setName("Robin");
		employee.setUsername("robin123");
		employee.setPassword("robin123");
		employee.setRole(employeeRolesRepository.findById(3).get());
		employee.setGodown(null);
		employee.setIsLocked(false);
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/6")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(employee))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
	@Test
	@Order(10)
	public void testDelete() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/5"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
	
	@Test
	@Order(11)
	public void testDeleteNotExistingResource() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/6"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
}
