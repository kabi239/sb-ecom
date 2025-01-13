package com.ecommerce.sb_ecom.security;

import com.ecommerce.sb_ecom.model.AppRole;
import com.ecommerce.sb_ecom.model.Role;
import com.ecommerce.sb_ecom.model.User;
import com.ecommerce.sb_ecom.repository.RoleRepository;
import com.ecommerce.sb_ecom.repository.UserRepository;
import com.ecommerce.sb_ecom.security.jwt.AuthEntryPointJwt;
import com.ecommerce.sb_ecom.security.jwt.AuthTokenFilter;
import com.ecommerce.sb_ecom.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenJwtFilter(){
        return new AuthTokenFilter();
    }// this will intercept the request and check for valid JWT token

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    } // this is to make sure user are fetched in a logic we have defined in userDetailsService

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll() // pages like signin n all
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/admin/**").permitAll() // enabling for testing right now
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("swagger-ui/**").permitAll() // swagger is used for api documentation
                                .requestMatchers("api/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
        );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers( headers-> headers.frameOptions(
                frameOptions-> frameOptions.sameOrigin()));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return webSecurity -> webSecurity.ignoring().requestMatchers("/v2/api-docs/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/webjars/**",
                "configuration/security",
                "swagger-ui.html");
    }// this will completely exclude these paths from security filter chain
    // this is done at global level

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository,PasswordEncoder passwordEncoder){
        return args -> {
            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(()->{Role newUser=new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUser);
                    });
            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(()->{Role newSeller=new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSeller);
                    });
            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(()->{Role newAdmin=new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdmin);
                    });
            Set<Role> userRoles= Set.of(userRole);
            Set<Role> sellerRoles= Set.of(sellerRole);
            Set<Role> adminRoles= Set.of(userRole,sellerRole,adminRole);

            //Create users if not present
            if(!userRepository.existsByUsername("user1")){
                User user1 = new User("user","user@egemail.com",passwordEncoder.encode("userPassword"));
                userRepository.save(user1);
            }
            if(!userRepository.existsByUsername("seller1")){
                User seller1 = new User("seller","seller@egemail.com",passwordEncoder.encode("sellerPassword"));
                userRepository.save(seller1);
            }
            if(!userRepository.existsByUsername("admin")){
                User admin = new User("admin","admin@egemail.com",passwordEncoder.encode("adminPassword"));
                userRepository.save(admin);
            }
            //Update roles for existing users
            userRepository.findByUsername("user1").isPresent(user->{
                user.setRole(userRoles);
                userRepository.save(user);
            });
            userRepository.findByUsername("seller1").isPresent(seller->{
                seller.setRole(sellerRoles);
                userRepository.save(seller);
            });
            userRepository.findByUsername("admin").isPresent(admin->{
                admin.setRole(adminRoles);
                userRepository.save(admin);
            });
        };
    }

}
