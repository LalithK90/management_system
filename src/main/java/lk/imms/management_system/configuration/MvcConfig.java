package lk.imms.management_system.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@EnableWebMvc
@EnableCaching
public class MvcConfig implements WebMvcConfigurer {
    //to activate the thymeleaf decouple logic - start
    // == fields ==
    private final SpringResourceTemplateResolver templateResolver;

    // == constructor
    public MvcConfig(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    // == init ==
    @PostConstruct
    public void init() {
        templateResolver.setUseDecoupledLogic(true);
    }
//to activate the thymeleaf decouple logic - end

    //config web mvc config - start
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/login");
        registry.addViewController("/login")
                .setViewName("login/login");
        registry.addViewController("/mainWindow")
                .setViewName("mainWindow");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
    //config web mvc config - end

    //time zone set to
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Colombo"));
    }

    //Manage all errors using one template and one method
    @ControllerAdvice
    public static class ErrorController {

        private Logger logger = LoggerFactory.getLogger(ErrorController.class);

        @ExceptionHandler( Throwable.class )
        @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
        public String exception(final Throwable throwable, Model model) {
            logger.error("Exception during execution of SpringSecurity application", throwable);
            String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
            model.addAttribute("errorMessage", errorMessage);
            return "error/error";
        }
    }
    //to enable Cache in spring boot
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}

