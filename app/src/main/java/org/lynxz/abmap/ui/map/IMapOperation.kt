package org.lynxz.abmap.ui.map


/**
 * 地图fragment功能接口
 * */
interface IMapOperation {

    /**
     * 显示实时交通情况
     * */
    fun showTraffic() {}

    /**
     * 比例尺缩放按钮显隐
     * */
    fun setZoomVisibility(show: Boolean = true) {}

    /**
     * 改变地图中心点位置
     * @param lon 经度
     * @param lat 纬度
     * */
    fun changeCameraCenter(lon: Double, lat: Double)
}