package com.example.clicker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class textBar : LinearLayout {

    public var mount: Int = 0
    public var type : Int = 0

    constructor(context: Context ) : super(context)
    constructor(context: Context , attr: AttributeSet) : super(context , attr)
    constructor(context: Context , attr: AttributeSet , defStyle : Int ) : super( context , attr , defStyle)



}