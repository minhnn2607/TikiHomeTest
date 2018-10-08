package vn.nms.hotkey;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private HotKeyWordAdapter mAdapter;
    private ProgressBar progressBar;
    private LinearLayout llError;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadHoyKey();
    }

    private void init() {
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        progressBar = findViewById(R.id.progressbar);
        llError = findViewById(R.id.llError);
        mAdapter = new HotKeyWordAdapter();
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(
                recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            itemDecorator.setDrawable(dividerDrawable);
        }
        recyclerView.addItemDecoration(itemDecorator);
        findViewById(R.id.btnRetry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.loadHoyKey();
            }
        });
    }

    private void loadHoyKey() {
        progressBar.setVisibility(View.VISIBLE);
        mainPresenter.loadHoyKey();
    }

    @Override
    public void onGetHoyKeySuccess(List<HotKeyWordModel> results) {
        llError.setVisibility(View.GONE);
        mAdapter.setData(results);
    }

    @Override
    public void onGetHotKeyFail() {
        llError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (mainPresenter != null) {
            mainPresenter.detachView();
        }
        super.onDestroy();
    }
}
