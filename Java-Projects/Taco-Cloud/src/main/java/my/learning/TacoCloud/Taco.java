package my.learning.TacoCloud;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Taco {
    private String name;
    private List<String> ingredients;
}
