package com.carpooling.common;

import com.carpooling.core.notificationManagment.rest.NotificationDataService;
import com.carpooling.core.notificationManagment.rest.NotificationManager;
import com.carpooling.core.routeManagment.rest.RouteDataService;
import com.carpooling.core.routeManagment.rest.RouteManager;
import com.carpooling.core.userManagement.rest.UserDataService;
import com.carpooling.core.userManagement.rest.UserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {
    @Bean
    public UserDataService createUserDataService(){
        UserDataService u = new UserDataService();
        return u;
    }

    @Bean
    public RouteDataService createRouteDataService(){
        RouteDataService r = new RouteDataService();
        return r;
    }

    @Bean
    public NotificationDataService createChatDataService(){
        NotificationDataService c = new NotificationDataService();
        return c;
    }

    @Bean
    public UserManager createUserManager(){
        UserManager u = new UserManager();
        return u;
    }

    @Bean
    public RouteManager createRouteManager(){
        RouteManager r = new RouteManager();
        return r;
    }

    @Bean
    public NotificationManager createChatManager(){
        NotificationManager c = new NotificationManager();
        return c;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
