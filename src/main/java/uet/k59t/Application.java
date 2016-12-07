package uet.k59t;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import uet.k59t.model.MailMail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("Spring-Mail.xml");
//        MailMail mm = (MailMail) context.getBean("mailMail");
//        String sender="sendergmailid@gmail.com";//write here sender gmail id
//        String[] receiver = new String[3];
//        receiver[0] = "zzsushiboyzz@gmail.com";
//        receiver[1] = "luongthanlong@gmail.com";
//        receiver[2] = "thelongdt@gmail.com";
//
//        mm.sendMail(sender,receiver,"hight scholl music cal","long 1234");
//
//        System.out.println("success");
        SpringApplication.run(Application.class);



    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        return new FilterRegistrationBean(new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                String method = request.getMethod();
// this origin value could just as easily have come from a database
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods",
                        "POST,GET,OPTIONS,DELETE, PUT");
                response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader(
                        "Access-Control-Allow-Headers",
                        "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method," +
                                "Access-Control-Request-Headers,Authorization," +
                                "auth-token,ra_preferred_ip," +
                                "Pragma,Cache-Control,Expires");
                if ("OPTIONS".equals(method)) {
                    response.setStatus(HttpStatus.OK.value());
                } else {
                    chain.doFilter(req, res);
                }
            }

            public void init(FilterConfig filterConfig) {
            }

            public void destroy() {
            }
        });
    }
}

