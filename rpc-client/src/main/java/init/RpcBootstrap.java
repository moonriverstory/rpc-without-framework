package init;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * load spring config
 */
public class RpcBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }

}

