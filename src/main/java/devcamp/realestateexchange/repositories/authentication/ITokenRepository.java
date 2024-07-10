package devcamp.realestateexchange.repositories.authentication;

import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.authentication.Token;

public interface ITokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);
}