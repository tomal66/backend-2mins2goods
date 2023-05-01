package com.mins2goods.backend.api;

import com.mins2goods.backend.model.Address;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddressResource {
    private final AddressService addressService;

    @PostMapping("/address/save")
    public ResponseEntity<Address> saveAddress(@RequestBody Address address)
    {
        return ok().body(addressService.saveAddress(address));
    }

    @GetMapping("/address/{username}")
    public ResponseEntity<Address> getAddress(@PathVariable String username)
    {
        return ResponseEntity.ok().body(addressService.getAddress(username));
    }
}
