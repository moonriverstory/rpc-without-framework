package sample;

import api.HelloService;
import api.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import proxy.RpcProxy;

public class HelloClient2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient2.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);

        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello(new Person("Kevin", "Mitchell"));
        LOGGER.info("helloService.hello(Person), result= " + result);

        System.exit(0);
    }
}
