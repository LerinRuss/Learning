package my.learning.taco_cloud.data;

import my.learning.taco_cloud.entity.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
