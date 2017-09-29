package rpcImpl;


import annotation.RpcService;
import api.HelloService;
import api.pojo.Person;

@RpcService(value = HelloService.class, version = "ice.hello2")
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String hello(String name) {
        return "你好! " + name;
    }

    @Override
    public String hello(Person person) {
        return "你好! " + person.getFirstName() + " " + person.getLastName();
    }
}
