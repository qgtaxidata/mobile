package com.example.taxidata.base;

public interface BasePresent<T extends BaseView>  {

   void attachView(T view);

   void detachView();
}
