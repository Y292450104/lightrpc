package rpc.xp.client;

import rpc.xp.common.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;

public class ProxyInterface {

    public static <T> T getProxy(final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setMethodName(method.getName());
                request.setClassName(clazz.getName());
                request.setArgs(args);
                request.setParameterTypes(method.getParameterTypes());
                String id = UUID.randomUUID().toString();
                request.setId(id);
                SynchronousQueue queue = new SynchronousQueue();
                ResultInfo.putSunchronousQuee(id, queue);
                Client.write(request);
                return queue.take();
            }
        });
    }
}
