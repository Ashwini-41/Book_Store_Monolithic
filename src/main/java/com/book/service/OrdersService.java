package com.book.service;

import com.book.dto.AddressDTO;
import org.springframework.http.ResponseEntity;

public interface OrdersService {
    ResponseEntity<String> orderByCartID(Long id, AddressDTO addressDTO);
}
