package my.learning.taco_cloud.data;

import my.learning.taco_cloud.Order;

public interface OrderRepository {
    Order save(Order order);
}
