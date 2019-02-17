package com.example.giffun01.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
/**
 * 版本更新的实体类封装，如果存在版本更新则会提供下述信息。
 * */
class Version(
    @SerializedName("change_log") val changeLog: String,
    @SerializedName("is_force") val isForce: Boolean,
    val url: String,
    @SerializedName("version_name") val sersionName: String,
    @SerializedName("version_code") val versionCode: Int,
    val channel: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(changeLog)
        parcel.writeByte(if (isForce) 1 else 0)
        parcel.writeString(url)
        parcel.writeString(sersionName)
        parcel.writeInt(versionCode)
        parcel.writeString(channel)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Version> {
        override fun createFromParcel(parcel: Parcel): Version {
            return Version(parcel)
        }

        override fun newArray(size: Int): Array<Version?> {
            return arrayOfNulls(size)
        }
    }
}
