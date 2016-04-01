package com.example.test.aidl;
import com.example.test.aidl.ITaskCallBack;
import com.example.test.aidl.Person;
interface ITaskBinder {
   void commonCall();
   void callback();
   String objectCall(in Person person);
   void registerCallBack(ITaskCallBack cb);
   void unregisterCallBack();
}