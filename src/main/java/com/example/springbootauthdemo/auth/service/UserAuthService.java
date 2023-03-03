package com.example.springbootauthdemo.auth.service;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.auth.entity.UserAuth;
import com.example.springbootauthdemo.auth.exception.AuthenticationTokenNotFoundException;
import com.example.springbootauthdemo.auth.repository.UserAuthRepository;
import com.example.springbootauthdemo.auth.utils.AuthTokenGenerator;
import com.example.springbootauthdemo.auth.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService {

    private final UserAuthPool userAuthPool;
    private final UserAuthRepository userAuthRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final TimeUtils time;

    @Autowired
    public UserAuthService(UserAuthPool userAuthPool,
                           UserAuthRepository userAuthRepository,
                           AuthTokenGenerator authTokenGenerator,
                           TimeUtils time) {

        this.userAuthPool = userAuthPool;
        this.userAuthRepository = userAuthRepository;
        this.authTokenGenerator = authTokenGenerator;
        this.time = time;
    }

    public UserAuth storeAuthForUser(User user) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
        userAuth.setToken(authTokenGenerator.generateToken());
        userAuth.setIssuedAt(time.now());
        userAuth.setExpiresAt(time.calculateTokenExpiryDate(userAuth.getIssuedAt()));
        return userAuthRepository.save(userAuth);
    }

    public void removeAuthentication(@NonNull String authToken) throws AuthenticationTokenNotFoundException {
        Optional<UserAuth> auth = userAuthRepository.findAll()
                .stream()
                .filter(userAuth -> userAuth.getToken().equals(authToken))
                .findFirst();
        if (auth.isPresent()) {
            userAuthRepository.delete(auth.get());
            userAuthPool.removeAuthentication(authToken);
            return;
        }

        throw new AuthenticationTokenNotFoundException("No such a token is found");
    }
}
