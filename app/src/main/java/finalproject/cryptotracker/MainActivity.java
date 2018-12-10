package finalproject.cryptotracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private float touchX1,touchX2;
    private final int minSwipeDistance = 300;
    private List<CoinItem> coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setBackgroundText("Loading...");

        coins = new ArrayList<>();
        aggregateCoins();
    }

    private void setBackgroundText(String text) {
        TextView backgroundText = findViewById(R.id.backgroundText);
        backgroundText.setText(text);
    }

    private void drawCoinsUI() {
        RecyclerView recyclerView = findViewById(R.id.coinRecycler);
        CoinAdapter adapter = new CoinAdapter(this, coins);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch(e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchX1 = e.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        touchX2 = e.getX();
                        float deltaX = touchX2 - touchX1;
                        if (Math.abs(deltaX) > minSwipeDistance) {
                            startActivity(new Intent(MainActivity.this, NewsActivity.class));
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });
    }

    private void aggregateCoins() {
        aggregateCoin("BTC");
        aggregateCoin("XRP");
        aggregateCoin("ETH");
        aggregateCoin("XLM");
        aggregateCoin("MITH");
        aggregateCoin("USDT");
        aggregateCoin("BCH");
        aggregateCoin("EOS");
        aggregateCoin("LTC");
        aggregateCoin("XMR");
        aggregateCoin("DEX");
    }

    private void aggregateCoin(final String ticker) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=" + ticker + "&tsyms=USD&api_key=6db320aa389652ff3c1fe760b03851b1861a7fe89d028f1a29a262fcb8fe2675";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseCoinData(ticker, response);
                drawCoinsUI();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setBackgroundText("Failed to get coin data!");
            }
        });

        queue.add(stringRequest);
    }

    private void parseCoinData(String ticker, String response) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject coinData = (JSONObject) parser.parse(response);
            JSONObject display = (JSONObject) coinData.get("DISPLAY");
            JSONObject tickerData = (JSONObject) display.get(ticker);
            JSONObject usd = (JSONObject) tickerData.get("USD");
            String price = ((String) usd.get("PRICE")).replaceAll(" ", "");
            String percentChange = ((String) usd.get("CHANGEPCT24HOUR")) + "%";
            coins.add(new CoinItem(ticker, price, percentChange));
        } catch (Exception e) {
            setBackgroundText("Failed to parse coin data!");
        }
    }
}
