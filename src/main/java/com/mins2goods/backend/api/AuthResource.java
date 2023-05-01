package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.AddressDto;
import com.mins2goods.backend.dto.AuthResponseDto;
import com.mins2goods.backend.dto.LoginDto;
import com.mins2goods.backend.dto.RegisterDto;
import com.mins2goods.backend.model.Address;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.UserRepository;
import com.mins2goods.backend.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthResource(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
    {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        //registerDto.setRole("ROLE_USER");

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setMobile(registerDto.getMobile());
        user.setEmail(registerDto.getEmail());
        user.setRole(registerDto.getRole());
        AddressDto addressDto = registerDto.getAddress();
        if(addressDto!=null)
        {
            Address address = new Address();
            address.setAddress(addressDto.getAddress());
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
            address.setLongitude(addressDto.getLongitude());
            address.setLatitude(addressDto.getLatitude());
            address.setZipcode(addressDto.getZipcode());
            address.setState(addressDto.getState());
            address.setUser(user);
            user.setAddress(address);
        }
        userRepository.save(user);

        return new ResponseEntity<>("User registration success!", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        String username = jwtGenerator.getUsernameFromJWT(token);
        String role = userRepository.findByUsername(username).getRole();

        return new ResponseEntity<>(new AuthResponseDto(token, username, role), HttpStatus.OK);
    }
}
