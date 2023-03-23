package com.likeurator.squadmania_auth.domain.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likeurator.squadmania_auth.auth.AuthenticationRequest;
import com.likeurator.squadmania_auth.auth.AuthenticationResponse;
import com.likeurator.squadmania_auth.auth.AuthenticationService;
import com.likeurator.squadmania_auth.auth.RestRequest;

import lombok.extern.slf4j.Slf4j;
import lombok.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final AuthenticationManager authenticationManager;

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

    //정보수정
    //test 필요
    public UserUpdateResponse findAndUpdateUser(RestRequest request, String password){
        var responseBody = userRepository.findByEmail(request.getEmail_id())
            .orElseThrow(null);

        if(responseBody.getEmailId()!=request.getEmail_id() || responseBody.getPassword()!=request.getPassword()){
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail_id(), request.getPassword())
            );    
            responseBody.setEmailId(request.getEmail_id());
            responseBody.setPassword(request.getPassword());
            
            userRepository.save(responseBody);
        }
        /*
            그 외 정보 수정할 목록
        */
    
        return UserUpdateResponse.builder()
            .emailId(request.getEmail_id())
            .password(request.getPassword())
        .build();
    }

    public Userinfo signUpUser(Userinfo user){
        return userRepository.save(user);
    }

    public void signOutUser(Userinfo user){
        userRepository.delete(user);
    }
    
}
