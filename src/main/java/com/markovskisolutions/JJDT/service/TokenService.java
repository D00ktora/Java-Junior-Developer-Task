package com.markovskisolutions.JJDT.service;

import org.springframework.security.core.Authentication;


public interface TokenService {
    String generateToken(Authentication authentication);
}
