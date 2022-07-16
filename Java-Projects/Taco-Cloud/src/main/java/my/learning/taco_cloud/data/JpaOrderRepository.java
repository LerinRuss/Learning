package my.learning.taco_cloud.data;

import java.util.List;
import my.learning.taco_cloud.Order;
import my.learning.taco_cloud.User;
import org.springframework.data.repository.CrudRepository;

public interface JpaOrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user);
}
