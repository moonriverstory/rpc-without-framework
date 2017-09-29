package api;

import api.pojo.Person;

/**
 * rpc api demo
 */
public interface HelloService {

    String hello(String name);

    String hello(Person person);
}

