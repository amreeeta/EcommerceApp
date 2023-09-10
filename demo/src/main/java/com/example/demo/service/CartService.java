package com.example.demo.service;

import com.example.demo.entity.ShoppingCart;
import com.example.demo.repository.CartRepository;
import com.example.demo.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ShoppingCart createCart(ShoppingCart cart){ //create
        return cartRepository.save(cart);
    }


    public List<ShoppingCart> getAllCarts() { //read
        return this.cartRepository.findAll();
    }

    public ShoppingCart getCartById(int cart_id) {
        Optional<ShoppingCart> cart= cartRepository.findById(cart_id);
        if(cart.isPresent()){
            return cart.get();
        }
        else{
            throw new ResourceNotFoundException("cart", "Id", cart_id);
        }
    }

    //update
    public ShoppingCart updateCart(ShoppingCart cart, int cart_id) {

        ShoppingCart existingCart= cartRepository.findById(cart_id).orElseThrow(
                () -> new ResourceNotFoundException("cart", "Id", cart_id));

        existingCart.setQuantity(cart.getQuantity());
        cartRepository.save(existingCart);
        return existingCart;
    }

    //delete

    public void deleteCart(int cart_id) {
        //check if id exists
        cartRepository.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException ("cart", "Id", cart_id));
        cartRepository.deleteById(cart_id);
    }
}
