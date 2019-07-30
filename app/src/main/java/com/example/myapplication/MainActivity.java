package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DIY.MyDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ImageView mAnimation;
    private AnimationDrawable mAnimationDrawable;
    private MyDialog myDialog;

    private EditText mEditTextPhone;
    private EditText mEditTextCheckCode;
    private String mPhoneNumber;
    private String mCheckCode;
    private Button mAcquireCheckCode;
    private Button mLogIn;
    private TextView mLogInWithId;

    private ImageView mTick;
    private ImageView mCross;

    private ImageView mWeChat;
    private ImageView mWeiBo;
    private ImageView mQQ;

    private TextView mProtocal;
    private EditText mPhone86;

    private boolean mInputPhone = false;
    private String mFault = "您输入的电话号码过短";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /*
    点击空白隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*
    初始化
     */
    private void initView() {
        mAnimation = findViewById(R.id.animation);


        mEditTextPhone = (EditText) findViewById(R.id.phone);
        mEditTextCheckCode = findViewById(R.id.checkcode);
        mTick = findViewById(R.id.tick);
        mCross = findViewById(R.id.cross);
        mCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTextPhone.setText("");
            }
        });

        /**
         * 修改电话号码的焦点监听
         */
        mEditTextPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                EditText textView = (EditText) view;
                if (hasFocus) {
                    textView.setHint("");
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    if (TextUtils.isEmpty(textView.getText())) {
                        textView.setHint("123456");
                    }

                    if (!mInputPhone) {
                        Toast.makeText(MainActivity.this, mFault, Toast.LENGTH_SHORT).show();
                        mTick.setVisibility(View.GONE);
                        mCross.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        /**
         * 修改电话号码的文本监听
         */
        mEditTextPhone.addTextChangedListener(new TextWatcher() {
            private String mBefore;// 用于记录变化前的文字
            private int mCursor;// 用于记录变化时光标的位置

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                mBefore = charSequence.toString();
                mCursor = start;
                //Toast.makeText(MainActivity.this, mBefore+"   "+mCursor, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /**
                 * 验证手机号码是否合法
                 * 176, 177, 178;
                 * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189;
                 * 145, 147;
                 * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139;
                 * 150, 151, 152, 153, 155, 156, 157, 158, 159;
                 *
                 * "13"代表前两位为数字13,
                 * "[0-9]"代表第二位可以为0-9中的一个,
                 * "[^4]" 代表除了4
                 * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
                 */

                String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
                Pattern p = Pattern.compile(telRegex);
                Matcher m = p.matcher(editable.toString());


                if (!m.matches() && editable.toString().length() == 11) {
                    Toast.makeText(MainActivity.this, "您输入的电话号码格式错误", Toast.LENGTH_SHORT).show();
                    mFault = "您输入的电话号码格式错误";
                    mInputPhone = false;
                    mTick.setVisibility(View.GONE);
                    mCross.setVisibility(View.VISIBLE);
                }

                if (editable.toString().length() > 11) {
                    Toast.makeText(MainActivity.this, "您输入的电话号码超出长度限制", Toast.LENGTH_SHORT).show();
                    mFault = "您输入的电话号码超出长度限制";
                    mInputPhone = false;
                    mTick.setVisibility(View.GONE);
                    mCross.setVisibility(View.VISIBLE);
                }

                if (editable.toString().length() > 13) {
                    mEditTextPhone.setText(mBefore);
                }

                if (editable.toString().length() < 11) {
                    mFault = "您输入的电话号码长度不足";
                    mInputPhone = false;
                    mTick.setVisibility(View.GONE);
                    mCross.setVisibility(View.GONE);

                }

                if (m.matches()) {
                    mCross.setVisibility(View.GONE);
                    mTick.setVisibility(View.VISIBLE);
                    mInputPhone = true;
                }

            }
        });

        mPhoneNumber = mEditTextPhone.getText().toString();

        EditText editTextCheck = (EditText) findViewById(R.id.checkcode);

        /**
         * 输入验证码的焦点监听
         */
        mEditTextCheckCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                EditText textView = (EditText) view;
                if (hasFocus) {
                    textView.setHint("");
                } else {
                    if (TextUtils.isEmpty(textView.getText())) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        textView.setHint("||");
                    }
                }
            }
        });


        mCheckCode = editTextCheck.getText().toString();

        /**
         * 获取验证码按钮的监听
         */
        mAcquireCheckCode = findViewById(R.id.acquirecheck);
        mAcquireCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "获取验证码!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 登录按钮的监听
         */
        mLogIn = findViewById(R.id.login);
        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                myDialog = new MyDialog(MainActivity.this, R.style.MyDialog);
//                myDialog.show();
//
//                Window window = myDialog.getWindow();
//                //设置显示位置
//                WindowManager.LayoutParams lp = window.getAttributes();
//                //不设置lp.width 和lp.height，对话框大小由布局决定
//                // 自定义位置
//                lp.x = 0;
//                lp.y = -450;
//                lp.gravity =  Gravity.TOP;//不设置这个时,lp.x和lp.y无效
//                window.setGravity(Gravity.CENTER);
//                // 居中显示
//                window.setAttributes(lp);
//
//                WindowManager.LayoutParams lpback=getWindow().getAttributes();
//                lpback.alpha=0.8f;
//                getWindow().setAttributes(lpback);
//
//                myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        WindowManager.LayoutParams lpback=getWindow().getAttributes();
//                        lpback.alpha=1f;
//                        getWindow().setAttributes(lpback);
//                    }
//                });
                //alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明



//                mAnimationDrawable = (AnimationDrawable)getResources().getDrawable(R.drawable.waiting_animation);
//                mAnimation.setImageDrawable(mAnimationDrawable);
//                mAnimationDrawable.start();

                if (mInputPhone) {
                    loading();
                    CheckAccount();
                } else {
                    Toast.makeText(MainActivity.this, mFault, Toast.LENGTH_SHORT).show();
                    mTick.setVisibility(View.GONE);
                    mCross.setVisibility(View.VISIBLE);
                    mEditTextPhone.requestFocus();
                    return;
                }
            }

            private void CheckAccount() {

                boolean check = false;

                //Send request

                if (!check) {
                    mEditTextCheckCode.setText("");
                    mEditTextCheckCode.requestFocus();
                } else {
                    Toast.makeText(MainActivity.this, "登录", Toast.LENGTH_SHORT).show();
                }

                if (check) {


                }
            }
        });

        /**
         * 账号密码登录的监听
         */
        mLogInWithId = findViewById(R.id.loginWithId);
        mLogInWithId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "账号密码登录!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 微信登录
         */
        mWeChat = findViewById(R.id.wechat);
        mWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "微信登录!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 微博登录
         */
        mWeiBo = findViewById(R.id.weibo);
        mWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "微博登录!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * qq登录
         */
        mQQ = findViewById(R.id.qq);
        mQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "QQ登录!", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        阅读协议的监听
         */
        mProtocal = findViewById(R.id.xieyi);
        final SpannableStringBuilder style = new SpannableStringBuilder();

        style.append("注册登录即代表同意4D书城用户协议");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, "显示协议!", Toast.LENGTH_SHORT).show();
            }
        };
        style.setSpan(clickableSpan, 7, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mProtocal.setText(style);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 7, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //配置给TextView
        mProtocal.setMovementMethod(LinkMovementMethod.getInstance());
        mProtocal.setText(style);

        /*
        修改+86的颜色
         */
        mPhone86 = findViewById(R.id.phone86);
        final SpannableStringBuilder phone86Style = new SpannableStringBuilder();
        phone86Style.append("手机号  +86");

        ForegroundColorSpan foregroundColorSpan86 = new ForegroundColorSpan(Color.parseColor("#C0C0C0"));
        phone86Style.setSpan(foregroundColorSpan86, 5, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //配置给TextView
        mPhone86.setMovementMethod(LinkMovementMethod.getInstance());
        mPhone86.setText(phone86Style);
    }

    public void loading(){
        myDialog = new MyDialog(MainActivity.this, R.style.MyDialog);


        Window window = myDialog.getWindow();
        //设置显示位置
        WindowManager.LayoutParams lp = window.getAttributes();
        //不设置lp.width 和lp.height，对话框大小由布局决定
        // 自定义位置
        lp.x = 0;
        lp.y = -450;
        lp.gravity =  Gravity.TOP;//不设置这个时,lp.x和lp.y无效
        window.setGravity(Gravity.CENTER);
        // 居中显示
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.dialogWindowAnim);

        myDialog.show();

        WindowManager.LayoutParams lpback=getWindow().getAttributes();
        lpback.alpha=0.8f;
        getWindow().setAttributes(lpback);

        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                WindowManager.LayoutParams lpback=getWindow().getAttributes();
                lpback.alpha=1f;
                getWindow().setAttributes(lpback);
            }
        });
    }
}
