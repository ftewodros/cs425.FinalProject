package edu.miu.cs.cs425.carrentalswe.service.Impl;

import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.User;
import edu.miu.cs.cs425.carrentalswe.repository.ExternalUserRepository;
import edu.miu.cs.cs425.carrentalswe.service.ExternalUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalUserServiceImpl implements ExternalUserService {

    private ExternalUserRepository externalUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public ExternalUserServiceImpl(ExternalUserRepository externalUserRepository) {
        this.externalUserRepository = externalUserRepository;
    }

    @Override
    public List<ExternalUser> getUsers() {
        return externalUserRepository.findAll();
    }

    @Override
    public ExternalUser getOneUser(Long id) {
        return externalUserRepository.getById(id);
    }

    @Override
    public ExternalUser registerUser(ExternalUser externalUser) {

        if(externalUser.getUser().getPassword()!= null) externalUser.getUser().setPassword(bCryptPasswordEncoder.encode(externalUser.getUser().getPassword()));
        return externalUserRepository.save(externalUser);
    }

    @Override
    public ExternalUser findByUser(User user) {
        return externalUserRepository.findByUser(user);
    }
}
