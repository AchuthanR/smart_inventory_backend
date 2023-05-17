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
import com.incedo.smart_inventory.repositories.ProductRepository;

public class ProductControllerTests extends AbstractTest {
	@Autowired
	ProductRepository productRepository;
	
	@Test
	@Order(3)
	public void testReadAll() {
		try {
			MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
					.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		   
			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			Product[] product = super.mapFromJson(content, Product[].class);
			assertTrue(product.length >= 0);
		}
		catch (Exception ex) {
			
		}
	}
	
	@Test
	@Order(1)
	public void testCreate() throws Exception {
		Product product = new Product();
		product.setId(1);
		product.setName("Laptop");
		product.setPrice(10000.00);
		product.setWeight((float) 70);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(product)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Product createdproduct = super.mapFromJson(content, Product.class);
		assertTrue(createdproduct.getId() == 1);
		
		assertTrue(productRepository.findById(1).isPresent());
	}
	
	@Test
	@Order(2)
	public void testCreateWithNullValue() throws Exception {
		Product product = new Product();
		product.setId(1);
		product.setPrice(10000.00);
		product.setWeight((float) 70);
		product.setName(null);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(product)))
				.andReturn();
	   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		
		assertTrue(productRepository.findById(2).isEmpty());
	}
	@Test
	@Order(4)
	public void testUpdate() throws Exception {
	   Product product = new Product();
	   product.setId(1);
	   product.setName("Computer");
	   product.setPrice(10000.00);
	   product.setWeight( (float) 70);
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/products/1")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(product))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   Product updatedProduct = super.mapFromJson(content, Product.class);
	   assertTrue(updatedProduct.getId() == 1);
	   assertTrue(updatedProduct.getName().equals("Computer"));
	}
	
	
	
	@Test
	@Order(5)
	public void testUpdateNotExistingResource() throws Exception {
	   Product product = new Product();
	   product.setName("earphone");
	   
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/product/2")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(mapToJson(product))).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(404, status);
	}
	
	@Test
	@Order(6)
	public void testDelete() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/1"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	}
	
	@Test
	@Order(7)
	public void testDeleteNotExistingResource() throws Exception {
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/2"))
			   .andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(400, status);
	}
	
}
