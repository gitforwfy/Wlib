package com.photo.album.activity.model;

import com.photo.album.activity.view.ILoginView;

/**
 *    登陆的Presenter
 */
public class LoginPresenter {
    LoginModelImpl loginModel;
    ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        super();
        loginModel = new LoginModelImpl();
        this.iLoginView = iLoginView;
    }

    public ILoginView getiLoginView() {
        return iLoginView;
    }

    public void setiLoginView(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    /**
     * 调用model进行数据处理，根据回调接口来操作ILoginView进行对应的activity界面更新
     * @param username
     * @param pwd
     */
    public void login(String username, String pwd) {
        System.out.println(username+","+pwd);
        loginModel.login(username, pwd, new ILoginModel.ILoginCallBack() {

            public void onStatus() {
                System.out.println(loginModel.status+"");
                switch (loginModel.status) {
                    case LoginModelImpl.STATUS_SUCCESS:
                        iLoginView.jumpActivity();
                        iLoginView.showMsg(loginModel.msg);
                        break;
                    case LoginModelImpl.STATUS_FAIL:
                        iLoginView.showMsg(loginModel.msg);
                        break;
                    default:
                        break;
                }
                loginModel.status=ILoginModel.STATUS_NORMAL;
            }
        });
    }
}
