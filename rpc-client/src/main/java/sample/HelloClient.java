package sample;

import api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import proxy.RpcProxy;

public class HelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);

        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("World");
        LOGGER.info("helloService.hello result= " + result);

        HelloService helloService2 = rpcProxy.create(HelloService.class, "ice.hello2");
        String result2 = helloService2.hello("世界");
        LOGGER.info("helloService2.hello result2= " + result2);

        System.exit(0);
    }
}
