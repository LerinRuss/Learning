package my.learning.taco_cloud.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.data.TacoRepository;
import my.learning.taco_cloud.entity.Taco;
import my.learning.taco_cloud.hateoas.TacoModel;
import my.learning.taco_cloud.hateoas.TacoModelAssembler;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
@RepositoryRestController
@RequiredArgsConstructor
public class RecentTacosController {
    private final TacoRepository tacoRepository;

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoModel>> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

        CollectionModel<TacoModel> tacoModels = new TacoModelAssembler().toCollectionModel(tacos)
                .add(linkTo(methodOn(RecentTacosController.class).recentTacos())
                        .withRel("recents"));

        return ResponseEntity.ok(tacoModels);
    }
}
