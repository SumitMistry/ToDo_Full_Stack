package springboot.ToDo.Swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info
                                ( title = "Todo API Swagger doc by Sumit",
                                version = "1.01",
                                description = "API documentation for Todo web-app",
                                contact = @Contact(name = "http://localhost:8080", email = "sumit@nyc.com",url = "http://localhost:8080"),
                                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
                                )
                    //servers = @Server(url = "http://localhost:8080") ---> no need, will be auto fetched..
                    )

// ---below = If your API requires authentication, add JWT or OAuth2 security to Swagger
//@OpenAPIDefinition(
//        security = { @SecurityRequirement(name = "bearerAuth") }
//)
//@SecuritySchemes({
//        @SecurityScheme(
//                name = "bearerAuth",
//                type = SecuritySchemeType.HTTP,
//                scheme = "bearer",
//                bearerFormat = "JWT"
//        )
//})
public class SwaggerConfig {
}
