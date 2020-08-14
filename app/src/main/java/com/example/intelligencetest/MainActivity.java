package com.example.intelligencetest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller=Navigation.findNavController(this,R.id.fragment2);          //创建头部信息
        NavigationUI.setupActionBarWithNavController(this,controller);
    }

    @Override
    public boolean onSupportNavigateUp() {                                            //添加退出的对画框和功能
        if(controller.getCurrentDestination().getId()==R.id.questionFragment){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Are you sure to quit");
            builder.setPositiveButton("Y", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    controller.navigateUp();
                }
            });
            builder.setNegativeButton("N", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog =builder.create();                                 //创建对话框
            dialog.show();
        }else if(controller.getCurrentDestination().getId()==R.id.titleFragment){
            finish();                                                               //结束应用
        }else{
            controller.navigate(R.id.titleFragment);                                //其他导航到标题
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();                                      //返回建调用上面的后撤建
        //super.onBackPressed();
    }
}