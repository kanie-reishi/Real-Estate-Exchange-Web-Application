package devcamp.realestateexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.Token;

public interface ITokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);
}