package com.likeurator.squadmania_auth.domain.user;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.likeurator.squadmania_auth.domain.user.model.Userinfo;

import lombok.*;

@Service
@RequiredArgsConstructor
@Transactional
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
