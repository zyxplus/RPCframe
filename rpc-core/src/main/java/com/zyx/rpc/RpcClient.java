package com.zyx.rpc;

import com.zyx.rpc.entity.RpcRequest;
import com.zyx.rpc.serializer.CommonSerializer;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
