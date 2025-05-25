package za.co.carhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.service.authentication.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public User create(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping("/{id}")
    public User read(@PathVariable Integer id) {
        return service.read(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public List<User> getAll() {
        return service.findAll();
    }
}
