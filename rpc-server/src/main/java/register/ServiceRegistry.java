package register;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Constant;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * register rpc service to zk
 */
public class ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String registryAddress;

    private final ZkClient zkClient;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
        // 创建 ZooKeeper 客户端
        zkClient = new ZkClient(registryAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
    }

    public void register(String serviceName, String serviceAddress) {
        // 创建 registry 节点（持久）
        String registryPath = Constant.ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            LOGGER.info("create registry node: {}", registryPath);
        }
        // 创建 service 节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.info("create service node: {}", servicePath);
        }
        // 创建 address 节点（临时）
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
        LOGGER.info("create address node: {} , data: {}", addressNode, serviceAddress);
    }

}

