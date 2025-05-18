package za.co.carhire.repository.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.carhire.domain.authentication.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
