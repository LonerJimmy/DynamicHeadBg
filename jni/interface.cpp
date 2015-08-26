#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/bitmap.h>

#include <string.h>
#include <math.h>
//#include "BlurFilter_port.h"

extern void stackBlurCanvasRGB(void* src, int width, int height, int radius);

extern "C"
{
/*
 * Class:     lenovo_jni_ImageUtils
 * Method:    fastBlur
 * Signature: (Landroid/graphics/Bitmap;Landroid/graphics/Bitmap;III)V
 */
JNIEXPORT void JNICALL Java_lenovo_jni_ImageUtils_fastBlur(JNIEnv* env,
		jobject thiz, jobject bitmapcolor, jobject bitmapgray, jint width, jint height, jint degree) {

	AndroidBitmapInfo infocolor;
	void* pixelscolor;
	AndroidBitmapInfo infogray;
	void* pixelsgray;

	AndroidBitmap_getInfo(env, bitmapcolor, &infocolor);
	AndroidBitmap_getInfo(env, bitmapgray, &infogray);
	AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor);
	AndroidBitmap_lockPixels(env, bitmapgray, &pixelsgray);
	memcpy( (void*) pixelsgray, (void*) pixelscolor, sizeof(int)*width*height);
	//memset( (void*) pixelsgray, 0x000000, sizeof(int)*width*height);
	stackBlurCanvasRGB((void*) pixelsgray, width, height, degree);

	AndroidBitmap_unlockPixels(env, bitmapcolor);
	AndroidBitmap_unlockPixels(env, bitmapgray);
}

}
