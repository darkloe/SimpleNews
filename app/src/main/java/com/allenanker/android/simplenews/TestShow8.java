package com.allenanker.android.simplenews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class TestShow8 extends AppCompatActivity {

    private EditText lgUser;

    private EditText lgPassword;

    private Button btn_ok;

    private Button btn_rg;

    private CheckBox is_checked;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "c4e8b5419b13b0dfb48dace72cb4739b");

        setContentView(R.layout.login);

        sharedPreferences = getSharedPreferences("RememberPwd",MODE_PRIVATE);

        addControl();

        boolean isRemembered = sharedPreferences.getBoolean("RememberPwd",false);

        if (isRemembered){

            String account = sharedPreferences.getString("account","");

            String password = sharedPreferences.getString("password","");

            lgUser.setText(account);

            lgPassword.setText(password);

            is_checked.setChecked(true);
        }

        addLogin();

    }

    private void addLogin() {

        btn_rg.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent intent=new Intent(TestShow8.this,RegisterActivity.class);

                startActivity(intent);

            }

        });

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                BmobQuery<Person> query= new BmobQuery<>();

                query.findObjects(TestShow8.this,new FindListener<Person>(){

                    String lgU=lgUser.getText().toString();

                    String lgp=lgPassword.getText().toString();

                    int panduan=1;

                    @Override

                    public void onSuccess(List<Person> list) {

                        for(int i=0;i<list.size();i++){

                            String name=list.get(i).getName().trim();

                            String password=list.get(i).getPassword().trim();

                            Log.e("user","唯一 id:"+list.get(i).getObjectId()+"----"+name+"---"+password);

                            if(name.equals(lgU) && password.equals(lgp)){

                                editor = sharedPreferences.edit();

                                if (is_checked.isChecked()){

                                    editor.putBoolean("RememberPwd",true);

                                    editor.putString("account",name);

                                    editor.putString("password",password);

                                }else {

                                    editor.clear();
                                }

                                editor.apply();

                                Toast.makeText(TestShow8.this, "登录成功", Toast.LENGTH_SHORT).show();

                                panduan=2;

                                //成功后panduan等于2,则跳出该循环,并且把输入快都清空,跳转到指定页面

                                lgUser.setText("");

                                lgPassword.setText("");

                                Intent intent=new Intent(TestShow8.this,NewsListActivity.class);

                                startActivity(intent);

                                break;

                            }

                        }

                        if(panduan==1){

                            Toast.makeText(TestShow8.this, "登录失败", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override

                    public void onError(int i, String s) {

                    }

                });

            }

        });

    }

    private void addControl() {

        lgUser = findViewById(R.id.ed1);

        lgPassword = findViewById(R.id.ed2);

        btn_ok = findViewById(R.id.button);

        btn_rg = findViewById(R.id.button2);

        is_checked = findViewById(R.id.remember_Password);

    }
}