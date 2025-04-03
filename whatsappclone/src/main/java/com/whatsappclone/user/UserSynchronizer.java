package com.whatsappclone.user;

import com.whatsappclone.mapper.UserMapper;
import com.whatsappclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdd(Jwt token) {
        log.info("Synchronizer user with idp");
        getUserEmail(token).ifPresent(userEmail ->{
            log.info("syncro user having email {}",userEmail);
            Optional<User> optUser=userRepository.findByEmail(userEmail);
            User user=userMapper.fromTokenAttributes(token.getClaims());
            optUser.ifPresent(value->user.setId(optUser.get().getId()));
            userRepository.save(user);
        });

    }

    private Optional<String> getUserEmail(Jwt token){
        Map<String ,Object> attributes=token.getClaims();
        if (attributes.containsKey("email")){
            return Optional.of(attributes.get("email").toString());
        }
        return Optional.empty();
    }

}
