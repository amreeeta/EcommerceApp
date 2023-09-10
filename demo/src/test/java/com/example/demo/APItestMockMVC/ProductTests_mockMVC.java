package com.example.demo.APItestMockMVC;

import com.example.demo.controller.ProductController;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductTests_mockMVC {
	@MockBean
	private ProductService productService;
	@Autowired
	private MockMvc mockMvc;
	@Test
	@DisplayName("Should List All products When making GET request to endpoint - /api/product/getAll")
	public void shouldCreateProduct() throws Exception {
		Product postRequest1 = new Product();
		postRequest1.setName("couch");
		postRequest1.setPrice((float) 244.6);
		postRequest1.setAvailableQuantity(6);
		Mockito.when(productService.getAllProducts()).thenReturn(asList(postRequest1));

		mockMvc.perform(get("/api/product/getAll"))
				.andExpect(status().is(200));


	}



}
