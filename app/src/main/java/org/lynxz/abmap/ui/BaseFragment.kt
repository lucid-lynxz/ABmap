package org.lynxz.abmap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.lynxz.abmap.ui.trans.BaseTransFragment
import org.lynxz.abmap.ui.trans.IPermissionCallback
import org.lynxz.abmap.ui.trans.PermissionFragment
import org.lynxz.abmap.ui.trans.PermissionResultInfo

abstract class BaseFragment : Fragment(), IPermissionCallback {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(getLayout(), container, false)!!

    /**
     * 获取布局layoutId
     * */
    @LayoutRes
    abstract fun getLayout(): Int


    private val permissionFrag by lazy {
        BaseTransFragment.getTransFragment(
            activity!!, "permission_tag_fragment",
            PermissionFragment()
        )
    }

    fun requestPermission(permission: String) {
        permissionFrag?.requestPermission(permission, this)
    }

    fun requestPermissions(permissions: Array<String>) {
        permissionFrag?.requestPermissions(permissions, this)
    }

    override fun onRequestResult(permission: PermissionResultInfo) {
    }
}