package com.example.demo.controller;

import com.example.demo.entity.ShoppingCart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //create
    @PostMapping(value = "/add")
    @CrossOrigin
    public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCart cart) {

        if (cart != null) {
            cartService.createCart(cart);
            return ResponseEntity.ok(cart);
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No cart item created");

    }

    //read
    @GetMapping(value = "/getAll") //working
    @CrossOrigin
    public ResponseEntity<List<ShoppingCart>> getAllCarts() {

        List<ShoppingCart> cartList = cartService.getAllCarts();
        if (cartList.isEmpty() == false)
            return ResponseEntity.ok(cartList);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No carts available");
    }


    //update
    @PutMapping("/update/{id}")
    @CrossOrigin
    public ResponseEntity<ShoppingCart> updateCart(@PathVariable("id") int id, @RequestBody ShoppingCart cart) {
        if (cart != null)
            return new ResponseEntity<ShoppingCart>(cartService.updateCart(cart, id), HttpStatus.OK);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No cart item found with matching id");
    }


    //delete
    @DeleteMapping("/delete/{id}") // working
    @CrossOrigin
    public ResponseEntity<String> deleteCart(@PathVariable("id") int id) {
        List<ShoppingCart> cartList = cartService.getAllCarts();
        int flag = 0;
        for (ShoppingCart cart : cartList) {
            if (cart.getId() == id) {
                flag = 1;
            }
        }
        if (flag == 1) {
            cartService.deleteCart(id);
            return new ResponseEntity<String>("deleted!", HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No customer found with matching id");
    }

}