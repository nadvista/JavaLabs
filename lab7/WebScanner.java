import java.io.IOException;

public class WebScanner {
    public static final int MAX_DEPTH = 10;
    public static final int CRAWLERS_COUNT = 1000;

    private static int freezedCrawlers = 0;
    private static Crawler[] allCrawlers = new Crawler[CRAWLERS_COUNT];
    private static Object locker = new Object();

    private static int errors = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

        var startUrl = "http://blog.adw.org/";
        var startDepth = 0;
        UrlsContainer.AddUnchecked(startUrl, startDepth);

        var threads = new Thread[CRAWLERS_COUNT];
        for (int i = 0; i < CRAWLERS_COUNT; i++) {
            var crawler = new Crawler();
            var thread = new Thread(crawler);
            allCrawlers[i] = crawler;
            threads[i] = thread;
            thread.start();
        }

        waitAll();

        // waitAllThreads(threads);
        var checked = UrlsContainer.GetChecked();
        for (var element : checked) {
            System.out.println(element.getUrl());
        }
        System.out.println(checked.size());
        System.out.println(errors);
    }

    private static void waitAll() throws InterruptedException {
        while (freezedCrawlers != CRAWLERS_COUNT) {
            Thread.sleep(1);
        }
        stopCrawlers();
    }

    private static void stopCrawlers() {
        for (var crawler : allCrawlers) {
            crawler.Stop();
        }
    }

    public static void CrawlerFreezedHandler(Crawler sender) {
        synchronized (locker) {
            freezedCrawlers++;
        }
    }

    public static void CrawlerUnfreezedHandler(Crawler sender) {
        synchronized (locker) {
            freezedCrawlers--;
        }
    }

    public static void CrawlerError() {
        synchronized (locker) {
            errors++;
        }
    }
}