package com.zyx.rpc.socket;

import com.zyx.rpc.RpcClient;
import com.zyx.rpc.entity.RpcRequest;
import com.zyx.rpc.entity.RpcResponse;
import com.zyx.rpc.enumeration.ResponseCode;
import com.zyx.rpc.enumeration.RpcError;
import com.zyx.rpc.enumeration.RpcException;
import com.zyx.rpc.serializer.CommonSerializer;
import com.zyx.rpc.util.ObjectReader;
import com.zyx.rpc.util.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class SocketClient implements RpcClient {

    private final String host;
    private final int port;
    private CommonSerializer serializer;

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
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)){

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;

            if (rpcResponse == null) {
                logger.error("服务调用失败，service: {}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_NOT_FOUND,
                        " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null ||
                    rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,
                        " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();

        } catch (IOException e) {
            logger.error("调用报错: {}", e);
            throw new RpcException("服务调用失败", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
