LOCAL_PATH := $(call my-dir)

APP_OPTIM := release

#################################
# Library
include $(CLEAR_VARS)

LOCAL_MODULE    := alias
LOCAL_SRC_FILES := alias.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
