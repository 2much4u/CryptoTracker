package finalproject.cryptotracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private float touchX1,touchX2;
    private final int minSwipeDistance = 300;
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
        newsList.add(new NewsItem("test", "test", "test"));
        drawNewsUI();
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
                        if (Math.abs(deltaX) > minSwipeDistance) {
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
}
