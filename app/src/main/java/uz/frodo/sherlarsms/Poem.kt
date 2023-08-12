package uz.frodo.sherlarsms

import android.os.Parcel
import android.os.Parcelable

class Poem(var name:String?,var text:String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Poem> {
        override fun createFromParcel(parcel: Parcel): Poem {
            return Poem(parcel)
        }

        override fun newArray(size: Int): Array<Poem?> {
            return arrayOfNulls(size)
        }
    }
}