package org.baeldung.spring;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import utils.BD;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("org.baeldung.web.controller")).paths(PathSelectors.ant("/*"))
				.build().apiInfo(apiInfo()).useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET,
						newArrayList(
								new ResponseMessageBuilder().code(500).message("500 message")
										.responseModel(new ModelRef("Error")).build(),
								new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build()));
	}

	private ApiInfo apiInfo() {
		
		
		String mode = "<u>Modo:</u> " + (BD.justRead?"<i>Solo Lectura<i>":"</i>Lectura + Edicion</i>");
		String ambiente = "<u>Ambiente:</u> " + (BD.local?"<i>Local (para desarrollo)</i>":"<i>Real (dureka online)</i>");

		ApiInfo apiInfo = new ApiInfo("DUREKA API v2.2", mode + "<br>" + ambiente, "", "", "", "", "");
		return apiInfo;
	}
}
