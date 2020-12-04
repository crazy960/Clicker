package com.example.clicker

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.ArrayList


class MainThread(context: Context , holder: SurfaceHolder , view:SurfaceView) : Thread(){

    class Position( var x : Float , var y : Float)
    class LineData( var start : Position , var end : Position , var v : Float , var acc : Float  )


    var number:Int = 0
    private var context: Context = context
    private var holder:SurfaceHolder = holder
    private val view : SurfaceView = view

    private var initLine:Path = Path()
    private var drawLines : ArrayList<LineData> = ArrayList()
    private var initSpeed : Float = 0.5F
    private var accSpeed : Float = 0.2F

    private var lineDelay : Int = 0
    private var lineCreateTime : Int = 10
    private var isRunning:Boolean = true

    public var gun : GunView = GunView(context)

    init{
        gun.layoutParams = ViewGroup.LayoutParams( 100 ,100 )
    }



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

        for(y in 0..10){
            var start : Position = Position(0F , viewHeight*0.65F + (viewHeight*0.035F*y))
            var end : Position = Position(viewWidth , viewHeight*0.65F + (viewHeight*0.035F*y))
            val path :LineData = LineData(start, end , initSpeed , accSpeed)
            drawLines.add(path)

        }

        gun.x = view.width * 0.2F
        gun.y = view.height * 0.5F



    }


    public fun kill(){
        isRunning = false
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun run() {
        //super.run();
        var canvas: Canvas? = null
        val paint : Paint = Paint()

        CalculateBackgroundPath()

        while(isRunning){
            canvas = this.holder.lockCanvas()

            try {

                canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);



                val paint : Paint = Paint()
                paint.color = Color.rgb(202 , 34 , 254)
                paint.style = Paint.Style.STROKE;
                paint.strokeWidth = 4F
                if (canvas != null) {
                    canvas.drawPath(initLine ,paint)
                    var lineItera = drawLines.iterator()
                    while( lineItera.hasNext() ) {
                        var p = lineItera.next()
                        val path : Path = Path()
                        path.reset()
                        path.moveTo(p.start.x , p.start.y )
                        path.lineTo(p.end.x , p.end.y)
                        p.start.y += p.v
                        p.end.y += p.v
                        p.v += p.acc
                        canvas.drawPath(path, paint)

                    }
                    //gun.y+=2
                    gun.Draw(canvas)
                    gun.invalidate()

                }
            } catch ( e :Exception){
                Log.e(TAG, "[Drawing Thread]", e);
            }
            finally {
                if(canvas != null)
                    this.holder.unlockCanvasAndPost(canvas)
            }

            sleep(30)
            lineDelay++
            if(lineDelay >= lineCreateTime)
            {
                lineDelay = 0
                val viewHeight : Float = view.height.toFloat()
                val viewWidth : Float = view.width.toFloat()

                var start : Position = Position(0F , viewHeight*0.65F )
                var end : Position = Position(viewWidth , viewHeight*0.65F )
                val path :LineData = LineData(start, end , initSpeed , accSpeed)
                drawLines.add(path)

                drawLines.removeIf { item -> item.start.y >= view.height.toFloat() }

                Log.d("Draw","Cont ="+drawLines.size)
            }
        }
    }

}


class TitleSurfaceView : SurfaceView , SurfaceHolder.Callback {


    private val context_:Context
    private val holder_ :SurfaceHolder
    private val thread : MainThread;


    init {
        this.context_= context;
        this.holder_ = getHolder();
        holder_.addCallback(this)

        thread = MainThread(context , holder_ , this)

        

    }

    constructor(context: Context):super(context)
    constructor(context: Context , attr: AttributeSet) : super(context,attr)
    constructor(context: Context , attr:AttributeSet , defStyle:Int) : super(context , attr , defStyle)

    public fun getGun():GunView{
        return thread.gun
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //TODO("Not yet implemented")
        thread.start();

    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //TODO("Not yet implemented")
        thread.kill()
    }

}