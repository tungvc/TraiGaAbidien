package bo;

import model.PhoneInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by ABIDIEN on 3/24/2019.
 */
public class PhoneReader implements Iterable<PhoneInfo>, Iterator<PhoneInfo>, AutoCloseable {

    private BufferedReader bufferedReader;
    private boolean hasNext;

    public PhoneReader(String _path) {
        try {
            bufferedReader = new BufferedReader(new FileReader(_path));
            hasNext = true;
        } catch (FileNotFoundException e) {
            hasNext = false;
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public PhoneInfo next() {
        PhoneInfo result = null;
        String line;
        boolean found = false;
        try {
            while (!found && (line = bufferedReader.readLine()) != null) {
                result = PhoneInfo.parsePhoneInfo(line);
                if (result != null) {
                    found = true;
                }
            }

            if (!found) {
                hasNext = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterator<PhoneInfo> iterator() {
        return this;
    }

    @Override
    public void close() throws Exception {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
}
