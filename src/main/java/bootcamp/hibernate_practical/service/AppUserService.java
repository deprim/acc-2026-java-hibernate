package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.entity.AppUser;
import bootcamp.hibernate_practical.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository,
                          PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username,
                             String password) {

        AppUser appUser = new AppUser(username, passwordEncoder.encode(password));
        appUserRepository.save(appUser);

    }

}
