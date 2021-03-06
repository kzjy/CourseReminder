package kz.coursereminder.structure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapConverter {

    /**
     * Calculate InSampleSize of options
     * @param options option to calculate for
     * @param reqWidth required width of bitmap
     * @param reqHeight required height of bitmap
     * @return insample size
     */
    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Create bitmapFactoryOption
     * @param imgDecodableString imageDecodableString to pass in
     * @param picker whether it is a background or icon
     * @return bitmapfactory.options
     */
    @NonNull
    private BitmapFactory.Options createBitmapFactoryOptions(String imgDecodableString, String picker) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgDecodableString, options);
        options.inSampleSize = calculateInSampleSize(options, 270, 480);
        if (picker.equals("Icon")) {
            options.inSampleSize = calculateInSampleSize(options, 120, 120);
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * Convert decodable string to bitmap
     * @param imgDecodableString imagedecodablestring from gallery
     * @param size size of bitmap to convert
     * @return bitmap of image
     */
    public Bitmap convertDecodableStringToBitmap(String imgDecodableString, String size) {
        BitmapFactory.Options options = createBitmapFactoryOptions(imgDecodableString, size);
        return BitmapFactory.decodeFile(imgDecodableString, options);
    }

    /**
     * Decode base64 string to bitmap
     * @param input base64 string
     * @return bitmap
     */
    public Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /**
     * Encode bitmap to base64 string
     * @param bitmap bitmap to encode
     * @return base64 string
     */
    public String encodeBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
