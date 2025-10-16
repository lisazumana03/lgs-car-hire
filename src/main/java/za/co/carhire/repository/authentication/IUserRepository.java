package za.co.carhire.repository.authentication;

/* INUserepository.java

     IUserNotificationRepository/repository class

     Author: Bonga Velem

     Student Number: 220052379

     */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByIdNumber(Long idNumber);

    Optional<User> findByEmail(String email);

    // Method for login authentication with role verification
    User findByEmailAndPasswordAndRole(String email, String password, Role role);
}
