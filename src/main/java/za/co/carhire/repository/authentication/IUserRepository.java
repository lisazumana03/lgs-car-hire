package za.co.carhire.repository.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.authentication.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
}
