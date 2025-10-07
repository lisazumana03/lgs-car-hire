package za.co.carhire.service.authentication.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.UserService;

import java.util.List;

@Service
public class temp_UserServiceImpl implements UserService {
    @Autowired
    private final IUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public temp_UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        // Encode password before saving
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repository.save(user);
    }

    @Override
    public User read(Integer userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public User update(User user) {
        if (repository.existsById(user.getUserId())) {
            // Only encode password if it's being changed and not already encoded
            if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return repository.save(user);
        }
        return null;
    }

    @Override
    public void delete(Integer userId) {
        repository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findByIdNumber(Long idNumber) {
        return repository.findUserByIdNumber(idNumber);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }
}
