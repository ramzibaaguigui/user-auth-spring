package com.example.springbootauthdemo.lab;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.auth.entity.UserAuth;
import com.example.springbootauthdemo.auth.exception.AuthenticationTokenNotFoundException;
import com.example.springbootauthdemo.auth.repository.UserAuthRepository;
import com.example.springbootauthdemo.auth.service.UserAuthPool;
import com.example.springbootauthdemo.auth.utils.AuthTokenGenerator;
import com.example.springbootauthdemo.auth.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabAuthService {
    private final LabAuthPool labAuthPool;
    private final LabAuthRepository labAuthRepository;
    private final AuthTokenGenerator authTokenGenerator;
    private final TimeUtils time;

    @Autowired
    public LabAuthService(LabAuthPool labAuthPool,
                           LabAuthRepository labAuthRepository,
                           AuthTokenGenerator authTokenGenerator,
                           TimeUtils time) {

        this.labAuthPool = labAuthPool;
        this.labAuthRepository = labAuthRepository;
        this.authTokenGenerator = authTokenGenerator;
        this.time = time;
    }

    public LabAuth storeAuthForLab(Laboratory laboratory) {
        LabAuth labAuth = new LabAuth();
        labAuth.setAuthLab(laboratory);
        labAuth.setToken(authTokenGenerator.generateToken());
        labAuth.setIssuedAt(time.now());
        labAuth.setExpiresAt(time.calculateTokenExpiryDate(labAuth.getIssuedAt()));
        return labAuthRepository.save(labAuth);
    }

    public void removeAuthentication(@NonNull String authToken) throws AuthenticationTokenNotFoundException {
        Optional<LabAuth> auth = labAuthRepository.findAll()
                .stream()
                .filter(labAuth -> labAuth.getToken().equals(authToken))
                .findFirst();
        if (auth.isPresent()) {
            labAuthRepository.delete(auth.get());
            labAuthPool.removeAuthentication(authToken);
            return;
        }

        throw new AuthenticationTokenNotFoundException("No such a token is found");
    }
}
