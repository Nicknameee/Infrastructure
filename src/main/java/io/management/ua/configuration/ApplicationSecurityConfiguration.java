package io.management.ua.configuration;

import io.management.ua.configuration.entries.AuthenticationEntryPoint;
import io.management.ua.configuration.filters.AuthorizationBasicRequestFilter;
import io.management.ua.configuration.filters.AuthorizationTokenRequestFilter;
import io.management.ua.configuration.filters.PreLogoutTokenBasedFilter;
import io.management.ua.configuration.handlers.AuthenticationLogoutSecurityHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfiguration {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationLogoutSecurityHandler authenticationLogoutSecurityHandler;
    private final UserDetailsService userDetailsService;
    private final AuthorizationTokenRequestFilter authorizationTokenRequestFilter;
    private final AuthorizationBasicRequestFilter authorizationBasicRequestFilter;
    private final PreLogoutTokenBasedFilter preLogoutTokenBasedFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Order(1)
    protected SecurityFilterChain configureAPIFilterChain(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/api/**");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authorizationTokenRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(preLogoutTokenBasedFilter, LogoutFilter.class);
        http
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest()
                .denyAll()
                .and()
                .logout()
                .logoutSuccessHandler(authenticationLogoutSecurityHandler)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.POST.name()))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain configurePublicFilterChain(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/ws/**", "/util/**", "/**/allowed")
                .antMatchers(HttpMethod.POST, "/login", "/logout");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/logout").permitAll()
                .antMatchers("/ws/**", "/util/**", "/**/allowed").permitAll()
                .anyRequest()
                .denyAll();
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        return http.build();
    }

    @Bean
    @Order(5)
    public SecurityFilterChain httpBasicInternalFilterChain(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/actuator/**");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .httpBasic();
        http
                .addFilterBefore(authorizationBasicRequestFilter, BasicAuthenticationFilter.class);
        http
                .authorizeRequests()
                .antMatchers("/actuator/**").hasAnyRole("MONITOR", "DISCOVERY", "USER")
                .anyRequest()
                .denyAll();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true)
                .ignoring()
                .antMatchers();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    protected DaoAuthenticationProvider repositoryAuthenticationProvider() {
        DaoAuthenticationProvider repositoryAuthenticationProvider = new DaoAuthenticationProvider();
        repositoryAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        repositoryAuthenticationProvider.setUserDetailsService(userDetailsService);
        return repositoryAuthenticationProvider;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
