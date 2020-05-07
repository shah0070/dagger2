package com.ecommerce.myapplication.movie.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.ecommerce.myapplication.BaseApplication;
import com.ecommerce.myapplication.BaseFragment;
import com.ecommerce.myapplication.Constants;
import com.ecommerce.myapplication.R;
import com.ecommerce.myapplication.models.MovieData;
import com.ecommerce.myapplication.models.response.Result;
import com.google.android.material.snackbar.Snackbar;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author shishank
 */

public class MovieDetailFragment extends BaseFragment implements Contracts.View {

    LinearLayout llParent;

    Unbinder unbinder;

    ImageView ivMoviePoster;

    Button btnLike;

    Button btnDislike;

    TextView tvMovieTitle;

    DecoView userVoteProgress;

    TextView tvSummary;

    RelativeLayout rlParent;

    ScrollView svParent;

    TextView tvScore;



    private final float MAX_RANGE = 10f;

    @Inject
    MovieDetailPresenter presenter;

    private Result result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getActivity().getApplication())
            .getAppComponent()
            .newMovieDetailComponent(new MovieDetailModule(this))
            .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        ivMoviePoster=view.findViewById(R.id.iv_movie_poster);
        btnLike=view.findViewById(R.id.btn_like);
        btnDislike=view.findViewById(R.id.btn_dislike);
        tvMovieTitle=view.findViewById(R.id.tv_movie_title);
        userVoteProgress=view.findViewById(R.id.user_vote_progress);
        tvSummary=view.findViewById(R.id.tv_summary);
        rlParent=view.findViewById(R.id.rl_parent);
        svParent=view.findViewById(R.id.sv_parent);
        tvScore=view.findViewById(R.id.tv_score);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SeriesItem seriesItem = new SeriesItem.Builder(ContextCompat.getColor(getActivity(), android.R.color.white))
                .setRange(0, MAX_RANGE, MAX_RANGE).setInitialVisibility(true).build();
        userVoteProgress.addSeries(seriesItem);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //View Methods
    @Override
    public void initView() {

    }

    @Override
    public void PopulateData() {
        rlParent.setVisibility(View.VISIBLE);
        svParent.setVisibility(View.VISIBLE);
        if (getActivity().getIntent() != null) {
            result = getActivity().getIntent().getParcelableExtra(Constants.MOVIE_DETAIL);
            if (result != null) {
                presenter.findMovie(result.getId());
                float userVote = (float) result.getVoteAverage();
                tvScore.setText(String.format(Locale.getDefault(), "%s", result.getVoteAverage()));
                SeriesItem seriesItem1 =
                        new SeriesItem.Builder(ContextCompat.getColor(getActivity(),
                                android.R.color.holo_green_light))
                                .setRange(0, MAX_RANGE, 0)
                                .setSpinDuration(1000)
                                .setInitialVisibility(true)
                                .build();
                int series1Index = userVoteProgress.addSeries(seriesItem1);
                userVoteProgress.addEvent(new DecoEvent.Builder(userVote)
                        .setIndex(series1Index).setDuration(1000).build());

                Glide.with(getActivity()).asDrawable()
                        .load(Constants.IMAGE_URL_BASE + result.getPosterPath())
                        .into(ivMoviePoster);
                tvMovieTitle.setText(String.format(Locale.getDefault(), "%s ( %s )",
                        result.getOriginalTitle(), result.getReleaseDate()));
                tvSummary.setText(result.getOverview());

            }
        }
    }

    @Override
    public void onCompleted(MovieData movieData) {
        cancelLoadingDialog();
        String s;
        if (movieData.isLike()) {
            s = getString(R.string.like_this_movie);
            btnLike.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_light));
            btnDislike.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        } else {
            s = getString(R.string.dislike_this_movie);
            btnDislike.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_light));
            btnLike.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        }
        Snackbar.make(btnDislike, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.btn_like, R.id.btn_dislike})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_like:
                if (result != null) {
                    MovieData movieData = new MovieData();
                    movieData.setId(result.getId());
                    movieData.setLike(true);
                    movieData.setDisLike(false);
                    movieData.setName(result.getOriginalTitle());
                    presenter.updateMovie(movieData);
                }
                break;
            case R.id.btn_dislike:
                if (result != null) {
                    MovieData movieData = new MovieData();
                    movieData.setId(result.getId());
                    movieData.setDisLike(true);
                    movieData.setLike(false);
                    movieData.setName(result.getOriginalTitle());
                    presenter.updateMovie(movieData);
                }
                break;
        }
    }
}
