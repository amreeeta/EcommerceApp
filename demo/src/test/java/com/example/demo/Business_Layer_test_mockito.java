package com.example.demo;
import static org.mockito.Mockito.verify;

import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class Business_Layer_test_mockito {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    private CartService cartService;

    private CustomerService customerService;
    private ProductService productService;


     //   this.productService= new ProductService(this.productRepository);


    @Test
    void getAllProducts()
    {
        this.productService= new ProductService(this.productRepository);
        productService.getAllProducts();
        verify(productRepository).findAll();
    }

    @Test
    void getAllCustomers()
    {
        this.customerService= new CustomerService(this.customerRepository);
        customerService.getAllCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void getAllCartItems()
    {
        this.cartService= new CartService(this.cartRepository);
        cartService.getAllCarts();
        verify(cartRepository).findAll();
    }


}
