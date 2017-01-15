package abidien.autopost.services;

import abidien.chuongga.Environment;
import abidien.common.Spawn;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ABIDIEN on 14/01/2017.
 */
public class LogService implements ILog {
    ConcurrentLinkedQueue<Pair<Integer, Integer>> _queue;

    public LogService() {
        _queue = new ConcurrentLinkedQueue<>();
        Spawn.run(()->{
            ArrayList<Pair<Integer, Integer>> list = new ArrayList();
            int limit = 1000;
            while (true) {
                try {
                    int queueSize = _queue.size();
                    if (queueSize > limit + 1000) limit += 1000;
                    else if (limit > 1000 && queueSize < limit - 1000) limit -= 1000;

                    while (_queue.peek() != null && list.size() < limit) {
                        list.add(_queue.poll());
                    }

                    if (list.size() > 0) {
                        flush(list);
                        list.clear();
                    }

                    if (queueSize > limit)
                        Thread.sleep(1 * 1000);
                    else
                        Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void flush(ArrayList<Pair<Integer, Integer>> list) {
        HashMap<Pair<Integer, Integer>, Integer> map = new HashMap<>();

        for (Pair<Integer, Integer> record: list) {
            map.put(record, map.getOrDefault(record, 0) + 1);
        }
        for (Map.Entry<Pair<Integer, Integer>, Integer> entry: map.entrySet()) {
            Environment.getReportService().updateReport(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void log(int fakeLinkId, int domainId) {
        if (domainId < 0) return;
        _queue.offer(new Pair<>(fakeLinkId, domainId));
    }

}
