package my.learning.taco_cloud.api;

import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.data.IngredientRepository;
import my.learning.taco_cloud.entity.Ingredient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping(path="/ingredients", produces="application/json")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientRepository ingredientRepository;

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }
}
