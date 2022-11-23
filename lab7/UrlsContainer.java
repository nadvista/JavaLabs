import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class UrlsContainer {
    private static Object locker = new Object();

    private static HashMap<Integer, URLDepthPair> uncheckedUrls = new HashMap<Integer, URLDepthPair>();
    private static HashMap<Integer, URLDepthPair> checkedUrls = new HashMap<Integer, URLDepthPair>();

    public static int GetUncheckedSize() {
        return uncheckedUrls.size();
    };

    public static Collection<URLDepthPair> GetChecked() {
        return checkedUrls.values();
    }

    public static URLDepthPair getFreeElement() {
        synchronized (locker) {
            if (GetUncheckedSize() == 0)
                return null;
            var element = uncheckedUrls.entrySet().iterator().next().getValue();
            checkedUrls.put(element.hashCode(), element);
            uncheckedUrls.remove(element.hashCode());
            return element;
        }
    }

    public static void AddUnchecked(String url, int depth) {
        synchronized (locker) {
            if (depth >= WebScanner.MAX_DEPTH)
                return;
            var pair = new URLDepthPair(url, depth);
            uncheckedUrls.put(pair.hashCode(), pair);
        }
    }

    public static void AddUnchecked(List<String> urls, int depth) {
        synchronized (locker) {
            for (var url : urls) {
                if (depth >= WebScanner.MAX_DEPTH)
                    return;
                var pair = new URLDepthPair(url, depth);
                uncheckedUrls.put(pair.hashCode(), pair);
            }
        }
    }

}
