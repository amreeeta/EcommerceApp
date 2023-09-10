package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

//order

    //create
    @PostMapping(value = "/add")
    @CrossOrigin
    public  ResponseEntity<Order> createOrder(@RequestBody Order order) {

        if (order!=null) {
            orderService.createOrder(order);
            return ResponseEntity.ok(order);
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No order item created");


    }


    //read
    @GetMapping(value = "/getAll") //working
    @CrossOrigin
    public ResponseEntity<List<Order>> getAllOrders() {

        List<Order> orderList = orderService.getAllOrders();
        if (orderList.isEmpty()==false)
            return ResponseEntity.ok(orderList);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Orders available");
    }

    //update
    @PutMapping("/update/{id}")
    @CrossOrigin
    public ResponseEntity<Order> updateOrder(@PathVariable("id") int id, @RequestBody Order order) {
        if(order!=null)
            return new ResponseEntity<Order>(orderService.updateOrder(order, id), HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No order item found with matching id");
    }
    //delete
    //delete
    @DeleteMapping("/delete/{id}") // working
    @CrossOrigin
    public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
        List<Order> orderList = orderService.getAllOrders();
        int flag = 0;
        for (Order order : orderList) {
            if (order.getId() == id) {
                flag = 1;
            }
        }
        if (flag == 1) {
            orderService.deleteOrder(id);
            return new ResponseEntity<String>("deleted!", HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No order found with matching id");
    }
}


