package devcamp.realestateexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.entity.authentication.Token;
import devcamp.realestateexchange.repositories.authentication.ITokenRepository;
import devcamp.realestateexchange.services.interfacep.ITokenService;

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