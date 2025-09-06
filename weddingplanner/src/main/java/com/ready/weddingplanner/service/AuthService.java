package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.User;
import com.ready.weddingplanner.dto.AuthDto;
import com.ready.weddingplanner.dto.PartnerDto;
import com.ready.weddingplanner.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PartnerService partnerService;
    private final UserService userService;

    @Transactional
    public User signup(AuthDto.SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        // After saving the user, create a partner link if applicable
        if ((savedUser.getRole() == User.Role.GROOM || savedUser.getRole() == User.Role.BRIDE) && request.getPartnerEmail() != null) {
            // Temporarily set the new user as the current user for the partner service call
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            PartnerDto.PartnerRequest partnerRequest = new PartnerDto.PartnerRequest();
            partnerRequest.setPartnerEmail(request.getPartnerEmail());
            partnerRequest.setWeddingDate(request.getWeddingDate());
            partnerRequest.setBudgetAmount(request.getBudgetAmount());
            partnerService.createPartnerLink(partnerRequest);

            // Clear the temporary security context
            SecurityContextHolder.clearContext();
        }

        return savedUser;
    }

    @Transactional(readOnly = true)
    public User login(AuthDto.LoginRequest request, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public User devLogin(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
