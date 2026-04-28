//package adapter.config.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class TokenService {
//    private final OAuth2AuthorizedClientService clientService;
//
//    public OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken oauthToken) {
//        return clientService.loadAuthorizedClient(
//                oauthToken.getAuthorizedClientRegistrationId(),
//                oauthToken.getName()
//        );
//    }
//}
