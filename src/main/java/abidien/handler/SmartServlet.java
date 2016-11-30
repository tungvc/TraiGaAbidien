package abidien.handler;

import abidien.common.Invoke;
import abidien.controllers.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public class SmartServlet extends BaseServlet {

    Map<String, Method> methods = new HashMap<>();
    public SmartServlet() {
        for (Method method : this.getClass().getMethods()) {
            methods.put(method.getName(), method);
        }
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        String methodName = uri[uri.length - 1];
        Method m = methods.get(methodName);
        String[] params = m.getAnnotation(Invoke.class).params().split(",");
        Object[] args = new Object[params.length];
        Class[] paramsType = m.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            switch (params[i]) {
                case "request":
                    args[i] = request;
                    break;
                case "response":
                    args[i] = response;
                    break;
                default:
                    String v = request.getParameter(params[i]);
                    args[i] = convertParam(paramsType[i], v);
                    break;
            }
        }
        try {
            m.invoke(this, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected <T> T convertParam(Class<T> clazz, String value) {
        if (clazz.equals(Integer.TYPE) || clazz.equals(Integer.class))
            return (T)Integer.valueOf(value);
        else if (clazz.equals(String.class))
            return (T)value;
        return null;
    }
}
