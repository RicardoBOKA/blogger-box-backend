package com.dauphine.blogger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
        name = "Hello World !",
        description = "My first hello world endpoints"
)
public class HelloWorldController {

    @GetMapping("hello-world")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("hello")
    public String helloByName(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("hello/{name}")
    @Operation(
            summary = "Hello by name endpoint",
            description = "Returns 'Hello {name}' by path variable."
    )
    public String hello(
            @Parameter(description = "Name to Welcome")
            @PathVariable String name
    ) {
        return "Hello " + name;
    }
}
