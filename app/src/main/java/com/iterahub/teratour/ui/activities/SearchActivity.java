package com.iterahub.teratour.ui.activities;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iterahub.teratour.R;
import com.iterahub.teratour.adapters.SearchAdapter;
import com.iterahub.teratour.interfaces.SearchItemClickedInterface;
import com.iterahub.teratour.models.PostModels;
import com.iterahub.teratour.models.User;
import com.iterahub.teratour.utils.Constants;
import com.iterahub.teratour.utils.PrefUtils;
import com.iterahub.teratour.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchItemClickedInterface{

    @BindView(R.id.search_recycler_view)
    RecyclerView searchRV;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;

    private List<Object> dataModelObserver;
    private AppViewModel appViewModel;
    PrefUtils prefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        prefUtils = new PrefUtils(this);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        ButterKnife.bind(this);
        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            progressBar.setVisibility(View.VISIBLE);
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            doSearch(query);
        }
    }

    private void doSearch(String searchQuery){
        appViewModel.search(prefUtils.getAuth(),searchQuery).observe(this,searchResults -> {
            progressBar.setVisibility(View.GONE);
            if(searchResults != null){
                Log.e(this.getClass().getSimpleName(),String.valueOf(searchResults.isStatus()));
                dataModelObserver = new ArrayList<>();
                if(searchResults.getSearchData() != null && searchResults.getSearchData().getUserDataList() != null){
                    dataModelObserver.addAll(searchResults.getSearchData().getUserDataList());
                }
                if(searchResults.getSearchData() != null && searchResults.getSearchData().getPostDataList() != null){
                    dataModelObserver.addAll(searchResults.getSearchData().getPostDataList());
                }

                SearchAdapter adapter = new SearchAdapter(this, dataModelObserver,this);
                searchRV.setAdapter(adapter);
                noDataTv.setVisibility(View.GONE);
                if(searchResults.getSearchData() == null || (searchResults.getSearchData() != null &&
                        searchResults.getSearchData().getPostDataList() == null &&
                        searchResults.getSearchData().getUserDataList() == null)){
                    noDataTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void profileImageClicked(int position) {


    }

    @Override
    public void profileClicked(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();


        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);

        return true;
    }
}
