package pro.sorokovsky.sorokchatserverspring.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() throws IOException {
        final var resource = new ClassPathResource("static/openapi.yml");
        final var openApiContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        return new OpenAPIV3Parser().readContents(openApiContent, null, null).getOpenAPI();
    }
}
