package com.zyx.rpc;

import com.zyx.rpc.client.register.DefaultServiceRegistry;
import com.zyx.rpc.client.register.ServiceRegistry;
import com.zyx.rpc.server.RpcServer;

public class TestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);

        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
