package com.mins2goods.backend.service;

import com.mins2goods.backend.model.Address;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.AddressRepository;
import com.mins2goods.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address getAddress(String username) {
        User user = userRepository.findByUsername(username);
        return addressRepository.findByUser(user);
    }
}
