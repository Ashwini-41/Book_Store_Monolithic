package com.book.controller;

import com.book.model.Book;
import com.book.model.Cart;
import com.book.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addtocart(@RequestAttribute("id") Long id, @RequestParam Long BookId,@RequestParam Long quantity) {
        return cartService.addtocart(id,BookId,quantity);
    }

    





}
