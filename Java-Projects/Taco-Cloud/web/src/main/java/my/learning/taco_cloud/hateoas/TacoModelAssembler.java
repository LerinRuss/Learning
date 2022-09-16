package my.learning.taco_cloud.hateoas;

import my.learning.taco_cloud.api.RecentTacosController;
import my.learning.taco_cloud.entity.Taco;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TacoModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {
    public TacoModelAssembler() {
        super(RecentTacosController.class, TacoModel.class);
    }

    @Override
    protected TacoModel instantiateModel(Taco entity) {
        return new TacoModel(entity);
    }

    @Override
    public TacoModel toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity);
    }
}
