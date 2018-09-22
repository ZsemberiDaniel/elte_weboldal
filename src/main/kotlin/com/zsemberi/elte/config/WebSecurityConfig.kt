package com.zsemberi.elte.config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    lateinit var dataSource: DataSource

    @Value("\${spring.queries.users-query}")
    lateinit var usersQuery: String

    @Value("\${spring.queries.roles-query}")
    lateinit var rolesQuery: String

    override fun configure(auth: AuthenticationManagerBuilder?) {
        if (auth != null) {
            auth.jdbcAuthentication()
                    .usersByUsernameQuery(usersQuery)
                    .authoritiesByUsernameQuery(rolesQuery)
                    .dataSource(dataSource)
                    .passwordEncoder(bCryptPasswordEncoder)
        }
    }

    override fun configure(http: HttpSecurity?) {
        if (http != null) {
            http.authorizeRequests()
                    .antMatchers("/bootstrap-4.1.3/**", "/jquery/**", "/login/**", "/register/**",
                            "/images/**", "/szamrend/first").permitAll()
                    .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                    .anyRequest().authenticated()
            .and().formLogin()
                    .loginPage("/login/loginPage")
                    .loginProcessingUrl("/login/process")
                    .usernameParameter("name")
                    .passwordParameter("password")
                    .permitAll()
            .and().logout()
                    .permitAll()
            .and().exceptionHandling()
                    .accessDeniedPage("/login/accessDenied")

        }
    }

    override fun configure(web: WebSecurity?) {
        if (web != null) {
            web.ignoring()
                    .antMatchers("/bootstrap-4.1.3/**", "/jquery/**", "/images/**")
        }
    }
}