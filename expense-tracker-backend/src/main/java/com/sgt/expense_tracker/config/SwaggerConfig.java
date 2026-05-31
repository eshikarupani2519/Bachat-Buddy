package com.sgt.expense_tracker.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.PathItem;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer loginEndpointCustomizer() {
        return openApi -> {
            // Define the schema for the login form body
            Schema<?> loginSchema = new ObjectSchema()
                    .addProperty("email", new StringSchema()
                            .example("user@example.com")
                            .format("email"))
                    .addProperty("password", new StringSchema()
                            .example("password")
                            .format("password"));

            // Build the POST /login operation
            Operation loginOperation = new Operation()
                    .summary("User Login")
                    .description("Spring Security default login endpoint. " +
                            "Submit username & password as form data.")
                    .addTagsItem("Authentication")
                    .requestBody(new RequestBody()
                            .required(true)
                            .content(new Content()
                                    .addMediaType(
                                            "application/x-www-form-urlencoded",  // Must be form data!
                                            new MediaType().schema(loginSchema)
                                    )
                            )
                    )
                    .responses(new ApiResponses()
                            .addApiResponse("302", new ApiResponse()
                                    .description("Redirect on successful login"))
                            .addApiResponse("401", new ApiResponse()
                                    .description("Invalid credentials"))
                    );

            // Register the path
            PathItem loginPath = new PathItem().post(loginOperation);
            openApi.getPaths().addPathItem("/login", loginPath);
        };
    }
}