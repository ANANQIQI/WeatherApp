package com.gary.weatherdemo.mvp;

import com.gary.weatherdemo.model.CityBean;

/**
 * Created by GaryCao on 2019/03/13.
 * <p>
 * MVP应用框架元素5：IModel （用于MVVM框架对比&参考）
 */
public interface IModel {
    void getCityWeather(CityBean citybean);
}
