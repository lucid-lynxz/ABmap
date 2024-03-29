package org.lynxz.abmap.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.LocationSource
import org.lynxz.abmap.util.Logger

class AMapViewModel(application: Application) : AndroidViewModel(application), LocationSource,
    AMapLocationListener {

    val locationLiveData = MutableLiveData<AMapLocation>()

    private val mLocationClient by lazy {
        AMapLocationClient(getApplication()).also {
            it.setLocationListener(this)
            it.setLocationOption(AMapLocationClientOption().apply {
                locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                interval = 5000
            })
        }
    }


    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mLocationClient.startLocation()
    }

    override fun deactivate() {
        mLocationClient.stopLocation()
        mLocationClient.onDestroy()
    }

    fun stopLocation() {
        mLocationClient.stopLocation()
    }

    override fun onLocationChanged(location: AMapLocation?) {
        Logger.d("onLocationChanged $location")
        mLocationClient.stopLocation()
        if (location?.errorCode == 0) { // 定位成功
            locationLiveData.postValue(location)
        }
    }

    override fun onCleared() {
        super.onCleared()
        deactivate()
    }
}
