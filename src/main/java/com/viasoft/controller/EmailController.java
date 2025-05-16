package com.viasoft.controller;



import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class EmailController {



//    @PostMapping
//    public ResponseEntity<String> processEmail(@RequestBody @Valid EmailDTO request) {
//        emailService.processEmail(request);
//        return ResponseEntity.ok("E-mail processado com sucesso!");
//    }
}
