package com.viasoft.controller;


import com.viasoft.dto.EmailDTO;
import com.viasoft.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {


    private final EmailService emailService;
    @PostMapping
    public ResponseEntity<Void> enviarEmail(@Valid @RequestBody EmailDTO email) {
        emailService.processarEmail(email);
        return ResponseEntity.noContent().build();
    }
}
