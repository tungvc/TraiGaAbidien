package abidien.common;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.StringWriter;

/**
 * Created by ABIDIEN on 09/09/2016.
 */
public class TemplateEngine {
    static DefaultMustacheFactory factory = new DefaultMustacheFactory();
    static ConcurrentHashMap cache = new ConcurrentHashMap<String, Mustache>();
    static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue<StringWriter>();
    static File workingDirectory = new File(System.getProperty("user.dir"));

    static String layout(String path, Map<String, Object> map) {
        String file = path;
        if (workingDirectory != null) file = workingDirectory.getPath() + "/" + path;

        if (!cache.containsKey(file)){
            cache.put(file, factory.compile(file));
        }

        StringWriter w = (StringWriter)queue.poll();
        if (w == null) w = new StringWriter();
        else w.getBuffer().setLength(0);

        Mustache m = (Mustache) cache.get(file);
        m.execute(w, map);
        String s = w.toString();
        queue.add(w);
        return s;
    }

    public static String renderFakeLink(Map<String, Object> map) {
        return layout("templates/fake_link.mustache", map);
    }
}