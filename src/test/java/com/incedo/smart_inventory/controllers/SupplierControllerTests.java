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
import com.incedo.smart_inventory.entities.Product;
import com.incedo.smart_inventory.entities.Supplier;
import com.incedo.smart_inventory.repositories.SupplierRepository;

@Order(5)
public class SupplierControllerTests extends AbstractTest{
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Test
	@Order(3)
	public void testReadAll() {
		try {
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/suppliers")
					.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		   
			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			Supplier[] supplier = super.mapFromJson(content, Supplier[].class);
			assertTrue(supplier.length >= 0);
		}
		catch (Exception ex) {
			
		}
	}
	
	@Test
	@Order(1)
	public void testCreate() throws Exception {
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("ABC limited");
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/suppliers")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(supplier)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Supplier createdSupplier = super.mapFromJson(content, Supplier.class);
		assertTrue(createdSupplier.getId() == 1);
		
		assertTrue(supplierRepository.findById(1).isPresent());
	}
	
	@Test
	@Order(2)
	public void testCreateWithNullValue() throws Exception {
		Supplier supplier = new Supplier();
		supplier.setId(2);
		supplier.setName(null);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/suppliers")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(supplier)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
		assertTrue(supplierRepository.findById(2).isEmpty());
	}
	
	@Test
	@Order(4)
	public void testUpdate() throws Exception {
	   Supplier supplier = new Supplier();
	   supplier.setName("DEF limited");
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/suppliers/1")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(supplier))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   Supplier updatedSupplier = super.mapFromJson(content, Supplier.class);
	   assertTrue(updatedSupplier.getId() == 1);
	   assertTrue(updatedSupplier.getName().equals("DEF limited"));
	}
	
	
	@Test
	@Order(5)
	public void testUpdateNotExistingResource() throws Exception {
		Supplier supplier = new Supplier();
	   supplier.setName("DEF limited");
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/suppliers/2")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(supplier))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
	@Test
	@Order(6)
	
	public void testDelete() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/suppliers/1"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
	
	@Test
	@Order(7)
	public void testDeleteNotExistingResource() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/suppliers/2"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(400, status);
	}
	
	
	
	

}
