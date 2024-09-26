package br.com.cadastroit.security.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folders/navigate")
public class FoldersService {

    @GetMapping("/main/{user}")
    public ResponseEntity<Object> mainFolder(@PathVariable("user") String user){
        return ResponseEntity.ok("Main folder para "+user);
    }

}
