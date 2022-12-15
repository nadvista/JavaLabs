import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebScanner {
    public static final int MAX_DEPTH = 5;
    public static final int CRAWLERS_COUNT = 10;
    private static ArrayList<String> _errors = new ArrayList<String>();
    public static void main(String[] args) {
        // var startUrl = "http://blog.adw.org/";
        var startUrl = "http://www.consultant.ru/";
        var startDepth = 0;
        UrlsContainer.addUnchecked(startUrl, startDepth);
        Run();
        var foundpairs = UrlsContainer.getChecked();
        for (var pair : foundpairs) {
            System.out.println(String.format("URL %s in depth %s", pair.getUrl(), pair.getDepth()));
        }
        for(var error : _errors)
        {
            System.out.println(error);
        }
        System.out.println(foundpairs.size());
    }

    public static void Run() {
        var crawlers = new Crawler[CRAWLERS_COUNT];
        var executorService = Executors.newFixedThreadPool(CRAWLERS_COUNT);
        var futures = new Future<?>[CRAWLERS_COUNT];
        
        for (var i = 0; i < CRAWLERS_COUNT; i++)
            crawlers[i] = new Crawler(MAX_DEPTH);

        while (UrlsContainer.getUncheckedSize() > 0) {
            for (var i = 0; i < CRAWLERS_COUNT; i++) {
                var crawler = crawlers[i];
                var future = executorService.submit(crawler);
                futures[i] = future;
            }
            try {
                for (var future : futures) {
                    future.get();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (var crawler : crawlers) {
            _errors.addAll(crawler.getErrors());
        }
        executorService.shutdown();
    }
}