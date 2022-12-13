import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebScanner {
    public static final int MAX_DEPTH = 2;
    public static final int CRAWLERS_COUNT = 10;

    public static void main(String[] args) {
        var startUrl = "http://blog.adw.org/";
        var startDepth = 0;
        UrlsContainer.addUnchecked(startUrl, startDepth);
        Run();
        for (var pair : UrlsContainer.getChecked()) {
            System.out.println(String.format("URL %s in depth %s", pair.getUrl(), pair.getDepth()));

        }
    }

    public static void Run() {
        var executorService = Executors.newFixedThreadPool(CRAWLERS_COUNT);
        var crawlers = new Crawler[CRAWLERS_COUNT];
        for (var i = 0; i < CRAWLERS_COUNT; i++)
            crawlers[i] = new Crawler(MAX_DEPTH);

        while (UrlsContainer.getUncheckedSize() > 0) {
            for (var crawler : crawlers) {
                executorService.execute(crawler);
                System.out.println("Started new crawler");
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}