LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

USE_BLUR_ALGORITHM = USE_STACK_BLUR
#USE_STACK_BLUR

LOCAL_MODULE    := ImgFun
LOCAL_SRC_FILES :=  \

ifeq ($(USE_BLUR_ALGORITHM), USE_STACK_BLUR)
LOCAL_SRC_FILES += interface.cpp
LOCAL_SRC_FILES += StackBlur.cpp
endif

LOCAL_LDLIBS    := -llog -lGLESv2 -lGLESv1_CM -ljnigraphics -lEGL

include $(BUILD_SHARED_LIBRARY)