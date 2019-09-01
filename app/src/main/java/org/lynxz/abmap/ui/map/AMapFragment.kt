package org.lynxz.abmap.ui.map

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.model.MyLocationStyle
import kotlinx.android.synthetic.main.fragment_amap.*
import org.lynxz.abmap.R
import org.lynxz.abmap.ui.BaseFragment
import org.lynxz.abmap.ui.home.HomeViewModel

class AMapFragment : BaseFragment(), IMapOperation {

    private lateinit var amapViewModel: AMapViewModel

    override fun changeCameraCenter(lon: Double, lat: Double) {
    }

    override fun getLayout() = R.layout.fragment_amap

    override fun showTraffic() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amapViewModel = ViewModelProviders.of(this).get(AMapViewModel::class.java)

        map_amap.onCreate(savedInstanceState)
        map_amap.map?.let { map ->
            map.setLocationSource(amapViewModel)
            map.isMyLocationEnabled = true
            map.uiSettings?.apply {
                zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_CENTER
                isMyLocationButtonEnabled = true
            }
        }
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
}