package com.example.demo.controller;


import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/product")

public class ProductController {
    //Product Implementation
    @Autowired
    private ProductService productService;



    //create
    @PostMapping(value = "/add")
    @CrossOrigin
    public ResponseEntity<Product> createProduct( @RequestBody Product product) {
        if (product!=null) {
            productService.createProduct(product);
            return ResponseEntity.ok(product);
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No product item created");

    }


    //read
    @GetMapping(value = "/getAll")
    @CrossOrigin//working
    public ResponseEntity<List<Product>> getAllProducts() {

        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);

    }

    //update
    @PutMapping("/update/{id}")
    @CrossOrigin
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        if(product!=null)
            return new ResponseEntity<Product>(productService.updateProduct(product, id), HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No product item found with matching id");
    }


    //delete
    @DeleteMapping("/delete/{id}") // working
    @CrossOrigin
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        List<Product> productList = productService.getAllProducts();
        int flag = 0;
        for (Product product : productList) {
            if (product.getId() == id) {
                flag = 1;
            }
        }
        if (flag == 1) {
            productService.deleteProduct(id);
            return new ResponseEntity<String>("deleted!", HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Product found with matching id");
    }
    @DeleteMapping("/deleteEmpty") // working
    @CrossOrigin
    public ResponseEntity<String> deleteEmptyProduct() {
        List<Product> productList = productService.getAllProducts();
        for (Product product : productList)
        {
            if(product.getAvailableQuantity()==0)
            {
                productService.deleteProduct(product.getId());
            }
        }
        return new ResponseEntity<String>("deleted!", HttpStatus.OK);
    }
}
