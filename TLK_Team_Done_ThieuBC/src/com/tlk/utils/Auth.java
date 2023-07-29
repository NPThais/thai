
package com.tlk.utils;


import com.tlk.entity.NhanVien;

/**
 *
 * @author MACBOOK Pro
 */
public class Auth {
    public static NhanVien user = null;
    public static void clear(){
        Auth.user = null;
    }
    public static boolean isLogin(){
        return Auth.user != null;
    }
    public static  boolean isManager(){
        return Auth.isLogin() && user.isVaitro();
    }
}
