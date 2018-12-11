package finalproject.cryptotracker;

public class NewsItem {
    private String title;
    private String url;
    private String source;

    public NewsItem(String title, String url, String source) {
        this.title = title;
        this.url = url;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }
}
