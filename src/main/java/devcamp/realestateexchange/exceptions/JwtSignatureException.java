package devcamp.realestateexchange.exceptions;

import java.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import devcamp.realestateexchange.security.jwt.JwtUtils;

public class JwtSignatureException extends SignatureException {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    public JwtSignatureException(String message) {
        super(message);
        logger.error(message);
    }
}
