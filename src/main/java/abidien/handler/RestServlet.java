package abidien.handler;

import abidien.common.Invoke;
import abidien.common.JsonExt;
import abidien.services.IDataService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public abstract class RestServlet<T> extends SmartServlet {

    final IDataService<T> service;

    public abstract T factory();

    public RestServlet(IDataService<T> service) {
        this.service = service;
    }

    @Invoke(params = "request,response,id")
    public void load(HttpServletRequest request, HttpServletResponse response, int id) {
        service.load(id);
    }

    @Invoke(params = "request,response")
    public void save(HttpServletRequest request, HttpServletResponse response) {
        T instance = readFromRequest(request, factory());
        service.saveOrUpdate(instance);
    }

    @Invoke(params = "request,response,id")
    public void update(HttpServletRequest request, HttpServletResponse response, int id) {
        T old = service.load(id);
        T instance = readFromRequest(request, old);
        service.saveOrUpdate(instance);
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