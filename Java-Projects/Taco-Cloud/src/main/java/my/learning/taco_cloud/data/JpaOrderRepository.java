package my.learning.taco_cloud.data;

import my.learning.taco_cloud.Order;
import org.springframework.data.repository.CrudRepository;

public interface JpaOrderRepository extends CrudRepository<Order, Long> {
}
