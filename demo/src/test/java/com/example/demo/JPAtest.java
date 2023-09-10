package com.example.demo;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class JPAtest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @Test
    void findCustomer()
    {
        Customer customer= new Customer("bob", "bob@gmail.com");
        customerRepository.save(customer);
        Assertions.assertEquals(customerRepository.getCustomerByEmailAndName("bob@gmail.com","bob").getId()>0, true);
    }

    @Test
    void findProduct()
    {
        Product product= new Product();
        product.setName("toothbrush");
        product.setPrice(12.5F);
        productRepository.save(product);
        Assertions.assertEquals(product.getId()>0, true);
    }

    @Test
    void findCart() {
        Product product= new Product();
        product.setName("toothbrush");
        product.setPrice(12.5F);
        product.setId(1);
        ShoppingCart cart = new ShoppingCart();
        cart.setProductId(1);
        cart.setQuantity(1);
        cartRepository.save(cart);
        Assertions.assertEquals(cart.getId()>0, true);
    }

    @Test
    void findOrder() {
        Order order= new Order();
        Customer customer= new Customer("bob", "bob@gmail.com");
        order.setCustomer(customer);
        Product product= new Product();

        product.setName("toothbrush");
        product.setPrice(12.5F);
        product.setId(1);
        ShoppingCart cart = new ShoppingCart();
        cart.setProductId(1);
        cart.setQuantity(1);
        cartRepository.save(cart);
        order.setCartItems(cartRepository.findAll());
        order.setOrderDescription("description");
        orderRepository.save(order);
        Assertions.assertEquals(order.getId()>0, true);
    }





}
