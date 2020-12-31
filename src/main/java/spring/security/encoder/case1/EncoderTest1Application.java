package spring.security.encoder.case1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Anthony.liu
 * @Description
 * @Date 2020/12/30
 */

@SpringBootApplication
@RestController
//@ServletComponentScan(basePackages = "spring.security.encoder.case1")
public class EncoderTest1Application {
    private Map<String, String> pwdMap = new HashMap<>();
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(EncoderTest1Application.class, args);
    }

    @GetMapping("/sout")
    public String sout(){
        return "sout";
    }

    @GetMapping("/register/{pwd}")
    public String encode(@PathVariable("pwd") String pwd){
        final SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getCredentials());
        final String pwdx = passwordEncoder.encode(pwd);
        pwdMap.put(pwd, pwdx);

        return pwdx;
    }

    @GetMapping("/login1/{pwd}")
    public String login(@PathVariable("pwd")String pwd){

        final String s = pwdMap.get(pwd);
        if(StringUtils.isEmpty(s)){
            System.out.println("没这个用户");
            return "false";
        }
        final boolean matches = passwordEncoder.matches(pwd, s);
        return String.valueOf(matches);
    }
}
