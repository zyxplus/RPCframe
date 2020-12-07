package com.zyx.rpc;

import com.zyx.rpc.client.register.DefaultServiceRegistry;
import com.zyx.rpc.client.register.ServiceRegistry;
import com.zyx.rpc.serializer.KryoSerializer;
import com.zyx.rpc.socket.SocketServer;

public class TestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);

        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.start(9000);
    }
}
