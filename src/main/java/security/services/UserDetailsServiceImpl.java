package security.services;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.jwt.JwtUtils;
import devcamp.realestateexchange.entity.User;
import devcamp.realestateexchange.repositories.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserRepository userRepository;
  
    @Autowired
    private JwtUtils jwtUtils;
  
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
  
      return UserDetailsImpl.build(user);
    }
  
    public User createUser(User user){
      return userRepository.saveAndFlush(user);
    }
    // Get user from token
    public User whoami(HttpServletRequest req) {
      String token = resolveToken(req);
  
      String username = jwtUtils.getUserNameFromJwtToken(token);
  
      return userRepository.findByUsername(username).get();
    }
    // Resolve token from request
    private String resolveToken(HttpServletRequest req) {
      String bearerToken = req.getHeader("Authorization");
      if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
      }
      return null;
    }
    public boolean existsByUsername(String username) {
      return userRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email){
      return userRepository.existsByEmail(email);
    }

  }