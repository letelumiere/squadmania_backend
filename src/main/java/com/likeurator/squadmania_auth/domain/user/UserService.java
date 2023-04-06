package com.likeurator.squadmania_auth.domain.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.likeurator.squadmania_auth.auth.AuthenticationRequest;
import com.likeurator.squadmania_auth.auth.AuthenticationResponse;
import com.likeurator.squadmania_auth.auth.AuthenticationService;
import com.likeurator.squadmania_auth.auth.RestRequest;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;
import com.nimbusds.jose.crypto.PasswordBasedDecrypter;
import com.nimbusds.oauth2.sdk.Response;

import lombok.extern.slf4j.Slf4j;
import lombok.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public Userinfo saveUser(Userinfo user){
        return userRepository.save(user);
    }

    public Optional<Userinfo> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<Userinfo> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<Userinfo> getUserReferencedById(UUID id){
        return userRepository.getReferenceByUUID(id);
    }

    public void userUpdate(){


    }
}
