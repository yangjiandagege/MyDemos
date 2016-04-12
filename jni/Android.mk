LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
NDK_MODULE_PATH:=/home/yj/Tools/adt-bundle-linux-x86_64-20131030/android-ndk-r9d/prebuilt
LOCAL_MODULE    := TestNDK
LOCAL_SRC_FILES := TestNDK.cpp
LOCAL_LDLIBS += -llog
include $(BUILD_SHARED_LIBRARY)
