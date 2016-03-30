package com.example.test.aidl;
import com.example.test.aidl.ITaskCallBack;
import com.example.test.aidl.Person;
interface ITaskBinder {
   void fuc01();
   void fuc02();
   String fuc03(in Person person);
   void registerCallBack(ITaskCallBack cb);
   void unregisterCallBack();
}