package de.telekom.scdocumentscannerdemo.result;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.microblink.hardware.orientation.Orientation;
import com.microblink.image.Image;

import java.io.ByteArrayOutputStream;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class ImageUtils {

    public static Bitmap transformImageToBitmap(Image image) {
        Bitmap bitmapImage = image.convertToBitmap();
        if (bitmapImage != null && image.getImageOrientation() != Orientation.ORIENTATION_UNKNOWN) {

            boolean needTransform = false;

            Matrix matrix = new Matrix();
            int newWidth = bitmapImage.getWidth();
            int newHeight = bitmapImage.getHeight();

            if (image.getImageOrientation() != Orientation.ORIENTATION_LANDSCAPE_RIGHT) {
                needTransform = true;
                float pX = newWidth / 2.f;
                float pY = newWidth / 2.f;

                if (image.getImageOrientation() == Orientation.ORIENTATION_LANDSCAPE_LEFT) {
                    matrix.postRotate(180.f, pX, pY);
                } else {
                    if (image.getImageOrientation() == Orientation.ORIENTATION_PORTRAIT) {
                        matrix.postRotate(90.f, pX, pY);
                    } else if (image.getImageOrientation() == Orientation.ORIENTATION_PORTRAIT_UPSIDE) {
                        matrix.postRotate(270.f, pX, pY);
                    }
                }
            }

            int maxDimension = Math.max(newWidth, newHeight);
            final int maxAllowedDimension = 1920;
            if (maxDimension > maxAllowedDimension) {
                needTransform = true;
                float scale = (float) maxAllowedDimension / maxDimension;
                matrix.postScale(scale, scale);
            }
            if (needTransform) {
                bitmapImage = Bitmap.createBitmap(bitmapImage, 0, 0, bitmapImage.getWidth(), bitmapImage.getHeight(), matrix, false);
            }
        }

        return bitmapImage;
    }

    public static byte[] transformBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap transformByteArrayToBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static byte[] transformImageToByteArray(Image image) {
        return transformBitmapToByteArray(transformImageToBitmap(image));
    }
}
