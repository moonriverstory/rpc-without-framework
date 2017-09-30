package proxy;

import discovery.ServiceDiscovery;

import java.lang.reflect.Proxy;


public class RpcProxy {

    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass) {
        return create(interfaceClass, "");
    }

    /**
     * 利用反射代理，创建代理对象
     *
     * @param interfaceClass
     * @param serviceVersion
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass, final String serviceVersion) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new Invoker(serviceDiscovery, serviceVersion));
    }
}

