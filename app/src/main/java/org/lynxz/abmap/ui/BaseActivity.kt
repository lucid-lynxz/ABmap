package org.lynxz.abmap.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import org.lynxz.abmap.ui.trans.BaseTransFragment
import org.lynxz.abmap.ui.trans.IPermissionCallback
import org.lynxz.abmap.ui.trans.PermissionFragment
import org.lynxz.abmap.ui.trans.PermissionResultInfo

abstract class BaseActivity : AppCompatActivity(), IPermissionCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        doAfterOnCreate()
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun doAfterOnCreate()


    private val permissionFrag by lazy {
        BaseTransFragment.getTransFragment(
            this, "permission_tag_activity",
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