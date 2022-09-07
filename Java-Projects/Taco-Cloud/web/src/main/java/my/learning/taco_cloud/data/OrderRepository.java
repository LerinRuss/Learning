package my.learning.taco_cloud.data;

import java.util.List;
import my.learning.taco_cloud.entity.Order;
import my.learning.taco_cloud.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
