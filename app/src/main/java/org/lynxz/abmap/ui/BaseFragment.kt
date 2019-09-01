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
import org.lynxz.abmap.util.Logger

abstract class BaseFragment : Fragment(), IPermissionCallback {
    private val TAG = "BaseFragment"

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
        Logger.d(TAG, "permissionFrag lazy initing")
        BaseTransFragment.getTransFragment(
            activity!!, "permission_tag_fragment",
            PermissionFragment()
        )
    }

    fun requestPermission(permission: String) {
        Logger.d(TAG, "permissionFrag requestPermission start $permissionFrag")
        permissionFrag?.requestPermission(permission, this)
        Logger.d(TAG, "permissionFrag requestPermission end $permissionFrag")
    }

    fun requestPermissions(permissions: Array<String>) {
        permissionFrag?.requestPermissions(permissions, this)
    }

    override fun onRequestResult(permission: PermissionResultInfo) {
    }
}