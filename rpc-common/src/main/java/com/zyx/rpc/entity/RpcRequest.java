package com.zyx.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * 发送请求对象
 */
@Data
@AllArgsConstructor
public class RpcRequest implements Serializable {

    public RpcRequest() {}

    //接口名称
    private String interfaceName;

    //调用方法
    private String methodName;

    /**
     * 调用方法参数
     */
    private Object[] parameters;

    /**
     * 调用方法参数类型
     */
    private Class<?>[] paramTypes;

    /**
     * 请求号
     */
    private String requestId;

}
