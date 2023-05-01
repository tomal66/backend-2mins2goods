package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Address;
import com.mins2goods.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUser(User user);
}
