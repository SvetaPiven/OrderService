package org.order.entity.mongo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mongo")
public class UserController {

  private final UserRepository userRepository;

  @PostMapping
  public User addNewUsers(@RequestBody User user) {
    return userRepository.save(user);
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{userId}")
  public User getUser(@PathVariable String userId) {
    return userRepository.findById(userId).get();
  }

}
