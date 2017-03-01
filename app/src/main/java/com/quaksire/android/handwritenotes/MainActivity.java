package com.quaksire.android.handwritenotes;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.quaksire.android.handwritenotes.adapter.DoodleCursorAdapter;
import com.quaksire.android.handwritenotes.business.MainActivityPresenter;
import com.quaksire.android.handwritenotes.interfaces.IMainActivityPresenter;
import com.quaksire.android.handwritenotes.interfaces.IMainActivityView;
import com.quaksire.android.handwritenotes.util.TypeFaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.quaksire.android.handwritenotes.parameters.IntentParameters.VALUE_DEFAULT_IMAGE_ID;


public class MainActivity
        extends AppCompatActivity
        implements IMainActivityView {

    //======================================================================
    // View injection
    //======================================================================

    @BindView(R.id.my_recycler_view)
    private RecyclerView mRecycleView;

    @BindView(R.id.empty_list_placeholder)
    private ImageView mImageView;

    @BindView(R.id.toolbar)
    private Toolbar mToolbar;

    @BindView(R.id.toolbar_layout)
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private DoodleCursorAdapter mAdapter;

    private IMainActivityPresenter mPresenter;

    //======================================================================
    // Activity lifecycle
    //======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QUAKSIRE", "MainActivity.onCreate()");
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.mPresenter = new MainActivityPresenter(getApplicationContext(), this);
        this.mPresenter.loadIntent(getIntent());

        setSupportActionBar(this.mToolbar);

        this.mToolbar.setTitle(R.string.title_activity_main);

        Typeface myTypeface = TypeFaceUtils.getTypeFace(getApplicationContext());

        this.collapsingToolbarLayout.setCollapsedTitleTypeface(myTypeface);
        this.collapsingToolbarLayout.setExpandedTitleTypeface(myTypeface);
        this.mRecycleView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QUAKSIRE", "MainActivity.onResume()");
        this.mPresenter.openDatabase();
        this.mPresenter.loadContent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QUAKSIRE", "MainActivity.onPause()");
        this.mPresenter.closeDatabase();
    }

    //======================================================================
    // View actions
    //======================================================================

    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    private void startDrawActivity() {
        Log.d("QUAKSIRE", "MainActivity.startDrawActivity()");
        this.mPresenter.startDrawActvitivy(VALUE_DEFAULT_IMAGE_ID);
    }

    //======================================================================
    // Other
    //======================================================================

    @Override
    public void populateListValues(Cursor cursor) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.mRecycleView.setLayoutManager(mLayoutManager);
        if(this.mAdapter == null) {
            this.mAdapter = new DoodleCursorAdapter(
                    getApplicationContext(),
                    cursor,
                    this.mPresenter);
            this.mRecycleView.setAdapter(this.mAdapter);
        } else {
            //Update list
            this.mAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void setPlaceholderVisible(boolean visible) {
        //Does cursor contains elements? if not, display no item elements
        this.mImageView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }
}
