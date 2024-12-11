package com.plb.vinylmgt.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 999)
public class VinylErrorHandler {

    @ExceptionHandler({VinylNotFoundException.class})
    public ProblemDetail handleVinylNotFoundException(VinylNotFoundException e) {
        ProblemDetail vinylNotFound = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Vinyl Not found");
        vinylNotFound.setTitle("Entity doesn't exist");
        vinylNotFound.setProperty("message", e.getMessage());
        return vinylNotFound;
    }
}
