package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.Test_Attribute_Animation.ColorEvaluator;
import com.example.myapplication.Test_Attribute_Animation.MyView2;
import com.example.myapplication.Test_Attribute_Animation.SqureEvaluator;

public class TestActivity extends Activity {
    ImageView imageView_alpha;
    ImageView imageView_scale;
    ImageView imageView_scale2;
    ImageView imageView_rotate;
    ImageView imageView_translate;
    ImageView imageView_set;
    ImageView imageView_attribute;
    ImageView imageView_attribute2;
    MyView2 myView2;


    RefreshableView refreshableView;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = {"A", "B", "C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

        imageView_alpha = findViewById(R.id.test_alpha);
        imageView_scale = findViewById(R.id.test_scale);
        imageView_scale2 = findViewById(R.id.test_scale2);
        imageView_rotate = findViewById(R.id.test_rotate);
        imageView_translate = findViewById(R.id.test_translate);
        imageView_set = findViewById(R.id.test_set);
        imageView_attribute = findViewById(R.id.test_attribute);
        imageView_attribute2 = findViewById(R.id.test_attribute2);
        myView2 = findViewById(R.id.myview2);


        /*
        ValueAnimator for imageView_attribute
         */
        ValueAnimator valueAnimator = ValueAnimator.ofInt(imageView_attribute.getLayoutParams().width, 500);

//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//        });


        // 初始值 = 当前图片的宽度，此处在xml文件中设置为100
        // 结束值 = 500
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置
        // 即默认设置了如何从初始值150 过渡到 结束值500

        // 步骤2：设置动画的播放各种属性
        valueAnimator.setDuration(2000);
        // 设置动画运行时长:1s

        // 步骤3：将属性数值手动赋值给对象的属性:此处是将 值 赋给 图片的宽度
        // 设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                System.out.println(currentValue);
                // 输出每次变化后的属性值进行查看

                imageView_attribute.getLayoutParams().width = currentValue;
                // 每次值变化时，将值手动赋值给对象的属性
                // 即将每次变化后的值 赋 给图片的宽度，这样就实现了图片宽度属性的动态变化

                // 步骤4：刷新视图，即重新绘制，从而实现动画效果
                imageView_attribute.requestLayout();

            }
        });

        valueAnimator.start();
        // 启动动画

        /*
        ObjectAnimator for imageView_attribute2
         */

//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView_attribute2, "alpha", 1f, 0f, 1f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView_attribute2, "rotation", 0f, 360f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView_attribute2, "translationX", imageView_attribute2.getTranslationX(), 300,imageView_attribute2.getTranslationX());
//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView_attribute2, "scaleX", 1f, 3f, 1f);
//
//        animator.setDuration(500);
//        // 设置动画运行的时长
//
//        animator.setStartDelay(500);
//        // 设置动画延迟播放时间
//
//        animator.setRepeatCount(5);
//        // 设置动画重复播放次数 = 重放次数+1
//        // 动画播放次数 = infinite时,动画无限重复
//
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        // 设置重复播放动画模式
//        // ValueAnimator.RESTART(默认):正序重放
//        // ValueAnimator.REVERSE:倒序回放
//
//        animator.start();
//        // 启动动画


//        ViewWrapper wrapper = new ViewWrapper(imageView_attribute2);
//         //创建包装类,并传入动画作用的对象
//
//        ObjectAnimator animator = ObjectAnimator.ofInt(wrapper, "width", 2000);
//        animator.setDuration(5000).start();




        /*
        AnimatorSet.play(Animator anim)   ：播放当前动画
        AnimatorSet.after(long delay)   ：将现有动画延迟x毫秒后执行
        AnimatorSet.with(Animator anim)   ：将现有动画和传入的动画同时执行
        AnimatorSet.after(Animator anim)   ：将现有动画插入到传入的动画之后执行
        AnimatorSet.before(Animator anim) ：  将现有动画插入到传入的动画之前执行
        */

//        // 步骤1：设置需要组合的动画效果
//        ObjectAnimator translation = ObjectAnimator.ofFloat(imageView_attribute2, "translationX", imageView_attribute2.getTranslationX(), 300, imageView_attribute2.getTranslationX());
//// 平移动画
//        ObjectAnimator rotate = ObjectAnimator.ofFloat(imageView_attribute2, "rotation", 0f, 360f);
//// 旋转动画
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView_attribute2, "alpha", 1f, 0f, 1f);
//// 透明度动画
//
//// 步骤2：创建组合动画的对象
//        AnimatorSet animSet = new AnimatorSet();
//
//// 步骤3：根据需求组合动画
//        animSet.play(translation).with(rotate).before(alpha);
//        animSet.setDuration(5000);
//
//// 步骤4：启动动画
//        animSet.start();

//        //imageView_attribute2 = (ImageView) findViewById(R.id.test_attribute2);
//        // 创建动画作用对象：此处以ImageView为例
//
//        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_animation);
//// 创建组合动画对象  &  加载XML动画
//        animator.setTarget(imageView_attribute2);
//        // 设置动画作用对象
//        animator.start();
//        // 启动动画


// 使用解析
        //View.animate().xxx().xxx();
        // ViewPropertyAnimator的功能建立在animate()上
        // 调用animate()方法返回值是一个ViewPropertyAnimator对象,之后的调用的所有方法都是通过该实例完成
        // 调用该实例的各种方法来实现动画效果
        // ViewPropertyAnimator所有接口方法都使用连缀语法来设计，每个方法的返回值都是它自身的实例
        // 因此调用完一个方法后可直接连缀调用另一方法,即可通过一行代码就完成所有动画效果

// 以下是例子
        //imageView_attribute2 = (ImageView) findViewById(R.id.test_attribute2);
        // 创建动画作用对象：此处以Button为例

        //imageView_attribute2.animate().alpha(0.5f).setDuration(3000);
        // 单个动画设置:将按钮变成透明状态
        //imageView_attribute2.animate().alpha(0f).setDuration(5000).setInterpolator(new BounceInterpolator());
        // 单个动画效果设置 & 参数设置
        imageView_attribute2.animate().alpha(0f).setDuration(5000).scaleX(50).scaleY(50);
        // 组合动画:将按钮变成透明状态再移动到(500,500)处

        // 特别注意:
        // 动画自动启动,无需调用start()方法.因为新的接口中使用了隐式启动动画的功能，只要我们将动画定义完成后，动画就会自动启动
        // 该机制对于组合动画也同样有效，只要不断地连缀新的方法，那么动画就不会立刻执行，等到所有在ViewPropertyAnimator上设置的方法都执行完毕后，动画就会自动启动
        // 如果不想使用这一默认机制，也可以显式地调用start()方法来启动动画



// ofFloat()作用有两个
// 1. 创建动画实例
// 2. 参数设置：参数说明如下
// Object object：需要操作的对象
// String property：需要操作的对象的属性
// float ....values：动画初始值 & 结束值（不固定长度）
// 若是两个参数a,b，则动画效果则是从属性的a值到b值
// 若是三个参数a,b,c，则则动画效果则是从属性的a值到b值再到c值
// 以此类推
// 至于如何从初始值 过渡到 结束值，同样是由估值器决定，此处ObjectAnimator.ofFloat（）是有系统内置的浮点型估值器FloatEvaluator，同ValueAnimator讲解









        /*
        颜色渐变的圆
         */
        ObjectAnimator anim = ObjectAnimator.ofObject(myView2, "color", new ColorEvaluator(),
                "#0000AA", "#FFFFFF");
        // 设置自定义View对象、背景颜色属性值 & 颜色估值器
        // 本质逻辑：
        // 步骤1：根据颜色估值器不断 改变 值
        // 步骤2：调用set（）设置背景颜色的属性值（实际上是通过画笔进行颜色设置）
        // 步骤3：调用invalidate()刷新视图，即调用onDraw（）重新绘制，从而实现动画效果

        anim.setDuration(4000);
        anim.start();




        Animation animation_alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_demo);
        Animation animation_scale = AnimationUtils.loadAnimation(this, R.anim.scale_demo);
        Animation animation_scale2 = AnimationUtils.loadAnimation(this, R.anim.scale_demo2);
        Animation animation_rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_demo);
        Animation animation_translate = AnimationUtils.loadAnimation(this, R.anim.translate_demo);
        Animation animation_set = AnimationUtils.loadAnimation(this, R.anim.set_demo);

        /**
         * 设置动画的监听
         */
        animation_set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


//        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
//        alphaAnimation.setDuration(3000);
//        imageView_alpha.startAnimation(alphaAnimation);
//
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(3000);
//        imageView_scale.startAnimation(scaleAnimation);
//
//        RotateAnimation rotateAnimation = new RotateAnimation(0,180);
//        rotateAnimation.setDuration(3000);
//        imageView_rotate.startAnimation(rotateAnimation);
//
//        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0.5f);
//        translateAnimation.setDuration(3000);
//        translateAnimation.setFillAfter(true);
//        imageView_translate.startAnimation(translateAnimation);


//        1.public AlphaAnimation (Context context, AttributeSet attrs)
//        context：当前上下文
//        attrs：xml中读取的属性设置
//        2.public AlphaAnimation (float fromAlpha, float toAlpha)

//        imageView_alpha.startAnimation(animation_alpha);
//        imageView_scale.startAnimation(animation_scale);
//        imageView_scale2.startAnimation(animation_scale2);
//        imageView_rotate.startAnimation(animation_rotate);
//        imageView_translate.startAnimation(animation_translate);

        imageView_set.startAnimation(animation_set);




    }

    // 提供ViewWrapper类,用于包装View对象
    // 本例:包装Button对象
    private static class ViewWrapper {
        private View mTarget;

        // 构造方法:传入需要包装的对象
        public ViewWrapper(View target) {
            mTarget = target;
        }

        // 为宽度设置get（） & set（）
        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

    }
}
