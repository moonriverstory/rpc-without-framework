package proxy;

import client.RpcClient;
import discovery.ServiceDiscovery;
import pojo.RpcRequest;
import pojo.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

public class Invoker implements InvocationHandler {

    private ServiceDiscovery serviceDiscovery;

    public Invoker(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest(); // 创建并初始化 RPC 请求
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);

        String serverAddress = null;

        if (serviceDiscovery != null) {
            serverAddress = serviceDiscovery.discover(); // 发现服务
        }

        if(serverAddress==null){
            return "no server address found!";
        }

        String[] array = serverAddress.split(":");
        String host = array[0];
        int port = Integer.parseInt(array[1]);

        RpcClient client = new RpcClient(host, port); // 初始化 RPC 客户端
        RpcResponse response = client.send(request); // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应

        if (response.getError() != null) {//TODO isError() ?
            throw response.getError();
        } else {
            return response.getResult();
        }
    }
}
