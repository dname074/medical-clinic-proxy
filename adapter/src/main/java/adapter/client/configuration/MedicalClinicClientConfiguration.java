package adapter.client.configuration;

//import adapter.config.security.TokenService;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@Configuration
@RequiredArgsConstructor
public class MedicalClinicClientConfiguration {
//    private final TokenService tokenService;

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

//    @Bean
//    public RequestInterceptor oauth2RequestInterceptor() {
//        return requestTemplate -> {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth instanceof OAuth2AuthenticationToken oauthToken) {
//                OAuth2AuthorizedClient client = tokenService.getAuthorizedClient(oauthToken);
//                if (client != null) {
//                    System.out.println(client.getAccessToken().getTokenValue());
//                    requestTemplate.header("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
//                }
//            } else {
//                System.out.println("model.User is not logged in or token is not set");
//            }
//        };
//    }
}
