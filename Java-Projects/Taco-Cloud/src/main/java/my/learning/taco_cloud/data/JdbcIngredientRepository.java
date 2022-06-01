package my.learning.taco_cloud.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("SELECT id, name, type FROM Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("SELECT id, name, type FROM Ingredient where id = ?",
                (rs, rowNum) -> new Ingredient(
                        rs.getString("id"),
                        rs.getString("name"),
                        Ingredient.Type.valueOf(rs.getString("type"))));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("INSERT INTO Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
            rs.getString("id"),
            rs.getString("name"),
            Ingredient.Type.valueOf(rs.getString("type")));
    }
}
