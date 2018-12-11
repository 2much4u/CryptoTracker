package finalproject.cryptotracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private float touchX1,touchX2;
    private final int minSwipeDistance = 300;
    private final int maxPressDistance = 10;
    private List<NewsItem> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setBackgroundText("Loading news...");

        newsList = new ArrayList<>();
        aggregateNews();
    }

    private void setBackgroundText(String text) {
        TextView backgroundText = findViewById(R.id.newsBackgroundText);
        backgroundText.setText(text);
    }

    private void drawNewsUI() {
        RecyclerView recyclerView = findViewById(R.id.newsRecycler);
        NewsAdapter adapter = new NewsAdapter(this, newsList);
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
                        if (Math.abs(deltaX) <= maxPressDistance) {
                            View view = rv.findChildViewUnder(e.getX(), e.getY());
                            NewsAdapter.newsViewHolder viewHolder = (NewsAdapter.newsViewHolder) rv.getChildViewHolder(view);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(viewHolder.getUrl())));
                        } else if (Math.abs(deltaX) > minSwipeDistance) {
                            startActivity(new Intent(NewsActivity.this, MainActivity.class));
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

    private void aggregateNews() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN&api_key=6db320aa389652ff3c1fe760b03851b1861a7fe89d028f1a29a262fcb8fe2675";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseNewsData(response);
                        drawNewsUI();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setBackgroundText("Failed to get news data!");
            }
        });

        queue.add(stringRequest);
    }

    private void parseNewsData(String response) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject newsData = (JSONObject) parser.parse(response);
            JSONArray newsArray = (JSONArray) newsData.get("Data");
            for (int i = 0; i < newsArray.size(); i++) {
                JSONObject article = (JSONObject) newsArray.get(i);
                newsList.add(new NewsItem((String) article.get("title"), (String) article.get("url"), (String) article.get("source")));
            }
        } catch (Exception e) {
            setBackgroundText("Failed to parse news data!");
        }
    }
}
