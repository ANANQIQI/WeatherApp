package com.gary.weatherdemo.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.example.commonui.ActionBar;
import com.example.commonui.listener.IActBarOnClickListener;
import com.gary.weatherdemo.R;
import com.gary.weatherdemo.model.CityBean;
import com.gary.weatherdemo.databinding.WeatherMainActivityBinding;
import com.gary.weatherdemo.admob.BannerAdActivity;
import com.gary.weatherdemo.utils.LogUtils;
import com.gary.weatherdemo.utils.WeatherUtils;
import com.gary.weatherdemo.viewmodel.MainActivityViewModel;

public class WtMainActivity extends BannerAdActivity implements IActBarOnClickListener {
    private MainActivityViewModel viewModel;
    private ActionBar actionBar;

    @Override
    public void onCreateNew(Bundle savedInstanceState) {
        initViews();
        /*EventBus.getDefault().register(this);*/
    }

    private void initViews() {
        WeatherMainActivityBinding binding = DataBindingUtil.<WeatherMainActivityBinding>setContentView(this, R.layout.activity_weather_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        binding.setViewModel(viewModel);

        initActionBar();
        updateCityTitleView();
    }

    @Override
    protected void onResume() {
        if (viewModel != null) {
            viewModel.loadCurCityInfo();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }*/
    }

    private void initActionBar() {
        actionBar = (ActionBar) findViewById(R.id.action_bar);
        actionBar.setOnClickListener(this);
    }

    private void updateCityTitleView() {
        viewModel.getCurCityInfo().observe(this, new Observer<CityBean>() {
            @Override
            public void onChanged(@Nullable CityBean cityBean) {
                if (null != actionBar) {
                    actionBar.setTitle(cityBean.adrName);
                }
                viewModel.queryCityWeather(cityBean.adcCode);
            }
        });
    }

    @Override
    public void onClickedLeftBtn() {
        LogUtils.d("onClickedLeftBtn()");
        WeatherUtils.startActivity(this, WtSearchActivity.class);
    }

    @Override
    public void onClickedRightBtn() {
        LogUtils.d("onClickedRightBtn()");
        WeatherUtils.startActivity(this, WtSettingActivity.class);
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        mText.setText(messageEvent.getMessage());
    }*/
}