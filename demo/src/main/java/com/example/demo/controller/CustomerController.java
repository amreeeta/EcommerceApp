package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;



    //create
    @PostMapping(value = "/add")
    @CrossOrigin
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        if (customer!=null) {
            customerService.createCustomer(customer);
            return ResponseEntity.ok(customer);
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer created");
    }

    //read
    @GetMapping(value = "/getAll") //working
    @CrossOrigin
    public ResponseEntity<List<Customer>> getAllCustomers() {

        List<Customer> customerList = customerService.getAllCustomers();
        if (customerList.isEmpty()==false)
            return ResponseEntity.ok(customerList);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customers available");
    }

    //update
    @PutMapping("/update/{id}")
    @CrossOrigin
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {


        if(customer!=null)
        {
            return new ResponseEntity<Customer>(customerService.updateCustomer(customer, id), HttpStatus.OK);
        }

        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer found with matching id");
    }



    //delete
    @DeleteMapping("/delete/{id}") // working
    @CrossOrigin
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
        List<Customer> customerList = customerService.getAllCustomers();
        int flag=0;
        for(Customer customer: customerList)
        {
            if(customer.getId()==id)
            {
                flag=1;
            }
        }
        if(flag==1)
        {
            customerService.deleteCustomer(id);
            return new ResponseEntity<String>("deleted!", HttpStatus.OK);
        }

        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer found with matching id");
    }
}