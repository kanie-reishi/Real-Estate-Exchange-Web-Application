package devcamp.realestateexchange.services;
import devcamp.realestateexchange.entity.Token;
public interface ITokenService {

    Token createToken(Token token);

    Token findByToken(String token);
}
