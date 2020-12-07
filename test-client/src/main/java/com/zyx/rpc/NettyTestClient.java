package com.zyx.rpc;

import com.zyx.rpc.api.HelloObject;
import com.zyx.rpc.api.HelloService;
import com.zyx.rpc.netty.client.NettyClient;
import com.zyx.rpc.serializer.HessianSerializer;
import com.zyx.rpc.serializer.KryoSerializer;
import com.zyx.rpc.socket.SocketClient;
import org.checkerframework.checker.units.qual.K;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9991);
        client.setSerializer(new HessianSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

    }
}
