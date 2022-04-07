package my.learning.TacoCloud;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Taco {
    private String name;
    private List<String> ingredients;
}
