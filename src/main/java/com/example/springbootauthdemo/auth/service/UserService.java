package com.example.springbootauthdemo.auth.service;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.auth.entity.UserAuth;
import com.example.springbootauthdemo.auth.exception.*;
import com.example.springbootauthdemo.auth.payload.LoginRequestPayload;
import com.example.springbootauthdemo.auth.repository.UserRepository;
import com.example.springbootauthdemo.auth.utils.AuthTokenGenerator;
import com.example.springbootauthdemo.auth.utils.Constants;
import com.example.springbootauthdemo.auth.utils.TimeUtils;
import com.example.springbootauthdemo.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthTokenGenerator authTokenGenerator;
    private final TimeUtils time;
    private final UserAuthService userAuthService;

    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       AuthTokenGenerator authTokenGenerator,
                       UserAuthService userAuthService,
                       TimeUtils time) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authTokenGenerator = authTokenGenerator;
        this.userAuthService = userAuthService;
        this.time = time;
    }

    public User registerUser(User user)
            throws PasswordNotMeetingRequirementsException, UsernameReservedException, UserInfoEmptyException {
        User toRegisterUser = validateUserRegistration(user);
        return userRepository.save(toRegisterUser);
    }

    public UserAuth authenticateUser(LoginRequestPayload loginRequestPayload) throws LoginRequestPayloadNotValidException, IncorrectUsernamePasswordException {
        if (!validateLoginRequestPayload(loginRequestPayload)) {
            throw new LoginRequestPayloadNotValidException("make sure your username and password are valid");
        }
        User searchedUser = userRepository.findAll()
                .stream()
                .filter(user -> passwordEncoder.matches(loginRequestPayload.getPassword(), user.getPassword()))
                .findFirst().orElse(null);
        if (searchedUser == null) {
            throw new IncorrectUsernamePasswordException("could not find user with such credentials");
        }
        return userAuthService.storeAuthForUser(searchedUser);
    }

    private boolean validateLoginRequestPayload(LoginRequestPayload payload) {
        if (payload.getPassword() == null || payload.getUsername() == null) {
            return false;
        }

        if (payload.getUsername().isBlank() || payload.getPassword().isEmpty()) {
            return false;
        }

        return true;
    }

    private User validateUserRegistration(User user)
            throws PasswordNotMeetingRequirementsException,
            UsernameReservedException,
            UserInfoEmptyException {
        if (userInfoEmpty(user)) {
            throw new UserInfoEmptyException("one of the user credentials is blank");
        }
        if (usernameIsReserved(user)) {
            throw new UsernameReservedException("the username is already existing,");
        }

        if (!passwordMeetsRequirements(user)) {
            throw new PasswordNotMeetingRequirementsException("the password should be at least 8 characters long");
        }

        hashPassword(user);
        return user;
    }

    private boolean usernameIsReserved(User user) {
        return userRepository.findUserByUsernameEquals(user.getUsername()).isPresent();
    }

    private boolean userInfoEmpty(User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getUsername() == null) {
            return true;
        }

        if (user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getUsername().isBlank()) {
            return true;
        }
        return false;
    }

    private boolean passwordMeetsRequirements(User user) {
        if (user.getPassword() == null) {
            return false;
        }

        return user.getPassword().length() >= Constants.PASSWORD_MIN_LENGTH;
    }

    private User hashPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

}
