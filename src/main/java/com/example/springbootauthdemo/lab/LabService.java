package com.example.springbootauthdemo.lab;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.auth.entity.UserAuth;
import com.example.springbootauthdemo.auth.exception.*;
import com.example.springbootauthdemo.auth.payload.LoginRequestPayload;
import com.example.springbootauthdemo.auth.utils.AuthTokenGenerator;
import com.example.springbootauthdemo.auth.utils.Constants;
import com.example.springbootauthdemo.auth.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LabService {
    private final LabRepository labRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthTokenGenerator authTokenGenerator;
    private final TimeUtils time;
    private final LabAuthService labAuthService;

    @Autowired
    public LabService(BCryptPasswordEncoder passwordEncoder,
                       LabRepository labRepository,
                       AuthTokenGenerator authTokenGenerator,
                       LabAuthService labAuthService,
                       TimeUtils time) {
        this.labRepository = labRepository;
        this.passwordEncoder = passwordEncoder;
        this.authTokenGenerator = authTokenGenerator;
        this.labAuthService= labAuthService;
        this.time = time;
    }

    public Laboratory registerLaboratory(Laboratory laboratory)
            throws PasswordNotMeetingRequirementsException, UsernameReservedException, UserInfoEmptyException {
        Laboratory toRegisterLab = validateLabRegistration(laboratory);
        return labRepository.save(toRegisterLab);
    }

    public LabAuth authenticateLab(LabLoginRequest labLoginRequest) throws LoginRequestPayloadNotValidException, IncorrectUsernamePasswordException {
        if (!validateLoginRequestPayload(labLoginRequest)) {
            throw new LoginRequestPayloadNotValidException("make sure your username and password are valid");
        }
        Laboratory searchedLab = labRepository.findAll()
                .stream()
                .filter(user -> passwordEncoder.matches(labLoginRequest.getPassword(), user.getPassword()))
                .findFirst().orElse(null);
        if (searchedLab == null) {
            throw new IncorrectUsernamePasswordException("could not find user with such credentials");
        }
        return labAuthService.storeAuthForLab(searchedLab);
    }

    private boolean validateLoginRequestPayload(LabLoginRequest payload) {
        if (payload.getPassword() == null || payload.getUsername() == null) {
            return false;
        }

        if (payload.getUsername().isBlank() || payload.getPassword().isEmpty()) {
            return false;
        }

        return true;
    }

    private Laboratory validateLabRegistration(Laboratory laboratory)
            throws PasswordNotMeetingRequirementsException,
            UsernameReservedException,
            UserInfoEmptyException {
        if (labInfoEmpty(laboratory)) {
            throw new UserInfoEmptyException("one of the user credentials is blank");
        }
        if (usernameIsReserved(laboratory)) {
            throw new UsernameReservedException("the username is already existing,");
        }

        if (!passwordMeetsRequirements(laboratory)) {
            throw new PasswordNotMeetingRequirementsException("the password should be at least 8 characters long");
        }

        hashPassword(laboratory);
        return laboratory;
    }

    private boolean usernameIsReserved(Laboratory laboratory) {
        return labRepository.findLabByUsernameEquals(laboratory.getUsername()).isPresent();
    }

    private boolean labInfoEmpty(Laboratory laboratory) {
        if (laboratory.getName() == null || laboratory.getAddress() == null ||
                laboratory.getEmail() == null ||
                laboratory.getUsername() == null) {
            return true;
        }

        if (laboratory.getName().isBlank() || laboratory.getAddress().isBlank() ||
                laboratory.getEmail().isBlank() ||
                laboratory.getUsername().isBlank()) {
            return true;
        }
        return false;
    }

    private boolean passwordMeetsRequirements(Laboratory laboratory) {
        if (laboratory.getPassword() == null) {
            return false;
        }

        return laboratory.getPassword().length() >= Constants.PASSWORD_MIN_LENGTH;
    }

    private Laboratory hashPassword(Laboratory laboratory) {
        laboratory.setPassword(passwordEncoder.encode(laboratory.getPassword()));
        return laboratory;
    }

}
