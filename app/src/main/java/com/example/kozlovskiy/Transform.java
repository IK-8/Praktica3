package com.example.kozlovskiy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;

import static com.example.kozlovskiy.UserStaticInfo.LOGIN;
import static com.example.kozlovskiy.UserStaticInfo.PASSWORD;

public class Transform {

    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "mysettings";
    /**
     * Метод проверяет пустая строка или нет
     * @param string Проверяемая строка
     * @return нет/да
     */
    public static Boolean StringNoNull(String string)
    {
        if(string==null)
            return  false;
        else return string.length() != 0;
    }

    /**
     * Метод вызывает вибрацию на время mills
     * @param context передаваемый контекст для доступа к SystemService
     */
    public static void Vibrate(Context context)
    {
        //секунда
        long mills = 1000L;
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //проверка возможности вибрации
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mills);
        }
    }

    /**
     * Метод сохраняет пользователя в файле настроек
     * @param sp -объект SharedPreferences
     * @param login - логин
     * @param password - пароль
     */
    public static void SaveUser(SharedPreferences sp,String login,String password)
    {
        SharedPreferences.Editor e = sp.edit();
        e.putString(LOGIN, login );
        e.putString(PASSWORD, password );
        e.apply();// не забудьте подтвердить изменения
    }


}
