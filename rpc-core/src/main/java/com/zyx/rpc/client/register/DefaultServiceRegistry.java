package com.zyx.rpc.client.register;

import com.zyx.rpc.enumeration.RpcError;
import com.zyx.rpc.enumeration.RpcException;
import com.zyx.rpc.server.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registerdService = ConcurrentHashMap.newKeySet();

    /**
     * getInterfaces() 确定此对象所表示的类或接口实现的接口
     *
     * @param service 待注册的服务实体
     * @param <T>
     */
    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if (registerdService.contains(serviceName)) {
            return;
        }
        registerdService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口: {} 注册服务：{}", interfaces, serviceName);
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_CAN_FOUND);
        }
        return service;
    }
}
