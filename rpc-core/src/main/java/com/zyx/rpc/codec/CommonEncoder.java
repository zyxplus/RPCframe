package com.zyx.rpc.codec;

import com.zyx.rpc.entity.RpcRequest;
import com.zyx.rpc.entity.RpcResponse;
import com.zyx.rpc.enumeration.PackageType;
import com.zyx.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 通用的编码拦截器
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;
    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        //写入序列化结果类型（JSON）
        out.writeInt(serializer.getCode());
        //序列化消息
        byte[] bytes = serializer.serialize(msg);
        //写入序列化长度
        out.writeInt(bytes.length);
        //写入序列化的数据
        out.writeBytes(bytes);
    }
}
