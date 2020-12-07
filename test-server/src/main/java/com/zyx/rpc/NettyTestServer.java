package com.zyx.rpc;

import com.zyx.rpc.client.register.DefaultServiceRegistry;
import com.zyx.rpc.client.register.ServiceRegistry;
import com.zyx.rpc.netty.server.NettyServer;

public class NettyTestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9991);
    }
}
