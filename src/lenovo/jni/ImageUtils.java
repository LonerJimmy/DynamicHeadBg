package lenovo.jni;

import android.graphics.Bitmap;

public final class ImageUtils
{
  static
  {
	  System.loadLibrary("ImgFun");
  }

  public static Bitmap fastBlur(Bitmap src, int degree)
  {
	  Bitmap dest = null;
	  int w = src.getWidth();
	  int h = src.getHeight();
	  
	  dest = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	  fastBlur(src, dest, w, h, degree);
	  
	  return dest;
  }

  private static native void fastBlur(Bitmap src, Bitmap store, int width, int height, int degree);
}