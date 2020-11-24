package com.example.clicker

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView;
import java.util.*

class MainThread(context: Context , holder: SurfaceHolder , view:SurfaceView) : Thread(){
    var number:Int = 0
    private var context: Context = context
    private var holder:SurfaceHolder = holder
    private val view : SurfaceView = view

    private var initLine:Path = Path()
    private var drawLines : Queue<Path> = LinkedList()



    fun CalculateBackgroundPath(){
        val viewHeight : Float = view.height.toFloat()
        val viewWidth : Float = view.width.toFloat()
        val dx_top : Float = 0.071428F*viewWidth
        val dx_bottom : Float =0.071428F*viewWidth*5

        initLine.reset()
        initLine.moveTo(0F,viewHeight*0.65F)
        initLine.lineTo(viewWidth, viewHeight*0.65F)

        for(x in 1..13) {
            initLine.moveTo(dx_top*x, viewHeight * 0.65F)
            initLine.lineTo(-2*viewWidth+(dx_bottom*x), viewHeight )
        }

        /*
        initLine.moveTo(viewWidth*0.5F , viewHeight*0.65F)
        initLine.lineTo(viewWidth*0.5F,viewHeight)

         */



    }


    override fun run() {
        //super.run();
        var canvas: Canvas? = null
        val paint : Paint = Paint()

        CalculateBackgroundPath()

        while(true){
            canvas = this.holder.lockCanvas()
            try {

                val paint : Paint = Paint()
                paint.setColor(Color.RED)
                paint.setStyle(Paint.Style.STROKE);
                if (canvas != null) {
                    canvas.drawPath(initLine ,paint)
                    /*
                    for( s in initLine )
                        canvas.drawPath(s ,paint)

                     */

                }
            } catch ( e :Exception){
                Log.e(TAG, "[Drawing Thread]", e);
            }
            finally {
                if(canvas != null)
                    this.holder.unlockCanvasAndPost(canvas)
            }

            sleep(20)



        }


    }


}
class TitleSurfaceView : SurfaceView , SurfaceHolder.Callback {


    var height_:Int = 0
    var width_:Int = 0
    private val context_:Context
    private val holder_ :SurfaceHolder
    private val thread : MainThread;

    init {
        this.context_= context;
        this.holder_ = getHolder();
        holder_.addCallback(this)
        thread = MainThread(context , holder_ , this)
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

    public fun startThread(){
        thread.start();
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //TODO("Not yet implemented")

        startThread()
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //TODO("Not yet implemented")
    }

}