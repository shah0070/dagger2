package com.ecommerce.myapplication.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.myapplication.BaseApplication;
import com.ecommerce.myapplication.BaseFragment;
import com.ecommerce.myapplication.Constants;
import com.ecommerce.myapplication.R;
import com.ecommerce.myapplication.api.ApiService;
import com.ecommerce.myapplication.models.response.Result;
import com.ecommerce.myapplication.movie.detail.MovieDetailActivity;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author shishank
 */

public class MovieListFragment extends BaseFragment implements Contracts.View {

    RecyclerView rvMovieList;

    ProgressBar progressBar;

    AppCompatTextView tvInfo;

    Unbinder unbinder;

    @Inject
    MovieListPresenter presenter;

    private GridLayoutManager gridLayoutManager;

    private static int pageIndex = 1;
    private MovieListAdapter movieListAdapter;

    @Inject
    ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getActivity().getApplication())
                .getAppComponent()
                .newMovieComponent(new MovieModule(this))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);
        rvMovieList=(RecyclerView)view.findViewById(R.id.rv_movie_list);
        tvInfo=(AppCompatTextView)view.findViewById(R.id.tv_info);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
            RxRecyclerView.scrollEvents(rvMovieList)
                .filter(event -> presenter.shouldUpdate())
                .filter(event1 -> hasScrolledToLast())
                .map(event -> pageIndex = pageIndex + 1)
                .subscribe(recyclerViewScrollEvent -> presenter.fetchMovies(pageIndex),
                        this::onError);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Boolean hasScrolledToLast() {
        int pastVisibleItems, visibleItemCount, totalItemCount;
        visibleItemCount = gridLayoutManager.getChildCount();
        totalItemCount = gridLayoutManager.getItemCount();
        pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
        return (visibleItemCount + pastVisibleItems) >= totalItemCount;
    }

    private void resetPageIndex() {
        pageIndex = 1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_high_rated:
                resetPageIndex();
                presenter.sortByPopularity(pageIndex);
                break;
            case R.id.action_most_popular:
                resetPageIndex();
                presenter.sortByRating(pageIndex);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //View methods
    @Override
    public void initView() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvMovieList.setLayoutManager(gridLayoutManager);
        movieListAdapter = new MovieListAdapter(getContext(), this);
        rvMovieList.setAdapter(movieListAdapter);
        presenter.fetchMovies(pageIndex);
    }

    @Override
    public void populateData(List<Result> resultList) {
        movieListAdapter.addAll(resultList);
        hideLoading();
    }

    @Override
    public void onMovieItemSelected(Result result) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIE_DETAIL, result);
        startActivity(intent);
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        Snackbar.make(tvInfo, R.string.something_went_wrong, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        rvMovieList.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        tvInfo.setVisibility(View.GONE);
        rvMovieList.setVisibility(View.VISIBLE);
    }

    @Override
    public void sortingList() {
        showLoading();
        movieListAdapter.clear();
    }
}
