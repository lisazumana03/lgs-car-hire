package za.co.carhire.service.authentication.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.util.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private final IUserRepository repository;

    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getPassword() != null && !user.getPassword().startsWith("hashed_")) {
            user.setPassword(hashPassword(user.getPassword()));
        }

        return repository.save(user);
    }

    @Override
    public User read(Integer userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public User update(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null");
        }

        if (repository.existsById(user.getUserId())) {
            if (user.getPassword() != null && !user.getPassword().startsWith("hashed_")) {
                user.setPassword(hashPassword(user.getPassword()));
            }
            return repository.save(user);
        }
        return null;
    }

    @Override
    public void delete(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        repository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByIdNumber(Long idNumber) {
        return repository.findUserByIdNumber(idNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public User authenticate(String email, String password) {
        if (Helper.isEmptyOrNull(email) || Helper.isEmptyOrNull(password)) {
            return null;
        }

        User user = findByEmail(email);
        if (user != null) {
            String hashedPassword = hashPassword(password);
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User register(User user) {
        if (user == null || !isValidUser(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }

        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        List<User> existingUsers = findByIdNumber(user.getIdNumber());
        if (!existingUsers.isEmpty()) {
            throw new IllegalArgumentException("ID number already registered");
        }

        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }

        return save(user);
    }

    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null || Helper.isEmptyOrNull(oldPassword) || Helper.isEmptyOrNull(newPassword)) {
            return false;
        }

        User user = read(userId);
        if (user != null) {
            String hashedOldPassword = hashPassword(oldPassword);
            if (hashedOldPassword.equals(user.getPassword())) {
                user.setPassword(hashPassword(newPassword));
                update(user);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByRole(UserRole role) {
        return repository.findAll().stream()
                .filter(user -> role.equals(user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllCustomers() {
        return findByRole(UserRole.CUSTOMER);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllAdmins() {
        return findByRole(UserRole.ADMIN);
    }

    @Override
    public boolean promoteToAdmin(Integer userId) {
        User user = read(userId);
        if (user != null && UserRole.CUSTOMER.equals(user.getRole())) {
            user.setRole(UserRole.ADMIN);
            update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean demoteToCustomer(Integer userId) {
        User user = read(userId);
        if (user != null && UserRole.ADMIN.equals(user.getRole())) {
            user.setRole(UserRole.CUSTOMER);
            update(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        if (Helper.isEmptyOrNull(email)) {
            return null;
        }

        return repository.findAll().stream()
                .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }

        if (Helper.isEmptyOrNull(user.getName()) ||
                !Helper.isValidEmail(user.getEmail()) ||
                Helper.isEmptyOrNull(user.getPassword()) ||
                user.getIdNumber() == null ||
                user.getDateOfBirth() == null ||
                Helper.isEmptyOrNull(user.getPhoneNumber())) {
            return false;
        }

        if (UserRole.CUSTOMER.equals(user.getRole()) &&
                Helper.isEmptyOrNull(user.getLicenseNumber())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canRentCar(Integer userId) {
        User user = read(userId);
        if (user == null) {
            return false;
        }

        return UserRole.CUSTOMER.equals(user.getRole()) &&
                !Helper.isEmptyOrNull(user.getLicenseNumber());
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return "hashed_" + Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            // Fallback to simple prefix (not secure, just for demo)
            return "hashed_" + password;
        }
    }
}