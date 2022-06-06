package my.learning.taco_cloud.data;

import my.learning.taco_cloud.Taco;
import org.springframework.data.repository.CrudRepository;

public interface JpaTacoRepository extends CrudRepository<Taco, Long> {
}
