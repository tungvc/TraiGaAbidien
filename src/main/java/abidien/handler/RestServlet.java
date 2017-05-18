package abidien.handler;

import abidien.autopost.models.DomainEntity;
import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.common.Invoke;
import abidien.common.JsonExt;
import abidien.common.WebUtils;
import abidien.models.IItem;
import abidien.models.UserEntity;
import abidien.services.IDataService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public abstract class RestServlet<K, T extends IItem<K>> extends SmartServlet {

    final IDataService<K, T> service;

    public abstract T factory();

    private Class<K> persistentClass;

    public RestServlet(IDataService<K, T> service) {
        super((service == null) ? null : service.getModelClass());
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
        if (Helper.getUser(request).getId().equals(service.load(id).getOwnerId())) {
            service.delete(service.load(id));
        }
    }

    @Invoke(params = "request,id")
    public void enable(HttpServletRequest request, K id) {
        if (Helper.getUser(request).getId().equals(service.load(id).getOwnerId())) {
            service.enable(id);
        }
    }

    @Invoke(params = "request,id")
    public void disable(HttpServletRequest request, K id) {
        if (Helper.getUser(request).getId().equals(service.load(id).getOwnerId())) {
            service.disable(id);
        } else System.out.println(Helper.getUser(request).getId() + " != " + service.load(id).getOwnerId());
    }

    @Invoke(params = "request,response")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        int userId = Helper.getUser(request).getId();
        List<T> rs = service.loadAll().stream()
                .filter(p -> p.getOwnerId() == userId)
                .collect(Collectors.toList());
        if (rs != null && rs.size() > 0 && rs.get(0).getId() instanceof Integer) {
            rs.sort((x1, x2) -> ((Integer) x2.getId() == null ? 0 : (Integer)x2.getId()) - ((Integer)x1.getId() == null ? 0 : (Integer)x1.getId()));
        }
        WebUtils.renderJson(response, JsonExt.toJson(rs));
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
