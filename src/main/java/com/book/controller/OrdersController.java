package com.book.controller;

import com.book.dto.AddressDTO;
import com.book.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/cart/{id}")
    public ResponseEntity<String> orderByCartID(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return ordersService.orderByCartID(id, addressDTO);
    }




}
