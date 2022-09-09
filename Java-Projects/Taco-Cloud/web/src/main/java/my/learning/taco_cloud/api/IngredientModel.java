package my.learning.taco_cloud.api;

import static my.learning.taco_cloud.entity.Ingredient.Type;

import lombok.Value;
import my.learning.taco_cloud.entity.Ingredient;
import org.springframework.hateoas.RepresentationModel;

@Value
public class IngredientModel extends RepresentationModel<IngredientModel> {
    String name;
    Type type;

    public IngredientModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
