//package adapter.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clients, OAuth2AuthorizedClientService service) {
//        OAuth2AuthorizedClientProvider provider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .clientCredentials()
//                        .refreshToken()
//                        .build();
//        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clients, service);
//        manager.setAuthorizedClientProvider(provider);
//        return manager;
//    }
//
//    @Bean
//    public WebClient oauthWebClient(OAuth2AuthorizedClientManager manager) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager);
//        oauth2.setDefaultOAuth2AuthorizedClient(true);
//        return WebClient.builder()
//                .apply(oauth2.oauth2Configuration())
//                .baseUrl("http://medical-clinic:8080")
//                .build();
//    }
//
//}
