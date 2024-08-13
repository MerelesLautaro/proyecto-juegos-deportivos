package com.lautadev.juegos_deportivos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "API INSCRIPCIÓN JUEGOS DEPORTIVOS",
                description = "Este proyecto es una plataforma de inscripciones para juegos deportivos desarrollada en Java con Spring. \n " +
                                "Permite gestionar las inscripciones de participantes en una variedad de disciplinas y categorías deportivas. \n" +
                                "Este proyecto es una replica de un sistema ya existente, desarrollado para mostrar mis habilidades como desarrollador \n"+
                                "Link del repositorio: [GitHub](https://github.com/MerelesLautaro/proyecto-juegos-deportivos) \n"+
                                "Mi Perfil de GitHub: [GitHub](https://github.com/MerelesLautaro)",
                version = "1.0.0",
                contact = @Contact(
                        name = "Mereles Lautaro",
                        url = "https://www.linkedin.com/in/mereles-lautaro/"
                )
        ),
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Access Token for my API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

}
