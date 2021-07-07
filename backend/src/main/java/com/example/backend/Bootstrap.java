package com.example.backend;

import com.example.backend.user.model.ERole;
import com.example.backend.user.model.Role;
import com.example.backend.user.model.User;
import com.example.backend.user.repository.RoleRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Random;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${app.boostrap}")
    private boolean bootstrap;

    private String getRandomName(String[] names) {
        int randomIndex = new Random().nextInt(names.length);
        return names[randomIndex];
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(Role.builder().name(value).build());
            }

            Role admin = roleRepository.findByName(ERole.ADMINISTRATOR)
                    .orElseThrow(() -> new EntityNotFoundException("Couldn't find role: ADMINISTRATOR."));
            Role regularUser = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new EntityNotFoundException("Couldn't find role: USER"));

            String[] names = {"Zi Quintero", "Arun Li", "Rikesh Singleton", "Fahim Craig", "Ema Crowther", "Hawa Santiago", "Xena Hull", "Rhodri Amin", "Leona Klein", "Ceara Gamble", "Bushra Sutherland", "Erika Emerson", "Reis Duggan", "Evalyn Bellamy", "Finlay Burks,Estelle Phelps", "Cairo Walsh", "Gracie-May Guest", "Eden Martin", "Evie-Rose Knights", "Gino King", "Danyal Whitley"};

            for(int i = 0; i < 25; i++) {
                User user = User.builder().username("User" + i).email("my_email" + i + "@gmail.com").password("my_pass" + i)
                        .fullName(getRandomName(names))
                        .roles(new HashSet<Role>(asList(regularUser))).build();
                userRepository.save(user);
            }

            User adminUser = User.builder().username("Admin Bob").email("admin_pass_bob@gmail.com").password("adminPass")
                    .fullName(getRandomName(names))
                    .roles(new HashSet<Role>(asList(admin))).build();
            userRepository.save(adminUser);

            User adminAndUser = User.builder().username("Admin/User Marty").email("admin_RegUser_marty@gmail.com").password("anotherAdminPass")
                    .fullName(getRandomName(names))
                    .roles(new HashSet<Role>(asList(admin, regularUser))).build();
            userRepository.save(adminAndUser);


}
    }}
