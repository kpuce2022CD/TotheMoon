package Youtube.SpringbootServer.config;

import Youtube.SpringbootServer.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //인터셉터 등록.
    @Override
    public void addInterceptors(InterceptorRegistry registry){

        //login 인터셉터 등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/members/add", "/login", "/logout", "/home", "/getcomments/**",
                        "/getkeyword/**", "/videoinfo/**", "/timeline/**", "/interest/**", "/findcomment/**",
                        "/css/**", "/*.ico", "/error","/oauth2/authorization/google/**","/auth/**","/oauth2/**"
                );
    }
}
