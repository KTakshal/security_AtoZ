package com.example.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

//  @PreAuthorize("hasRole('Admin')")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/admin/hello")
    public String sayAdminHello(){
        return "Hello,Admin";
    }


    @GetMapping("/user/hello")
    public String sayUserHello(){
        return "Hello,User";
    }
}
