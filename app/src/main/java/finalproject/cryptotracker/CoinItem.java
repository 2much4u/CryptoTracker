package finalproject.cryptotracker;

public class CoinItem {
    private String ticker;
    private String currentPrice;
    private String percentChange;

    public CoinItem(String ticker, String currentPrice, String percentChange) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.percentChange = percentChange;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(String percentChange) {
        this.percentChange = percentChange;
    }
}
