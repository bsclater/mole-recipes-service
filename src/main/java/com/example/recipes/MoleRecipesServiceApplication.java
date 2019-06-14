package com.example.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories("com.example.database") 
@EntityScan("com.example.database")
@SpringBootApplication
@EnableSwagger2
public class MoleRecipesServiceApplication {

	@Bean
	public Docket api() {
		String requestPath = "/api/recipes.*";
		String restAPITitle = "Mole Recipes Service REST API";
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex(requestPath))
				.build()
				.apiInfo(new ApiInfo(restAPITitle, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
	}

	public static void main(String[] args) {
		SpringApplication.run(MoleRecipesServiceApplication.class, args);
	}

}