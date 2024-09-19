package uz.pdp.onlineshop.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.onlineshop.config.JwtService;
import uz.pdp.onlineshop.controller.AuthenticationResponse;
import uz.pdp.onlineshop.dto.LoginDto;
import uz.pdp.onlineshop.dto.SignUpDto;
import uz.pdp.onlineshop.entity.Role;
import uz.pdp.onlineshop.entity.User;
import uz.pdp.onlineshop.exception.EmailAlreadyRegisteredExceptions;
import uz.pdp.onlineshop.exception.EmailOrPasswordIncorrectException;
import uz.pdp.onlineshop.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(SignUpDto request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        if(userRepository.existsByEmailAndEnabledTrue(user.getEmail())) {
            throw new EmailAlreadyRegisteredExceptions(user.getEmail());
        }
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);

    }

    public AuthenticationResponse login(LoginDto request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new EmailOrPasswordIncorrectException();
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        return new AuthenticationResponse(jwtService.generateToken(user.get()));
    }
}
