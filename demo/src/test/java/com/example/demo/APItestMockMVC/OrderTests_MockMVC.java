package com.example.demo.APItestMockMVC;

import com.example.demo.controller.OrderController;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebMvcTest(controllers = OrderController.class)
class OrderTests_MockMVC {
	@MockBean
	private OrderService orderService;
	@Autowired
	private MockMvc mockMvc;
	@Test
	@DisplayName("Should List All orders When making GET request to endpoint - /api/order/getAll")
	public void shouldCreateOrder() throws Exception {
		Order postRequest1 = new Order();
		postRequest1.setOrderDescription("this order needs gift wrapping");

		Customer customer = new Customer();
		customer.setName("bob");
		customer.setEmail("bob@gmail.com");
		postRequest1.setCustomer(customer);

		List <ShoppingCart> cartItems= new ArrayList<ShoppingCart>();

		ShoppingCart cartItem1=new ShoppingCart();
		cartItem1.setProductId(1);
		cartItem1.setQuantity(2);

		ShoppingCart cartItem2=new ShoppingCart();
		cartItem2.setProductId(2);
		cartItem2.setQuantity(2);

		cartItems.add(cartItem1);
		cartItems.add(cartItem2);

		postRequest1.setCartItems(cartItems);


		Mockito.when(orderService.getAllOrders()).thenReturn(asList(postRequest1));
		mockMvc.perform(get("/api/order/getAll"))
				.andExpect(status().is(200));


	}
}
