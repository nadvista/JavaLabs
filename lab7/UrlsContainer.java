import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class UrlsContainer {
    private static Object locker = new Object();

    private static HashMap<Integer, URLDepthPair> _uncheckedUrls = new HashMap<Integer, URLDepthPair>();
    private static ArrayList<URLDepthPair> _checkedUrls = new ArrayList<URLDepthPair>();

    public static int getUncheckedSize() {
        return _uncheckedUrls.size();
    };

    public static ArrayList<URLDepthPair> getChecked() {
        return _checkedUrls;
    }

    public static URLDepthPair getFreeElement() {
        synchronized (locker) {
            if (getUncheckedSize() == 0)
                return null;
            var element = _uncheckedUrls.entrySet().iterator().next().getValue();
            _checkedUrls.add(element);
            _uncheckedUrls.remove(element.hashCode());
            return element;
        }
    }

    public static void addUnchecked(String url, int depth) {
        synchronized (locker) {
            var pair = new URLDepthPair(url, depth);
            _uncheckedUrls.put(pair.hashCode(), pair);
        }
    }

    public static void addUnchecked(List<String> urls, int depth) {
        for (var url : urls) {
            addUnchecked(url, depth);
        }
    }

}
