package com.zyx.rpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.zyx.rpc.enumeration.SerializeException;
import com.zyx.rpc.enumeration.SerializerCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements CommonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);

    @Override
    public byte[] serialize(Object obj) {
        HessianOutput hessianOutput = null;
        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream()) {
            hessianOutput = new HessianOutput(byteOutputStream);
            hessianOutput.writeObject(obj);
            return byteOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("序列化过程报错：{}", e.getMessage());
            throw new SerializeException("序列化过程报错");
        } finally {
            if (hessianOutput != null) {
                try {
                    hessianOutput.close();
                } catch (IOException e) {
                    logger.error("关闭流过程报错：{}", e.getMessage());
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        HessianInput hessianInput = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            hessianInput = new HessianInput(byteArrayInputStream);
            return hessianInput.readObject();
        } catch (IOException e) {
            logger.error("序列化过程报错：{}", e.getMessage());
            throw new SerializeException("序列化过程报错");
        } finally {
            if (hessianInput != null) {
                hessianInput.close();
            }
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("HESSIAN").getCode();
    }
}
