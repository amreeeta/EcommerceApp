package com.example.demo.controller;


import com.example.demo.controller.dto.OrderDTO;
import com.example.demo.controller.dto.ResponseOrderDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.service.CartService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class ComplexOperationController {

    private OrderService orderService;
    private ProductService productService;
    private CustomerService customerService;

    private CartService cartService;


    public ComplexOperationController(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
        this.cartService=cartService;
    }

    private Logger logger = LoggerFactory.getLogger(ComplexOperationController.class);



    @GetMapping(value = "/getOrder/{orderId}")
    @CrossOrigin
    public ResponseEntity<Order> getOrderDetails(@PathVariable int orderId) {

        Order order = orderService.getOrderDetail(orderId);
        if(order==null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No products available");
        }
        else
        {
            return ResponseEntity.ok(order);
        }


    }

    @PostMapping("/placeOrder")
    @CrossOrigin
    public ResponseEntity<ResponseOrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("Request Payload " + orderDTO.toString());
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        float amount = orderService.getCartAmount(orderDTO.getCartItems());
        Customer customer = new Customer(orderDTO.getCustomerName(), orderDTO.getCustomerEmail());
        Integer customerIdFromDb = customerService.isCustomerPresent(customer);
        if (customerIdFromDb != null) {
            customer.setId(customerIdFromDb);
            logger.info("Customer already present in db with id : " + customerIdFromDb);
        }else{
            customer = customerService.saveCustomer(customer);
            logger.info("Customer saved.. with id : " + customer.getId());
        }
        Order order = new Order(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems());
        order = orderService.saveOrder(order);
        logger.info("Order processed successfully..");

        responseOrderDTO.setAmount(amount);
        responseOrderDTO.setDate(DateUtil.getCurrentDateTime());
        responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
        responseOrderDTO.setOrderId(order.getId());
        responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

        if(responseOrderDTO==null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No products available");
        }
        else
        {
            return ResponseEntity.ok(responseOrderDTO);
        }

    }

}
