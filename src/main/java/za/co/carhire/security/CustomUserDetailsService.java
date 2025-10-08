package za.co.carhire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.repository.authentication.IUserRepository;

/*
    Imtiyaaz Waggie 219374759
    Date: 09/10/2025
*/

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndPassword(email, null);

        if (user == null) {
            // Try to find user by email only
            user = userRepository.findAll().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        }

        return user;
    }
}
