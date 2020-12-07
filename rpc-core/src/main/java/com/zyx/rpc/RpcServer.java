package com.zyx.rpc;

import com.zyx.rpc.serializer.CommonSerializer;

public interface RpcServer {
    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
