package com.book.service;

import com.book.exception.CustomException;
import com.book.model.Book;
import com.book.model.Cart;
import com.book.model.User;
import com.book.repository.BookRepository;
import com.book.repository.CartRepository;
import com.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Cart addtocart(Long id, Long bookId, Long quantity) {
        Cart c = new Cart();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new CustomException("Book not found By id !"));
        User user= userRepository.findById(id).orElseThrow(() -> new CustomException("User not found By id !"));
        c.setBook(book);
        c.setUser(user);
        c.setQuantity(quantity);
        c.setPrice(book.getPrice()*quantity);
        if(book.getQuantity()-quantity >=0){
            book.setQuantity(book.getQuantity()-quantity);
        }else {
            throw new CustomException("Book out of Stock !");
        }
        cartRepository.save(c);
        return c;
    }

    @Override
    public
}
