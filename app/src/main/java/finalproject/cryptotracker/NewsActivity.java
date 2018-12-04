package finalproject.cryptotracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class NewsActivity extends AppCompatActivity {
    private float touchX1,touchX2;
    private final int minSwipeDistance = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                touchX2 = event.getX();
                float deltaX = touchX2 - touchX1;
                if (Math.abs(deltaX) > minSwipeDistance) {
                    Bundle bundle = new Bundle();
                    startActivity(new Intent(NewsActivity.this, MainActivity.class), );
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
