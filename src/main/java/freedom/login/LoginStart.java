package freedom.login;

import cn.dev33.satoken.SaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoginStart {
    private final static Logger logger = LoggerFactory.getLogger(LoginStart.class);

    public static void main(String[] args) {
        SpringApplication.run(LoginStart.class, args);
        logger.info("-----------------LoginStart success-----------------");
        logger.info("sa-taken config: {}", SaManager.getConfig());
    }
}