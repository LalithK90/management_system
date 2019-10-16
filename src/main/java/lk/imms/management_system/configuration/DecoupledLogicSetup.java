package lk.imms.management_system.configuration;

import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;


@Component
public class DecoupledLogicSetup {
    // == fields ==
    private final SpringResourceTemplateResolver templateResolver;

    // == constructor
    public DecoupledLogicSetup(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    // == init ==
    @PostConstruct
    public void init() {
        templateResolver.setUseDecoupledLogic(true);
    }
}
