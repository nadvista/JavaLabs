import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class UrlsContainer {
    private static Object locker = new Object();

    private static HashMap<Integer, URLDepthPair> uncheckedUrls = new HashMap<Integer, URLDepthPair>();
    private static HashMap<Integer, URLDepthPair> checkedUrls = new HashMap<Integer, URLDepthPair>();

    public static int getUncheckedSize() {
        return uncheckedUrls.size();
    };

    public static Collection<URLDepthPair> getChecked() {
        return checkedUrls.values();
    }

    public static URLDepthPair getFreeElement() {
        synchronized (locker) {
            if (getUncheckedSize() == 0)
                return null;
            var element = uncheckedUrls.entrySet().iterator().next().getValue();
            checkedUrls.put(element.hashCode(), element);
            uncheckedUrls.remove(element.hashCode());
            return element;
        }
    }

    public static void addUnchecked(String url, int depth) {
        synchronized (locker) {
            var pair = new URLDepthPair(url, depth);
            uncheckedUrls.put(pair.hashCode(), pair);
        }
    }

    public static void addUnchecked(List<String> urls, int depth) {
        for (var url : urls) {
            addUnchecked(url, depth);
        }
    }

}
