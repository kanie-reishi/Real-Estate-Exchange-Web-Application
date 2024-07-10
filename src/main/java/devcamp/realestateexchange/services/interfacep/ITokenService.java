package devcamp.realestateexchange.services.interfacep;
import devcamp.realestateexchange.entity.authentication.Token;
public interface ITokenService {

    Token createToken(Token token);

    Token findByToken(String token);
}
