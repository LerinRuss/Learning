package my.learning.taco_cloud.api;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.data.TacoRepository;
import my.learning.taco_cloud.entity.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/design", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DesignTacoController {
    private final TacoRepository tacoRepository;
    private final EntityLinks entityLinks;

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable Long id) {
        return ResponseEntity.of(tacoRepository.findById(id));
    }


}
