package br.com.cadastroit.security.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoService {

    @GetMapping("/validar")
    public ResponseEntity all(){
        return ResponseEntity.ok("It's working");
    }

    @GetMapping("/name")
    public ResponseEntity name(){
        return ResponseEntity.ok("Demo service!!!");
    }
}
