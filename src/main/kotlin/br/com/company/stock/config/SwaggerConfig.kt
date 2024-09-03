package br.com.company.stock.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(getApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.company.stock"))
            .paths(PathSelectors.any())
            .build()
    }

    private fun getApiInfo(): ApiInfo {
        val contact =
            Contact("Leonardo Sartori", "https://www.linkedin.com/in/sartorileonardo/", "analisysbrazilianstock@gmail.com")
        return ApiInfoBuilder()
            .title("Stock Analisys")
            .description("Analise de empresa brasileira de capital aberto")
            .version("1.0.0")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
            .contact(contact)
            .build()
    }


}