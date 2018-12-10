package finalproject.cryptotracker;

public class CoinItem {
    private String ticker;
    private int pictureID;
    private String currentPrice;
    private String percentChange;

    public CoinItem(String ticker, String currentPrice, String percentChange) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.percentChange = percentChange;

        // Match ticker with drawableID
        this.pictureID = R.mipmap.ic_launcher;
    }

    public String getTicker() {
        return ticker;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(String percentChange) {
        this.percentChange = percentChange;
    }
}
