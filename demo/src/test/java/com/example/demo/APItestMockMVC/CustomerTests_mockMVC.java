package com.example.demo.APItestMockMVC;

import com.example.demo.controller.CustomerController;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
class CustomerTests_mockMVC {

	@MockBean
	private CustomerService customerService;

	@Autowired
	private MockMvc mockMvc;
	@Test
	@DisplayName("Should List All customers When making GET request to endpoint - /api/customer/getAll")
	public void shouldCreateCustomer() throws Exception {
		Customer postRequest1 = new Customer();
		postRequest1.setName("bob");
		postRequest1.setEmail("bob@gmail.com");
		Mockito.when(customerService.getAllCustomers()).thenReturn(asList(postRequest1));

		mockMvc.perform(get("/api/customer/getAll"))
				.andExpect(status().is(200));

	}







}
