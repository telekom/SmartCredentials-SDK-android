LOCAL_PATH := $(call my-dir)

APP_OPTIM := release

#################################
# Library
include $(CLEAR_VARS)

LOCAL_MODULE    := alias
LOCAL_SRC_FILES := alias.cpp
LOCAL_LDLIBS := -llog

APP_CFLAGS := -fstack-protector
# Set APP_PIE to true
APP_PIE := $(strip $(APP_PIE))
ifndef APP_PIE
    APP_PLATFORM := android-21
    APP_PIE := true
endif

include $(BUILD_SHARED_LIBRARY)
