LOCAL_PATH := $(call my-dir)

APP_OPTIM := release

#################################
# Library
include $(CLEAR_VARS)

LOCAL_MODULE    := repo_alias
LOCAL_SRC_FILES := repo_alias.cpp
LOCAL_LDLIBS := -llog
LOCAL_CFLAGS := -fstack-protector-all

include $(BUILD_SHARED_LIBRARY)
