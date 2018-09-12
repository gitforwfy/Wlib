package com.photo.album.activity.model;

import android.os.AsyncTask;

import com.photo.album.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 登陆模型的具体实现
 */
public class LoginModelImpl implements ILoginModel {
    public int status = ILoginModel.STATUS_NORMAL;    //记录登陆状态
    public String msg;    //登陆操作后的提示语
    @Override
    public void login(final String username, final String pwd,
                      final ILoginCallBack loginCallBack) {
        if (status == ILoginModel.STATUS_VERIFY_ING) {
            return;
        }
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.getObject(username, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    //toast("查询成功");
                    if (pwd.equals(user.getPassword())) {
                        status = ILoginModel.STATUS_SUCCESS;
                        msg = "登陸成功";
                        loginCallBack.onStatus();
                    }
                }else{
                    //toast("查询失败：" + e.getMessage());
                    msg = "用户名或密码错误";
                    status = ILoginModel.STATUS_FAIL;
                }
            }
        });
    }
}
