package za.co.carhire.service.authentication;

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.authentication.UserRole;
import za.co.carhire.dto.authentication.UserDTO;

import java.util.List;

public interface UserService {

    User save(User user);
    User read(Integer userId);
    User update(User user);
    void delete(Integer userId);
    List<User> findAll();
    List<User> findByIdNumber(Long idNumber);

    User authenticate(String email, String password);
    User register(User user);
    boolean changePassword(Integer userId, String oldPassword, String newPassword);

    List<User> findByRole(UserRole role);
    List<User> findAllCustomers();
    List<User> findAllAdmins();
    boolean promoteToAdmin(Integer userId);
    boolean demoteToCustomer(Integer userId);

    User findByEmail(String email);
    boolean emailExists(String email);

    boolean isValidUser(User user);
    boolean canRentCar(Integer userId);
}