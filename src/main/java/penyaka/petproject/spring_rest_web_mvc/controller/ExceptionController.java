package penyaka.petproject.spring_rest_web_mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import penyaka.petproject.spring_rest_web_mvc.exception.NotFoundException;

//@ControllerAdvice
public class ExceptionController {
    //@ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException () {
        return ResponseEntity.notFound().build();
    }
}
