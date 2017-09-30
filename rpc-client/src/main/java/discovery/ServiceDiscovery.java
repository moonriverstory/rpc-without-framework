package discovery;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Constant;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 从zk上发现rpc服务
 */
public class ServiceDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscovery.class);

    private String registryAddress;

    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    /**
     * 发现rpc服务的ip:port地址
     *
     * @param name
     * @return
     */
    public String discover(String name) {
        // 创建 ZooKeeper 客户端
        ZkClient zkClient = new ZkClient(registryAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.info("connect zookeeper：" + registryAddress);
        try {
            String servicePath = Constant.ZK_REGISTRY_PATH + "/" + name;
            // 获取 service 节点
            List<String> nodeList = zkClient.getChildren(servicePath);
            if (nodeList == null || nodeList.isEmpty()) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }
            LOGGER.info("node list：" + nodeList);
            // 获取 address 节点
            String address;
            int size = nodeList.size();
            if (size == 1) {
                // 若只有一个地址，则获取该地址
                address = nodeList.get(0);
                LOGGER.info("get only address node: {}", address);
            } else {
                // 若存在多个地址，则随机获取一个地址
                address = nodeList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.info("get random address node: {}", address);
            }
            // 获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            // 读取一个数据，即注册在zk上面的rpc服务地址
            String serviceAddress = zkClient.readData(addressPath);
            LOGGER.info("get service address: " + serviceAddress);
            return serviceAddress;
        } catch (Exception e) {
            LOGGER.error("获取节点数据ERROR: ", e);
            return null;
        } finally {
            zkClient.close();
        }

    }

}
