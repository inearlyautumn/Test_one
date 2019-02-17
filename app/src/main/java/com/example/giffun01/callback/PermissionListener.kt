package com.example.giffun01.callback

interface PermissionListener {
    fun onGranted()
    fun onDenied(deniedPermissions: List<String>)

}
