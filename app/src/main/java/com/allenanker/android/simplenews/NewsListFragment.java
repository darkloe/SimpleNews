package com.allenanker.android.simplenews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class NewsListFragment extends Fragment {
    private List<News> mNewsList;
    private int mType;
    private RecyclerView mNewsRecyclerView;
    private NewsAdapter mNewsAdapter;
    private CallBacks mCallBacks;

    public NewsListFragment newInstance(int type) {
        NewsListFragment newsListFragment = new NewsListFragment();
        newsListFragment.setType(type);
        return newsListFragment;
    }

    public void setType(int type) {
        mType = type;
    }

    public interface CallBacks {
        void onNewsSelected(News news);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBacks = (CallBacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        mNewsRecyclerView = view.findViewById(R.id.news_rv);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI(mType);
        return view;
    }

    private class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView_title;
        private TextView mTextView_des;
        private News mNews;

        public NewsHolder(View itemView) {
            super(itemView);
            mTextView_title = itemView.findViewById(R.id.news_title);
            mTextView_des = itemView.findViewById(R.id.news_des);
            itemView.setOnClickListener(this);
        }

        public void bind(News news) {
            mNews = news;
            mTextView_title.setText(news.getTitle());
            mTextView_des.setText(news.getDes());
        }

        @Override
        public void onClick(View v) {
            mCallBacks.onNewsSelected(mNews);
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

        Context mContext;
        List<News> mNewsList;

        public NewsAdapter(Context context, List<News> newsList) {
            mContext = context;
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.bind(news);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    public void updateUI(int type) {
        NewsLab newsLab = NewsLab.get(getActivity());
        mNewsList = newsLab.getNews(type);
//        if (mNewsAdapter == null) {
//            mNewsAdapter = new NewsAdapter(this.getActivity(), mNewsList);
//            mNewsRecyclerView.setAdapter(mNewsAdapter);
//        } else {
//            mNewsAdapter.setNewsList(mNewsList);
//            mNewsAdapter.notifyDataSetChanged();
//        }
        mNewsAdapter = new NewsAdapter(this.getActivity(), mNewsList);
        mNewsRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }
}