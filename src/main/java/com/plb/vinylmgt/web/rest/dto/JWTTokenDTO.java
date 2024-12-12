package com.plb.vinylmgt.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JWTTokenDTO(@JsonProperty("id_token") String idToken) {
}
