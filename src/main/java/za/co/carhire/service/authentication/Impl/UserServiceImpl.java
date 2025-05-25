package za.co.carhire.service.authentication.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.authentication.UserService;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final IUserRepository repository;

    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User read(Integer userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public User update(User user) {
        if (repository.existsById(user.getUserId())) {
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
    public List<User> findByIdNumber(Integer idNumber) {
        return repository.findUserById(idNumber);
    }
}
