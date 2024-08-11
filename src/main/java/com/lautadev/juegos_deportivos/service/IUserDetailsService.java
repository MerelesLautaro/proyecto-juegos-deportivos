package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.AuthLoginRequestDTO;
import com.lautadev.juegos_deportivos.dto.AuthLoginResponseDTO;
import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.repository.IAccountRepository;
import com.lautadev.juegos_deportivos.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IUserDetailsService implements UserDetailsService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findUserEntityByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        //creamos una lista para los permisos
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //traer roles y convertirlos en 'SimpleGrantedAuthority'
        account.getRoleList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));
        // concatenamos 'ROLE_' para diferenciarlo de los permisos, Spring Security reconoce esto como un ROL


        //traer permisos y convertirlos en 'SimpleGrantedAuthority'
        account.getRoleList().stream()
                .flatMap(role -> role.getPermissionSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermission())));

        return new User(
                account.getUsername(),
                account.getPassword(),
                account.isEnabled(),
                account.isAccountNotExpired(),
                account.isAccountNotExpired(),
                account.isCredentialNotExpired(),
                authorityList
        );
    }

    public AuthLoginResponseDTO loginUser(AuthLoginRequestDTO userRequest) {
        //recuperar usuario y contraseña
        String username = userRequest.username();
        String password = userRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        return new AuthLoginResponseDTO(username,"Login Successful", accessToken, true);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = this.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }
}
