package devcamp.realestateexchange.security.services;


import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginAttemptService {

    private final StringRedisTemplate redisTemplate;
    private final int MAX_ATTEMPT = 5;

    public LoginAttemptService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void resetLoginAttempts(String key) {
        redisTemplate.delete(key);
    }

    public boolean incrementLoginAttemptsAndCheck(String username) {

        if (username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Username must not be null");
        }

        Long previousAttemptLong = redisTemplate.opsForValue().increment(username);
        long previousAttempt = (previousAttemptLong != null) ? previousAttemptLong : 0;

        // Set the key to expire after 1 day
        redisTemplate.expire(username, 1, TimeUnit.DAYS);
        return previousAttempt >= MAX_ATTEMPT;
    }

    public boolean isLogin(String username) {
        return redisTemplate.hasKey(username);
    }
}
