LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := uninstall
LOCAL_SRC_FILES := uninstall.c

include $(BUILD_SHARED_LIBRARY)
