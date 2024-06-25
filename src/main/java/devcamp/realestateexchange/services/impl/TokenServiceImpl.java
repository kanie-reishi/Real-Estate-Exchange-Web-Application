package devcamp.realestateexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.entity.Token;
import devcamp.realestateexchange.repositories.ITokenRepository;
import devcamp.realestateexchange.services.ITokenService;

@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private ITokenRepository tokenRepository;

    public Token createToken(Token token) {
        return tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}