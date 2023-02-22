package com.likeurator.squadmania_auth.domain.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import lombok.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    @Autowired UserRepository userRepository;

    public Userinfo saveUser(Userinfo user){
        return userRepository.save(user);
    }

    public Optional<Userinfo> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<Userinfo> findAllUsers(){
        return userRepository.findAll();
    }

    public Userinfo getUserReferencedById(Long id){
        return userRepository.getReferenceById(id);
    }

    public Userinfo findAndUpdateUser(Long id, Userinfo user){
        Userinfo responseBody = userRepository.getReferenceById(id);
        responseBody.setEmailId(user.getEmailId());
        
        return userRepository.save(responseBody);
    }

    public Userinfo signUpUser(Userinfo user){
        return userRepository.save(user);
    }

    public void signOutUser(Userinfo user){
        userRepository.delete(user);
    }
    
}
