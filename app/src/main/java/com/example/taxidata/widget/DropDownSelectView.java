package com.example.taxidata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.taxidata.R;
import com.example.taxidata.constant.Algorithm;
import com.example.taxidata.adapter.OnItemClickListener;

import java.util.ArrayList;

public class DropDownSelectView extends LinearLayout {

    private TextView resultTv;
    private ImageView upOrDown;
    private PopupWindow areaPopupWindow = null;
    protected OnItemClickListener onItemClickListener;
    private ArrayList<String> mList = new ArrayList<>();
    private View mView;
    private int kind;

    public DropDownSelectView(Context context) {
        this(context,null);
    }

    public DropDownSelectView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DropDownSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_drop_down_select,this,true);
        resultTv = view.findViewById(R.id.drop_down_select_tv);
        upOrDown = view.findViewById(R.id.drop_down_select_iv);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areaPopupWindow == null){
                    switch (kind) {
                        case 1:
                            showAreaPopWindow();
                            break;
                        case 2:
                            showTimePopWindow();
                            break;
                        case 3:
                            showAreaPopWindow();
                            break;
                        case 4:
                            //显示广告牌界面的天数类型选择框
                            showAdTimePopWindow();
                            break;
                        case 5:
                            //显示广告牌界面的时间段选择框
                            showAdTimePopWindow();
                            break;
                        case 6:
                            //显示广告牌界面的区域选择框
                            showAdAreaPopWindow();
                            break;
                        default:
                    }
                }else {
                    closePopWindow();
                }
            }
        });
    }

    //弹出区域选择列表
    private void showAreaPopWindow(){
/*        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.popupwindow_drop_down_select_area_list,this,false);
        ListView listView = contentView.findViewById(R.id.drop_down_select_area_lv);
        listView.setAdapter(new AreaChooseListAdapter(getContext(), mList));
        areaPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        areaPopupWindow.setOutsideTouchable(true);
        areaPopupWindow.showAsDropDown(this);*/
        loadLayout(R.layout.popupwindow_drop_down_select_area_list,R.id.drop_down_select_area_lv);
    }

    //弹出时间选择列表
    private void showTimePopWindow(){
 /*       LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.popupwindow_drop_down_select_time_list,this,false);
        ListView listView = contentView.findViewById(R.id.drop_down_select_time_lv);
        listView.setAdapter(new AreaChooseListAdapter(getContext(), mList));
        areaPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        areaPopupWindow.setOutsideTouchable(true);
        areaPopupWindow.showAsDropDown(this);*/
        loadLayout(R.layout.popupwindow_drop_down_select_time_list,R.id.drop_down_select_time_lv);
    }

    //弹出广告牌时间选择列表
    private void showAdTimePopWindow() {
        loadLayout(R.layout.popupwindow_drop_dow_ad_time,R.id.drop_down_select_ad_time);
    }

    //弹出广告牌地址选择列表
    private void showAdAreaPopWindow() {
        loadLayout(R.layout.popupwindow_drop_down_ad_area,R.id.drop_down_select_ad_area);
    }

    private void loadLayout(int resoureId,int listViewId) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(resoureId,this,false);
        ListView listView = contentView.findViewById(listViewId);
        listView.setAdapter(new AreaChooseListAdapter(getContext(), mList));
        areaPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        areaPopupWindow.setOutsideTouchable(true);
        areaPopupWindow.showAsDropDown(this);
    }

    private void closePopWindow(){
        areaPopupWindow.dismiss();
        areaPopupWindow = null;
    }

    public void setItemsData(ArrayList<String> list, int id){
        mList = list;
        kind = id;
        switch (kind){
            case 1:
                resultTv.setText("番禺区");
                break;
            case 2:
                resultTv.setText("2007年02月05日");
                break;
            case 3:
                resultTv.setText(Algorithm.WANG_ALGORITHM);
                break;
            case 4:
                resultTv.setText("请选择区域");
                break;
            case 5:
                resultTv.setText("请选择目标时段");
                break;
            case 6:
                resultTv.setText("请选择天数类型");
                break;
            default:
        }
    }

    public class AreaChooseListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<String> areaList;
        LayoutInflater inflater;

        public AreaChooseListAdapter(Context mContext, ArrayList<String> areaList) {
            this.mContext = mContext;
            this.areaList = areaList;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return areaList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemView listItemView = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_drop_down_select_list, null);
                listItemView = new ListItemView();
                listItemView.textView = convertView.findViewById(R.id.drop_down_select_list_item_tv);
                convertView.setTag(listItemView);
            }else {
                listItemView = (ListItemView) convertView.getTag();
            }
            final String text = areaList.get(position).toString();
            listItemView.textView.setText(text);
            listItemView.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultTv.setText(text);
                    closePopWindow();
                    //获得选择的时间和区域
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(position);
                    }
                    Log.d("DropDownSelectView",text);
                }
            });
            return convertView;
        }

    }

    private static class ListItemView{
        TextView textView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 获取下拉选中的区域(如选中番禺区，则返回字符串"番禺区")
     * @return String
     */
    public String getSlectedArea(){
        Log.d("DropDownSelectView",resultTv.getText().toString());
        return resultTv.getText().toString();
    }
}
