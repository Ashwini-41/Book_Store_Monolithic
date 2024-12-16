package com.book.service;

import com.book.model.Cart;

public interface CartService {

    Cart addtocart(Long id, Long bookId, Long quantity);
}

