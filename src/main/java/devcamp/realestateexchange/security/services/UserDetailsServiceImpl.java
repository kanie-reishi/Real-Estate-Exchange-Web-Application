package devcamp.realestateexchange.security.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import devcamp.realestateexchange.entity.Permission;
import devcamp.realestateexchange.entity.User;
import devcamp.realestateexchange.entity.Role;
import devcamp.realestateexchange.entity.ERole;
import devcamp.realestateexchange.repositories.IUserRepository;
import devcamp.realestateexchange.repositories.IRoleRepository;
import devcamp.realestateexchange.security.jwt.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private IRoleRepository roleRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleKey()));
      for (Permission permission : role.getPermissions()) {
        grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
      }
    }
    return UserDetailsImpl.build(user);
  }

  public User createUser(User user) {
    Role userRole = roleRepository.findByRoleKey(ERole.ROLE_USER.name())
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    user.setRoles(Collections.singleton(userRole));
    return userRepository.saveAndFlush(user);
  }

  // Get user from token
  public User whoami(HttpServletRequest req) {
    String token = resolveToken(req);

    String username = jwtUtils.getUserNameFromJwtToken(token);

    return userRepository.findByUsername(username).get();
  }

  // Resolve token from request
  private String resolveToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("token".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

}