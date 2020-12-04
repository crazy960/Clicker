package com.example.clicker

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener

class GunView : View {

    private val context_: Context = context
    private var bitmap : Bitmap = BitmapFactory.decodeResource( context_.resources, R.drawable.gun_level0)
    private var isAnim : Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap , 250 , 250 , false)
    }

    public fun Draw( canvas : Canvas){
        canvas.save()
        canvas.translate(x ,y)
        canvas.drawBitmap(bitmap , 0F,  0F , null)
        canvas.restore()
    }

    public fun Shoot(){

        if(isAnim)
            return

        var set : AnimatorSet = AnimatorSet()
        var set_1 : AnimatorSet = AnimatorSet()

        var x_0 : ObjectAnimator =  ObjectAnimator.ofFloat(this , "translationY",y , y-70F).apply {
            duration = 20
        }
        var y_0 : ObjectAnimator =  ObjectAnimator.ofFloat(this , "translationX",x , x-70F).apply {
            duration = 20
        }

        var x_1 : ObjectAnimator =  ObjectAnimator.ofFloat(this , "translationY",y-70F , y).apply {
            duration = 70
        }
        var y_1 : ObjectAnimator =  ObjectAnimator.ofFloat(this , "translationX",x-70F , x).apply {
            duration = 70
        }

        set.addListener(
                onStart = { isAnim = true } ,
                onEnd = { set_1.playTogether( x_1 , y_1 )
                          set_1.start() }
        )
        set_1.addListener(
                onEnd = { isAnim = false }
        )
        //set.play(anim1).before(anim2)
        set.playTogether(x_0,y_0)
        set.start()
    }



}
