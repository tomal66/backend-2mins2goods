package com.mins2goods.backend.service;

import com.mins2goods.backend.model.Address;

public interface AddressService {
    Address saveAddress(Address address);
    Address getAddress(String username);
}
