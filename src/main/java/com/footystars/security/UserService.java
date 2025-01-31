package com.footystars.security;

import com.footystars.model.entity.User;
import com.footystars.persistence.repository.UserRepository;
import com.footystars.utils.LogsNames;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for managing user-related operations, including user lookup and authentication.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user.
     * @return an {@link Optional} containing the user if found, otherwise empty.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves a new user to the repository.
     *
     * @param newUser the {@link User} entity to be saved.
     */
    public void save(User newUser) {
        userRepository.save(newUser);
    }

    /**
     * Loads a user by their username (email).
     * This method is used by Spring Security for authentication.
     *
     * @param username the username (email) of the user.
     * @return the {@link UserDetails} of the found user.
     * @throws UsernameNotFoundException if no user is found with the given email.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(LogsNames.USERNAME_EXCEPTION + username));
    }
}
