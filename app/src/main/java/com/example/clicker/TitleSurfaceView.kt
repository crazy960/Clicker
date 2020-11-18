package com.example.clicker

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView;

class MainThread : Thread(){
    var number:Int = 0
    override fun run() {
        super.run()
    }


}
class TitleSurfaceView : SurfaceView , SurfaceHolder.Callback {


    var height_:Int = 0
    var width_:Int = 0
    private val context_:Context
    private val holder_ :SurfaceHolder

    init {
        this.context_= context;
        this.holder_ = getHolder();
        holder_.addCallback(this)
    }

    constructor(context: Context):super(context){

    }
    constructor(context: Context , attr: AttributeSet) : super(context,attr) {

    }
    constructor(context: Context , attr:AttributeSet , defStyle:Int) : super(context , attr , defStyle){


    }

    public fun InputSize(x:Int , y:Int){
        height_=y
        width_=x
        getHolder().setFixedSize(x,y)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //TODO("Not yet implemented")
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //TODO("Not yet implemented")
    }

}