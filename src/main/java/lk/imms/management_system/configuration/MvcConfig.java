package lk.imms.management_system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@EnableCaching
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/index");
        registry.addViewController("/login")
                .setViewName("login/login");
        registry.addViewController("/mainWindow")
                .setViewName("mainWindow");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //can be implement like following also
        //registry.addResourceHandler("/resources/**").addResourceLocations("file:/resources/upload/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

    }

    //Bean configuration
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/resources/templates/");
        resolver.setSuffix(".html");
        return resolver;
    }

    //time zone set to
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Colombo"));
    }


    //to enable Cache in spring boot
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    //to activate the thymeleaf decouple logic - start

    // == fields ==
    private final SpringResourceTemplateResolver templateResolver;

    // == constructor
    @Autowired
    public MvcConfig(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    // == init ==

    @PostConstruct
    public void init() {
        templateResolver.setUseDecoupledLogic(true);
    }
/*    @PostConstruct
    public void init1() {
        springTemplateEngine.addDialect(new SpringSecurityDialect());
    }*/
//to activate the thymeleaf decouple logic - end
}