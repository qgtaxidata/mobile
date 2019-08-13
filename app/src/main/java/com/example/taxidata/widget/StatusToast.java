package com.example.taxidata.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.taxidata.R;

public class StatusToast {
    public static StatusToast myToast;
    private Toast toast;

    private StatusToast(){

    }

    public static StatusToast getMyToast(){
        if(myToast == null){
            myToast = new StatusToast();
        }
        return myToast;
    }

    public void ToastShow(Context context, ViewGroup root, int id, String str){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_status,root);
        TextView textView = view.findViewById(R.id.tv_toast);
        ImageView imageView = view.findViewById(R.id.imv_toast);
        textView.setText(str);
        imageView.setImageResource(id);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public void ToastCancel(){
        if (toast != null){
            toast.cancel();
        }
    }
}
