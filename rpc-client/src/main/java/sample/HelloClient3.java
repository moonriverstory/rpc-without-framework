package sample;

import api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import proxy.RpcProxy;

public class HelloClient3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient3.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);

        int loopCount = 100;

        long start = System.currentTimeMillis();

        HelloService helloService = rpcProxy.create(HelloService.class);
        for (int i = 0; i < loopCount; i++) {
            String result = helloService.hello("World");
            LOGGER.info(result);
        }

        long time = System.currentTimeMillis() - start;
        LOGGER.info("loop: " + loopCount);
        LOGGER.info("time: " + time + "ms");
        LOGGER.info("tps: " + (double) loopCount / ((double) time / 1000));

        System.exit(0);
    }
}
