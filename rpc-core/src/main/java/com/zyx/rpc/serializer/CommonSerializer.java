package com.zyx.rpc.serializer;


public interface CommonSerializer {

    /**
     * 自定义序列化
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /** 自定义反序列化：
     *      只有提供了准确的类型信息才能准确地进行反序列化
     * @param bytes
     * @param clazz
     * @return
     */
    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();

            default:
                return null;
        }
    }


}
