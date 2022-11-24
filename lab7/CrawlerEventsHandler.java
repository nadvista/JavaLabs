public class CrawlerEventsHandler implements ICrawlerEventsHandler {

    @Override
    public void CrawlerFreezed(Crawler crawler) {
        WebScanner.CrawlerFreezedHandler(crawler);
    }

    @Override
    public void CrawlerResumed(Crawler crawler) {
        WebScanner.CrawlerResumedHandler(crawler);
    }

}