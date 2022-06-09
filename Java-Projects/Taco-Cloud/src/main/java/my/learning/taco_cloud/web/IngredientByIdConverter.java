package my.learning.taco_cloud.web;

import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.Ingredient;
import my.learning.taco_cloud.data.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findOne(id);
    }
}
