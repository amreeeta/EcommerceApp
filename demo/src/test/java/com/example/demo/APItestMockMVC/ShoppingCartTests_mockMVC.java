package com.example.demo.APItestMockMVC;

import com.example.demo.controller.CartController;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.service.CartService;
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
@WebMvcTest(controllers = CartController.class)
class ShoppingCartTests_mockMVC {
	@MockBean
	private CartService cartService;
	@Autowired
	private MockMvc mockMvc;
	@Test

	@DisplayName("Should List All cart Items When making GET request to endpoint - /api/cart/getAll")
	public void shouldCreateCart() throws Exception {
		ShoppingCart postRequest1 = new ShoppingCart();
		postRequest1.setProductId(1);
		postRequest1.setQuantity(2);
		Mockito.when(cartService.getAllCarts()).thenReturn(asList(postRequest1));
		mockMvc.perform(get("/api/cart/getAll"))
				.andExpect(status().is(200));
	}
}
