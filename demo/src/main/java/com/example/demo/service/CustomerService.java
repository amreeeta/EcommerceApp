package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Integer isCustomerPresent(Customer customer){
        Customer customer1 = customerRepository.getCustomerByEmailAndName(customer.getEmail(),customer.getName());
        return customer1!=null ? customer1.getId(): null ;
    }

    public  Customer createCustomer(Customer customer){ //create
        return customerRepository.save(customer);
    }


    public List< Customer> getAllCustomers() { //read
        return this.customerRepository.findAll();
    }

    //update
    public  Customer updateCustomer(Customer customer, int customer_id) {

        Customer existingCustomer = customerRepository.findById(customer_id).orElseThrow(
                () -> new ResourceNotFoundException("customer", "Id", customer_id));
        existingCustomer. setName(customer.getName());
        existingCustomer. setEmail(customer.getEmail());
        customerRepository.save(existingCustomer);
        return existingCustomer;
    }

    //delete

    public void deleteCustomer(int customer_id) {
        //check if id exists
        customerRepository.findById(customer_id).orElseThrow(() -> new ResourceNotFoundException ("customer", "Id", customer_id));
        customerRepository.deleteById(customer_id);
    }


}
