package za.co.carhire.repository.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.authentication.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    List<User> findUserById(int idNumber);




}
