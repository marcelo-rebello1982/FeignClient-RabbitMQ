package br.com.cadastroit.services.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.SslSettings;

import br.com.cadastroit.security.config.security.RestHeaderAuthFilter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final String pathFilter = System.getenv("pathFilter");
    private final String username = System.getenv("user_mongodb");
    private final String password = System.getenv("pass_mongodb");
    private final String host     = System.getenv("host_mongodb");
    private final String dbName   = System.getenv("name_mongodb");
    private final String port     = System.getenv("port_mongodb");

    @Bean
    public MongoTemplate mongoTemplate() throws Exception{
    	
   
        try {
            MongoClientSettings settings = MongoClientSettings.builder().applyToSslSettings(ssl -> {
                ssl.applySettings(SslSettings.builder().enabled(false).build());
            }).applyToConnectionPoolSettings(pool -> {
                pool.maxSize(50).maxWaitTime(60, TimeUnit.SECONDS)
                        .maxConnectionLifeTime(55, TimeUnit.SECONDS)
                        .maxConnectionIdleTime(50, TimeUnit.SECONDS)
                        .minSize(20)
                        .applyConnectionString(new ConnectionString("mongodb://"+username+":"+password+"@"+host+":"+port))
                        .build();
            }).uuidRepresentation(UuidRepresentation.STANDARD).build();
            MongoClient client = MongoClients.create(settings);
            return new MongoTemplate(client, dbName);
        }catch (Exception ex){
            throw new Exception(String.format("Error on mongoClient, [error] = %s",ex.getMessage()));
        }
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/"+pathFilter+"/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    protected void configure(HttpSecurity http) throws Exception {
        this.log.debug("Defining criteria for http requests");
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .csrf().ignoringAntMatchers("/h2-console/**", "/api/**", "/"+pathFilter+"/", "/folders/navigate/");
        http.authorizeRequests(authorize -> {
                    authorize.antMatchers("/",
                                    "/webjars/**",
                                    "/login",
                                    "/resources/**").permitAll()
                            .antMatchers("/h2-console/**").permitAll() //Do not use in production
                            .antMatchers("/"+pathFilter+"/**").hasAnyRole("ADMIN", "USER")
                            .antMatchers("/folders/navigate/**").hasAnyRole("ADMIN", "USER");
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        //For h2 console config
        http.headers().frameOptions().sameOrigin();
    }
    
    public static String replaceChars(String text) {

		Map<String, String> replacements = Map.of("[", "", "]", "");

		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			text = text.replace(entry.getKey(), entry.getValue());
		}
		return text;
	}
}
