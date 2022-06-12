package my.learning.taco_cloud.security;

import my.learning.taco_cloud.User;
import org.springframework.data.repository.CrudRepository;

public interface JpaUserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
