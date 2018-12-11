package finalproject.cryptotracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.newsViewHolder> {
    private Context context;
    private List<NewsItem> newsList;

    public NewsAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public newsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new newsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(newsViewHolder holder, int position) {
        NewsItem news = newsList.get(position);
        holder.newsTitle.setText(news.getTitle());
        holder.newsSource.setText("Source: " + news.getSource());
        holder.url = news.getUrl();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class newsViewHolder extends RecyclerView.ViewHolder {
        private TextView newsTitle;
        private TextView newsSource;
        private String url;

        public newsViewHolder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsSource = itemView.findViewById(R.id.newsSource);
        }

        public String getUrl() {
            return url;
        }
    }
}
