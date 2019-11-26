package com.luojilab.component.componentlib.router;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Center router, works for component-dynamic-load/remove and services add/remove/get
 * Created by mrzhang on 2017/6/15.
 */
public class Router {
    /**
     * 注册的服务
     */
    private HashMap<Class, Object> services = new HashMap<>();
    /**
     * 没有注册的服务的空实现，用于独立组件编译调试
     */
    private HashMap<Class, Object> serviceProxies = new HashMap<>();
    /**
     * 注册的组件的集合
     */
    private static HashMap<String, IApplicationLike> components = new HashMap<>();

    private static volatile Router instance;

    private Router() {
    }

    public static Router getInstance() {
        if (instance == null) {
            synchronized (Router.class) {
                if (instance == null) {
                    instance = new Router();
                }
            }
        }
        return instance;
    }


    public synchronized <Service, Impl extends Service> void addService(@NonNull Class<Service> serviceName,
                                                                        @NonNull Impl serviceImpl) {
        if (serviceName == null || serviceImpl == null) {
            return;
        }
        services.put(serviceName, serviceImpl);
    }
    @SuppressWarnings("unchecked")
    public synchronized <S> S getService(@NonNull Class<S> service) {
        if (service == null) {
            return null;
        }
        S instance = (S) services.get(service);
        if (instance == null) {
            instance = (S) serviceProxies.get(service);
        }
        if (instance == null && service.isInterface()) {
            instance =
                    (S) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, (proxy, method, args) -> {
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        Log.e("Router", service.getName() + " 的实现不存在，" + method.getName()
                                + " 方法执行的是代理对象的空实现，如若需要，请在 gradle.properties 中配置对应模块的依赖");
                        Type type = method.getReturnType();
                        if (type == boolean.class) {
                            return false;
                        } else if (type == int.class) {
                            return 0;
                        } else if (type == short.class) {
                            return (short) 0;
                        } else if (type == char.class) {
                            return (char) 0;
                        } else if (type == byte.class) {
                            return (byte) 0;
                        } else if (type == long.class) {
                            return 0L;
                        } else if (type == float.class) {
                            return 0f;
                        } else if (type == double.class) {
                            return 0D;
                        } else {
                            return null;
                        }
                    });
            serviceProxies.put(service, instance);
        }
        return instance;
    }

    public synchronized void removeService(Class serviceName) {
        if (serviceName == null) {
            return;
        }
        services.remove(serviceName);
    }

    @SuppressWarnings("unchecked")
    synchronized public static <T> T getTypeService(Class<T> service) {
        Router router = Router.getInstance();
        return (T) router.getService(service);
    }

    /**
     * 注册组件
     *
     * @param classname 组件名
     */
    public static void registerComponent(@Nullable String classname, Context context) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (components.keySet().contains(classname)) {
            return;
        }
        try {
            Class clazz = Class.forName(classname);
            IApplicationLike applicationLike = (IApplicationLike) clazz.newInstance();
            applicationLike.onCreate();
            components.put(classname, applicationLike);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反注册组件
     *
     * @param classname 组件名
     */
    public static void unregisterComponent(@Nullable String classname) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (components.keySet().contains(classname)) {
            components.get(classname).onStop();
            components.remove(classname);
            return;
        }
        try {
            Class clazz = Class.forName(classname);
            IApplicationLike applicationLike = (IApplicationLike) clazz.newInstance();
            applicationLike.onStop();
            components.remove(classname);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
