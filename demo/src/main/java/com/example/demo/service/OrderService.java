package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;



    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

//order

    public Order createOrder(Order order){ //create
        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() { //read
        return this.orderRepository.findAll();
    }


    //update
    public Order updateOrder(Order order, int order_id) {

        Order existingOrder= orderRepository.findById(order_id).orElseThrow(
                () -> new ResourceNotFoundException("order", "Id", order_id));

        existingOrder.setOrderDescription(order.getOrderDescription());
        orderRepository.save(existingOrder);
        return existingOrder;
    }

    //delete

    public void deleteOrder(int order_id) {
        //check if id exists
        orderRepository.findById(order_id).orElseThrow(() -> new ResourceNotFoundException ("Order", "Id", order_id));
        orderRepository.deleteById(order_id);
    }



    public Order getOrderDetail(int orderId) {
        Optional<Order> order = this.orderRepository.findById(orderId);
        return order.isPresent() ? order.get() : null;
    }

    public float getCartAmount(List<ShoppingCart> shoppingCartList) {

        float totalCartAmount = 0f;
        float singleCartAmount = 0f;
        int availableQuantity = 0;

        for (ShoppingCart cart : shoppingCartList) {

            int productId = cart.getProductId();
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                Product product1 = product.get();
                if (product1.getAvailableQuantity() < cart.getQuantity()) {
                    singleCartAmount = product1.getPrice() * product1.getAvailableQuantity();
                    cart.setQuantity(product1.getAvailableQuantity());
                } else {
                    singleCartAmount = cart.getQuantity() * product1.getPrice();
                    availableQuantity = product1.getAvailableQuantity() - cart.getQuantity();
                }
                totalCartAmount = totalCartAmount + singleCartAmount;
                product1.setAvailableQuantity(availableQuantity);
                availableQuantity=0;
                cart.setProductName(product1.getName());
                cart.setAmount(singleCartAmount);
                productRepository.save(product1);
            }
        }
        return totalCartAmount;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
