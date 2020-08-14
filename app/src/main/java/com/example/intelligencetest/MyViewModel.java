package com.example.intelligencetest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import java.util.Random;
import java.util.logging.Level;

public class MyViewModel extends AndroidViewModel {      //AVM类可以访问全局资源，访问shared preference
    private SavedStateHandle handle;                             //保存临时数据,当viewmodel消失重新创建时可以重构数据
    private static String KEY_HIGH_SCORE="high_score";
    private static String KEY_LEFT_NUM="left_num";
    private static String KEY_RIGHT_NUM="right_num";
    private static String KEY_OPRATOR="operator";
    private static String KEY_ANSWER="answer";
    private static String KEY_CORRENT_SCORE="corrent_sore";
    private static String SAVE_FILENAME="save_shpdata_name";
    boolean win_flag=false;
    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if(!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences shp=getApplication().getSharedPreferences(SAVE_FILENAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_LEFT_NUM,0);
            handle.set(KEY_RIGHT_NUM,0);
            handle.set(KEY_OPRATOR,"+");
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CORRENT_SCORE,0);
        }
        this.handle=handle;
    }
    public MutableLiveData<Integer>get_leftnum(){//这些函数都要声明为public，不然在databinding中无法识别（函数不声明范围，默认是default）
        return handle.getLiveData(KEY_LEFT_NUM);
    }
    public MutableLiveData<Integer>get_rightnum(){
        return handle.getLiveData(KEY_RIGHT_NUM);
    }
    public MutableLiveData<String>get_ope(){
        return handle.getLiveData(KEY_OPRATOR);
    }
    public MutableLiveData<Integer>get_highscore(){
        return handle.getLiveData(KEY_HIGH_SCORE);
    }
    public MutableLiveData<Integer>get_currentscore(){
        return handle.getLiveData(KEY_CORRENT_SCORE);
    }
    public MutableLiveData<Integer>get_answer(){
        return handle.getLiveData(KEY_ANSWER);
    }
    void generator(){
        int LEVEL=49;
        Random random=new Random();
        int x,y;
        x=random.nextInt(LEVEL)+1;                         //1到50
        y=random.nextInt(LEVEL)+1;
        if(x%2==0){
            get_ope().setValue("+");
            get_leftnum().setValue(x);
            get_rightnum().setValue(y);
            get_answer().setValue(x+y);
        }else{
            get_ope().setValue("-");
            get_leftnum().setValue(Math.max(x, y));
            get_rightnum().setValue(Math.min(x, y));
            get_answer().setValue(get_leftnum().getValue()-get_rightnum().getValue());
        }
    }

    void saved(){
        SharedPreferences shp=getApplication().getSharedPreferences(SAVE_FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        editor.putInt(KEY_HIGH_SCORE,get_highscore().getValue());
        editor.apply();                                             //保存
    }
    void answerScoreCorrect(){
        get_currentscore().setValue(get_currentscore().getValue()+1);
        if(get_currentscore().getValue()>get_highscore().getValue()){
            get_highscore().setValue(get_currentscore().getValue());
            win_flag=true;
        }
        generator();
    }
}
