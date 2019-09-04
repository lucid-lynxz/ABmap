package org.lynxz.abmap.ui.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.*
import kotlinx.android.synthetic.main.fragment_amap.*
import org.lynxz.abmap.R
import org.lynxz.abmap.ui.BaseFragment
import org.lynxz.abmap.ui.trans.PermissionResultInfo
import org.lynxz.abmap.util.Logger
import showToast

/**
 * 高德地图fragment
 * */
class AMapFragment : BaseFragment(), IMapOperation {
    private val TAG = "AMapFragment"
    private lateinit var amapViewModel: AMapViewModel
    private var lastLocation: AMapLocation? = null

    private val myLocationStyle = MyLocationStyle().apply {
        myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
    }

    private val myCancelCallback = object : AMap.CancelableCallback {
        override fun onFinish() = updateMarkerPos()

        override fun onCancel() = updateMarkerPos()

        private fun updateMarkerPos() {
            lastLocation?.let {
                locationMarker?.setPosition(LatLng(it.latitude, it.longitude))
            }
        }
    }

    override fun changeCameraCenter(lon: Double, lat: Double) {
    }

    override fun getLayout() = R.layout.fragment_amap

    override fun showTraffic() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amapViewModel = ViewModelProviders.of(this).get(AMapViewModel::class.java)
        amapViewModel.locationLiveData.observe(this, Observer { location ->
            lastLocation = location
            amapViewModel.stopLocation()

            if (location?.errorCode != 0) {
                showToast("定位失败,请检查GPS")
                return@Observer
            }

            val latLng = LatLng(location.latitude, location.longitude)
            if (locationMarker == null) { //首次定位
                locationMarker = map_amap.map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_marker))
                        .anchor(0.5f, 0.5f)
                )

                //首次定位,选择移动到地图中心点并修改级别到15级
                map_amap.map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            } else {
                map_amap.map.animateCamera(
                    CameraUpdateFactory.changeLatLng(latLng), 500, myCancelCallback
                )
            }
        })

        map_amap.onCreate(savedInstanceState)
        map_amap.map?.let { map ->
            map.myLocationStyle = myLocationStyle
            map.setLocationSource(amapViewModel)
            map.isMyLocationEnabled = true // 定位图层按钮是否可点击
            map.uiSettings?.apply {
                zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_CENTER // 右边中间显示缩放按钮
                isCompassEnabled = true // 显示指南针
                isMyLocationButtonEnabled = true // 定位logo
            }
        }

        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onResume() {
        super.onResume()
        map_amap.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_amap.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map_amap.onDestroy()
    }


    private var locationMarker: Marker? = null
}