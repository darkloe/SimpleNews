package com.allenanker.android.simplenews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {


    private EditText register_user;

    private EditText register_password;

    private Button register_ok;

    private Person p2;

    private Person p3;

    LoadingDialog ld;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        ld = new LoadingDialog(this);

        addControl();//加载控件

        addRegisterShow();//注册方法

    }

    private void addRegisterShow() {

        register_ok.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                final String rUser=register_user.getText().toString().trim();

                final String rPassword=register_password.getText().toString().trim();

                //判断用户名和密码是否为空,如果为空则不能进去。

                if(rUser.length()>0&&rPassword.length()>0) {

                    BmobQuery<Person> query = new BmobQuery<>();

                    query.findObjects(RegisterActivity.this, new FindListener<Person>() {

                        boolean isAccountLegal = true;

                        @Override
                        public void onSuccess(List<Person> list) {

                            for (int i = 0; i < list.size(); i++) {

                                String name = list.get(i).getName();

                                if (name.equals(rUser)) {

                                    isAccountLegal = false;

                                    break;

                                }

                            }

                            if (!isAccountLegal){

                                Toast.makeText(RegisterActivity.this, "该账号已被注册，请更换账号", Toast.LENGTH_LONG).show();

                                register_user.setText("");

                                register_password.setText("");

                            }else {

                                p2 = new Person();

                                p2.setName(rUser);

                                p2.setPassword(rPassword);

                                //插入方法

                                p2.save(RegisterActivity.this, new SaveListener() {

                                    @Override

                                    public void onSuccess() {

                                        // TODO Auto-generated method stub

                                        ld.setLoadingText("注册中")

                                                .setSuccessText("注册成功")

                                                .setInterceptBack(true)

                                                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)

                                                .show();

                                        ld.loadSuccess();

                                        register_password.setText("");

                                        register_user.setText("");

                                    }

                                    @Override

                                    public void onFailure(int code, String msg) {

                                        // TODO Auto-generated method stub

                                        Toast.makeText(RegisterActivity.this, "创建数据失败：" + msg, Toast.LENGTH_SHORT).show();

                                        ld.setLoadingText("注册中")

                                                .setSuccessText("注册失败")

                                                .setInterceptBack(true)

                                                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)

                                                .show();

                                        ld.loadFailed();

                                    }

                                });

                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }

                    });

                }else{

                    Toast.makeText(RegisterActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    private void addControl() {

        register_user = findViewById(R.id.account);

        register_password = findViewById(R.id.password);

        register_ok = findViewById(R.id.register);

    }
}
