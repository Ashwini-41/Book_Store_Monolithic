package com.book.service;

import com.book.dto.AddressDTO;
import com.book.exception.CustomException;
import com.book.model.Book;
import com.book.model.Cart;
import com.book.model.Orders;
import com.book.model.User;
import com.book.repository.BookRepository;
import com.book.repository.CartRepository;
import com.book.repository.OrderRepository;
import com.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<String> orderByCartID(Long id, AddressDTO addressDTO) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart of id not Found"));

        Book book = bookRepository.findById(cart.getBook().getId())
                .orElseThrow(() -> new CustomException("Book of the id not found"));

        if (book.getQuantity() < cart.getQuantity()) {
            throw new CustomException("Insufficient stock for the book");
        }

        book.setQuantity(book.getQuantity() - cart.getQuantity());
        bookRepository.save(book);

        User user = userRepository.findById(cart.getUser().getId())
                .orElseThrow(() -> new CustomException("User not found by id"));

        Orders orders = new Orders();
        orders.setBook(book);
        orders.setUser(user);
        orders.setOrderDateTime(LocalDateTime.now());
        orders.setPrice(cart.getPrice());
        orders.setAddress(addressDTO.getAddress());
        orders.setCancel(false);

        orderRepository.save(orders);

        return ResponseEntity.ok("Order placed successfully");
    }

//    @Override
//    public ResponseEntity<String> orderByCartID(Long id, AddressDTO addressDTO) {
//        Orders orders = new Orders();
//        Cart cart = cartRepository.findById(id).orElseThrow(() -> new CustomException("Cart of id not Found"));
//        orders.setBook(cart.getBook());
//        Book book =  bookRepository.findById(cart.getId()).orElseThrow(() -> new CustomException("book of the id not found"));
//        book.setQuantity(book.getQuantity()-cart.getQuantity());
//        bookRepository.save(book);
//        orders.setOrderDateTime(LocalDateTime.now());
//        orders.setPrice(cart.getPrice());
//        orders.setAddress(addressDTO.getAddress());
//
//        User user = userRepository.findById(cart.getUser().getId()).orElseThrow(() -> new CustomException("User not found by id"));
//        orders.setUser(user);
//        orders.setBook(book);
//        orders.setCancel(false);
//
//        orderRepository.save(orders);
//
//        return ResponseEntity.ok("Order Place successfully");
//    }
}
