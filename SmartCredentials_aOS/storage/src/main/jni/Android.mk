LOCAL_PATH := $(call my-dir)

APP_OPTIM := release

#################################
# Library
include $(CLEAR_VARS)

LOCAL_MODULE    := repo_alias
LOCAL_SRC_FILES := repo_alias.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
