package com.example.demo.repository;


import com.example.demo.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart,Integer> {

}
