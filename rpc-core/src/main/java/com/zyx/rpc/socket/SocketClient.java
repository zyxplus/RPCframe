package com.zyx.rpc.socket;

import com.zyx.rpc.RpcClient;
import com.zyx.rpc.entity.RpcRequest;
import com.zyx.rpc.entity.RpcResponse;
import com.zyx.rpc.enumeration.ResponseCode;
import com.zyx.rpc.enumeration.RpcError;
import com.zyx.rpc.enumeration.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements RpcClient {

    private final String host;
    private final int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    /**
     * objectOutputStream 发送
     * objectInputStream 接收返回对象
     * @param rpcRequest
     * @return
     */
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            if (rpcResponse == null) {
                throw new RpcException(RpcError.SERVICE_NOT_FOUND,
                        " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null ||
                    rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,
                        " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();

        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用报错: {}", e);
            throw new RpcException("服务调用失败", e);
        }
    }
}
