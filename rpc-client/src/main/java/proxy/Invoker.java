package proxy;

import client.RpcClient;
import discovery.ServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.RpcRequest;
import pojo.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

public class Invoker implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Invoker.class);

    private ServiceDiscovery serviceDiscovery;

    private String serviceVersion;

    public Invoker(ServiceDiscovery serviceDiscovery, String serviceVersion) {
        this.serviceDiscovery = serviceDiscovery;
        this.serviceVersion = serviceVersion;
    }

    /**
     * 代理handler callbacks，利用rpc服务，获取整个rpcImpl对象的返回值
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 创建并初始化 RPC 请求
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setServiceVersion(this.serviceVersion);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);

        // 获取 RPC 服务地址
        String serverAddress = null;
        if (serviceDiscovery != null) {
            serverAddress = serviceDiscovery.discover(method.getDeclaringClass().getName()); // 发现服务
            LOGGER.debug("discover service: {} => {}", method.getDeclaringClass().getName(), serverAddress);
        }

        if (serverAddress == null || "".equals(serverAddress.trim()) || "null".equals(serverAddress.trim())) {
            throw new RuntimeException("no server address found!");
        }
        // 从 RPC 服务地址中解析主机名与端口号
        String[] array = serverAddress.split(":");
        String host = array[0];
        int port = Integer.parseInt(array[1]);

        // 初始化 RPC 客户端
        RpcClient client = new RpcClient(host, port);
        // 通过 RPC 客户端发送 RPC 请求并获取 RPC 响应
        long time = System.currentTimeMillis();
        RpcResponse response = client.send(request);
        LOGGER.info("rpc call spend time: {}ms", System.currentTimeMillis() - time);

        if (response == null) {
            throw new RuntimeException("response is null");
        }
        LOGGER.info("rpc call, request: " + request.toString() + " ,response: " + response.toString());
        // 返回 RPC 响应结果
        if (response.getError() != null) {
            throw response.getError();
        } else {
            return response.getResult();
        }
    }
}
