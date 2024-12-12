package com.plb.vinylmgt.configuration.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.plb.vinylmgt.configuration.security.SecurityUtils.AUTHORITIES_KEY;

public class VinylJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(source, extractResourceRoles(source));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt source) {
        String rolesRaw = String.valueOf(source.getClaims().get(AUTHORITIES_KEY));
        return Arrays.stream(rolesRaw.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
