package my.learning.taco_cloud.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.data.TacoRepository;
import my.learning.taco_cloud.entity.Taco;
import my.learning.taco_cloud.hateoas.TacoModel;
import my.learning.taco_cloud.hateoas.TacoModelAssembler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping(path = "/design", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DesignTacoController {
    private final TacoRepository tacoRepository;

    @GetMapping("/recent")
    public CollectionModel<TacoModel> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

        CollectionModel<TacoModel> tacoModels =
                new TacoModelAssembler().toCollectionModel(tacos);
        tacoModels.add(
                linkTo(methodOn(DesignTacoController.class).recentTacos())
                    .withRel("recents"));

        return tacoModels;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable Long id) {
        return ResponseEntity.of(tacoRepository.findById(id));
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Taco saveTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }
}
