package com.ydova.ahub.service;

import com.ydova.ahub.dto.LoginDto;
import com.ydova.ahub.entity.AppUser;
import com.ydova.ahub.repository.AppUserRepository;
import com.ydova.cv.YdovaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private Set<String> keys = Set.of("1209981c-f604-4d52-bbb4-9ae187ca4806", "5f7a6f6e-4b9b-467c-a90a-94bbe15602ec",
            "a9546ece-dc24-4615-9d72-f5b434465481", "test");

    private final AppUserRepository userRepository;

    @Autowired
    UserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public AppUser create(AppUser user) throws YdovaException {

        if (user.getKey() == null) {
            throw new YdovaException("Invalid Permission Key");
        }

        if (keys.contains(user.getKey())) {
            List<AppUser> users = userRepository.findByKey(user.getKey());

            if (!users.isEmpty() && !user.getKey().equals("test")) {
                throw new YdovaException("Invalid permission key or already in use");
            }
        }


        List<AppUser> userList = userRepository.findByEmail(user.getEmail());

        if (!userList.isEmpty()) {
            throw new YdovaException("User " + user.getEmail() + " already exists");
        }

        if (!keys.contains(user.getKey())) {
            throw new YdovaException("Invalid Permission Key");
        }
        return userRepository.save(user);
    }


    public AppUser authenticate(LoginDto user) throws YdovaException {

        List<AppUser> userList = userRepository.findByEmail(user.getEmail());

        if (userList.isEmpty()) {
            throw new YdovaException("User does not  exists");
        }

        AppUser authenticatedUser = userList.get(0);
        if (!authenticatedUser.getPassword().equals(user.getPassword())) {
            throw new YdovaException("Invalid Password");
        } else return authenticatedUser;
    }
}
