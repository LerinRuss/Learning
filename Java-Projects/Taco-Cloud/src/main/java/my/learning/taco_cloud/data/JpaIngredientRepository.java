package my.learning.taco_cloud.data;

import my.learning.taco_cloud.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface JpaIngredientRepository extends CrudRepository<Ingredient, String> {
}
