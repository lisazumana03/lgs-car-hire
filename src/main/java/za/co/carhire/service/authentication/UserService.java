package za.co.carhire.service.authentication;

import za.co.carhire.domain.authentication.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User read(Integer userId);

    User update(User user);

    void delete(Integer userId);

    List<User> findAll();

    List<User> findByIdNumber(Long idNumber);

    // New methods for login and registration
    User findByEmailAndPassword(String email, String password);

}
