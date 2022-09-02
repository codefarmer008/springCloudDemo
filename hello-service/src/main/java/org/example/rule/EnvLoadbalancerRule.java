package org.example.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class EnvLoadbalancerRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object o) {
        String deployEnv = "deploy.env";
        // 获取当前服务的集群名称
        String currentClusterName = nacosDiscoveryProperties.getClusterName();

        // 获取当前服务所处环境
        String env = nacosDiscoveryProperties.getMetadata().get(deployEnv);

        // 获取被调用的服务的名称
        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) getLoadBalancer();
        String serviceName = baseLoadBalancer.getName();

        // 获取nacos clinet的服务注册发现组件的api
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

        try {
            // 获取所有被调用服务
            List<Instance> allInstances = namingService.getAllInstances(serviceName);

            // 过滤出相同环境且相同集群下的所有服务
            List<Instance> sameVersionAndClusterInstances = allInstances.stream()
                    .filter(x -> StringUtils.equalsIgnoreCase(x.getMetadata().get(deployEnv), env)
                            && StringUtils.equalsIgnoreCase(x.getClusterName(), currentClusterName)
                    ).collect(Collectors.toList());

            Instance chooseInstance;
            if(sameVersionAndClusterInstances.isEmpty()) {
                // 过滤出所有相同环境的服务
                List<Instance> sameVersionInstances = allInstances.stream()
                        .filter(x -> StringUtils.equalsIgnoreCase(x.getMetadata().get(deployEnv), env))
                        .collect(Collectors.toList());
                if(sameVersionInstances.isEmpty()) {
                    System.out.println("跨集群调用找不到对应合适的服务:env:"+env);
                    throw new RuntimeException("找不到相同环境的微服务实例");
                }
                else {
                    // 随机权重
                    chooseInstance = ExtendBalancer.getHostByRandomWeight2(sameVersionInstances);
//                    log.info("跨集群同版本调用--->当前微服务所在集群:{},被调用微服务所在集群:{},当前微服务的版本:{},被调用微服务版本:{},Host:{},Port:{}",
//                            currentClusterName, chooseInstance.getClusterName(), chooseInstance.getMetadata().get("current-version"),
//                            chooseInstance.getMetadata().get("current-version"), chooseInstance.getIp(), chooseInstance.getPort());
                }
            }
            else {
                chooseInstance = ExtendBalancer.getHostByRandomWeight2(sameVersionAndClusterInstances);
//                log.info("同集群同版本调用--->当前微服务所在集群:{},被调用微服务所在集群:{},当前微服务的版本:{},被调用微服务版本:{},Host:{},Port:{}",
//                        currentClusterName, chooseInstance.getClusterName(), chooseInstance.getMetadata().get("version"),
//                        chooseInstance.getMetadata().get("current-version"), chooseInstance.getIp(), chooseInstance.getPort());
            }
            return new NacosServer(chooseInstance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}