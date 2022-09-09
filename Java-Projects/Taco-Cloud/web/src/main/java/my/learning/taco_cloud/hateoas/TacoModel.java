package my.learning.taco_cloud.hateoas;

import java.util.Date;
import lombok.Value;
import my.learning.taco_cloud.entity.Taco;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

@Value
public class TacoModel extends RepresentationModel<TacoModel> {
    private static final IngredientModelAssembler ingredientAssembler = new IngredientModelAssembler();

    String name;
    Date createdAt;
    CollectionModel<IngredientModel> ingredients;

    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
    }
}
