package abidien.handler;

import abidien.common.Helper;
import abidien.common.Invoke;
import abidien.common.JsonExt;
import abidien.models.IItem;
import abidien.models.UserEntity;
import abidien.services.IDataService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public abstract class RestServlet<K, T extends IItem<K>> extends SmartServlet {

    final IDataService<K, T> service;

    public abstract T factory();

    private Class<K> persistentClass;

    public RestServlet(IDataService<K, T> service) {
        super(service.getModelClass());
        this.service = service;
    }

    @Invoke(params = "request,response,id")
    public void load(HttpServletRequest request, HttpServletResponse response, K id) {
        service.load(id);
    }

    @Invoke(params = "request,response")
    public void save(HttpServletRequest request, HttpServletResponse response) {
        T instance = readFromRequest(request, factory());
        instance.setOwnerId(Helper.getUser(request).getId());
        service.saveOrUpdate(instance);
    }

    @Invoke(params = "request,response,id")
    public void update(HttpServletRequest request, HttpServletResponse response, K id) {
        T old = service.load(id);
        T instance = readFromRequest(request, old);
        service.saveOrUpdate(instance);
    }

    @Invoke(params = "request,id")
    public void delete(HttpServletRequest request, K id) {
        if (Helper.getUser(request).getId() == service.load(id).getOwnerId()) {
            service.delete(service.load(id));
        }
    }

    @Invoke(params = "request,id")
    public void enable(HttpServletRequest request, K id) {
        if (Helper.getUser(request).getId() == service.load(id).getOwnerId()) {
            service.enable(id);
        }
    }

    @Invoke(params = "request,id")
    public void disable(HttpServletRequest request, K id) {
        if (Helper.getUser(request).getId() == service.load(id).getOwnerId()) {
            service.disable(id);
        }
    }

    private T readFromRequest(HttpServletRequest request, T instance) {
        for (Field f : instance.getClass().getDeclaredFields()) {
            String name = f.getName();
            try {
                String p = request.getParameter(name);
                if (p != null) {
                    f.setAccessible(true);
                    f.set(instance, convertParam(f.getType(), p));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
