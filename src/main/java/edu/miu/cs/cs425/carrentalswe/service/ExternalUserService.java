package edu.miu.cs.cs425.carrentalswe.service;

import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExternalUserService {

    List<ExternalUser> getUsers();
    ExternalUser getOneUser(Long id);
    ExternalUser registerUser(ExternalUser externalUser);
    ExternalUser findByUser(User user);


}