public interface ICrawlerEventsHandler {
    void CrawlerFreezed(Crawler crawler);

    void CrawlerResumed(Crawler crawler);
}
