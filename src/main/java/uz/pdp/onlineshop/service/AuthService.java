package uz.pdp.onlineshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.onlineshop.config.JwtService;
import uz.pdp.onlineshop.controller.AuthenticationRequest;
import uz.pdp.onlineshop.controller.AuthenticationResponse;
import uz.pdp.onlineshop.controller.RegisterRequest;
import uz.pdp.onlineshop.entity.Role;
import uz.pdp.onlineshop.entity.User;
import uz.pdp.onlineshop.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);

    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user  = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthenticationResponse(jwtService.generateToken(user));
    }
}
