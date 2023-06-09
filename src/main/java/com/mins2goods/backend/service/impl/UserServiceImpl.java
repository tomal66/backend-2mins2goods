package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.dto.AddressDto;
import com.mins2goods.backend.dto.UserDto;
import com.mins2goods.backend.model.Address;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.UserRepository;
import com.mins2goods.backend.service.CartItemService;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartItemService cartItemService;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(String username, UserDto userDto) {
        User user = getUser(username);

        // Updating user details
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setMobile(userDto.getMobile());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        // Updating address details
        AddressDto addressDto = userDto.getAddress();
        Address address = user.getAddress();

        address.setAddress(addressDto.getAddress());
        address.setCountry(addressDto.getCountry());
        address.setZipcode(addressDto.getZipcode());
        address.setCity(addressDto.getCity());
        address.setLongitude(addressDto.getLongitude());
        address.setLatitude(addressDto.getLatitude());
        address.setState(addressDto.getState());

        user.setAddress(address);
        User updatedUser = saveUser(user);

        cartItemService.clearCart(username);

        return convertToUserDto(updatedUser);
    }


    @Override
    public Optional<Object> getUserById(Long userId) {
        return Optional.of(userRepository.findById(userId));
    }

    @Override
    public UserDto convertToUserDto(User user) {
        AddressDto addressDto = new AddressDto(
                user.getAddress().getAddress(),
                user.getAddress().getCountry(),
                user.getAddress().getZipcode(),
                user.getAddress().getCity(),
                user.getAddress().getLongitude(),
                user.getAddress().getLatitude(),
                user.getAddress().getState()
        );

        return new UserDto(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getMobile(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                addressDto
        );
    }

}
