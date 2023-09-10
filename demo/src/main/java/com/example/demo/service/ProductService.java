package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService  {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){ //create
        return productRepository.save(product);
    }


    public List<Product> getAllProducts() { //read
        return this.productRepository.findAll();
    }

    public Product getProductById(int product_id) {
        Optional<Product> product= productRepository.findById(product_id);
        if(product.isPresent()){
            return product.get();
        }
        else{
            throw new ResourceNotFoundException("Product", "Id", product_id);
        }
    }

    //update
    public Product updateProduct(Product product, int product_id) {

        Product existingProduct= productRepository.findById(product_id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", product_id));
        existingProduct. setName(product.getName());
        existingProduct.setAvailableQuantity(product.getAvailableQuantity());
        existingProduct.setPrice(product.getPrice());
        productRepository.save(existingProduct);
        return existingProduct;
    }

    //delete

    public void deleteProduct(int product_id) {
        //check if id exists
        productRepository.findById(product_id).orElseThrow(() -> new ResourceNotFoundException ("Product", "Id", product_id));
        productRepository.deleteById(product_id);
    }
}