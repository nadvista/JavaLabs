import java.util.ArrayList;
import java.util.List;

public class UrlsContainer {
    private static Object locker = new Object();

    private static ArrayList<URLDepthPair> uncheckedUrls = new ArrayList<URLDepthPair>();
    private static ArrayList<URLDepthPair> checkedUrls = new ArrayList<URLDepthPair>();

    public static int GetUncheckedSize() {
        return uncheckedUrls.size();
    };

    public static ArrayList<URLDepthPair> GetChecked() {
        return checkedUrls;
    }

    public static URLDepthPair getFreeElement() {
        synchronized (locker) {
            if (GetUncheckedSize() == 0)
                return null;
            var element = uncheckedUrls.get(0);
            checkedUrls.add(element);
            uncheckedUrls.remove(element);
            return element;
        }
    }

    public static void AddUnchecked(String url, int depth) {
        synchronized (locker) {
            if (depth >= WebScanner.MAX_DEPTH)
                return;
            for (var checked : checkedUrls) {
                if (checked.getUrl().equals(url))
                    return;
            }
            uncheckedUrls.add(new URLDepthPair(url, depth));
        }
    }

    public static void AddUnchecked(List<String> urls, int depth) {
        synchronized (locker) {
            for (var url : urls) {
                var isCurrentChecked = false;
                if (depth >= WebScanner.MAX_DEPTH)
                    return;
                for (var checked : checkedUrls) {
                    if (checked.getUrl().equals(url)) {
                        isCurrentChecked = true;
                        break;
                    }
                }
                if (!isCurrentChecked)
                    uncheckedUrls.add(new URLDepthPair(url, depth));
            }
        }
    }

}
