package com.example.exptracker21.service;

import com.example.exptracker21.entities.UserInfo;
import com.example.exptracker21.eventProducer.UserInfoProducer;
import com.example.exptracker21.model.UserInfoData;
import com.example.exptracker21.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoData user) {
        return userRepository.findByUsername(user.getUsername());
    }

    public Boolean signupUser(UserInfoData userInfoData) {
        userInfoData.setPassword(passwordEncoder.encode(userInfoData.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoData))) {
            return false;
        }
        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoData.getUsername(), userInfoData.getPassword(), new HashSet<>()));
        userInfoProducer.sendEventToKafka(userInfoData);
        return true;
    }
}
