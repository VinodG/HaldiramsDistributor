package com.winit.common.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BitmapUtilsLatLang {

	public static String label = "";

	public static Bitmap decodeSampledBitmapFromResourceNew(File f, int reqWidth,int reqHeight) {
		try {
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(new FileInputStream(f), null, options);

			// Calculate inSampleSize
			options.inSampleSize = 1;

			// Decode bitmap with inSampleSize set

			options.inJustDecodeBounds = false;
			Bitmap tmpBitmap = BitmapFactory.decodeStream(
					new FileInputStream(f), null, options);


			tmpBitmap = getResizedBitmap(tmpBitmap, reqWidth, reqHeight);

			float rotation = rotationForImage(null, Uri.fromFile(f));
			if (rotation != 0f) {
				Matrix matrix = new Matrix();
				matrix.preRotate(rotation);
				tmpBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0,tmpBitmap.getWidth(), tmpBitmap.getHeight(), matrix,true);
			}

			return tmpBitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getResizedBitmap(Bitmap bitmap, float width,float height) {
		if (bitmap != null) {

		}
		float bmpHieght = bitmap.getHeight();
		float bmpWidth = bitmap.getWidth();

		Bitmap scaledBitmap = null;
		if (bmpHieght < height && bmpWidth < width) {
			return bitmap;
		}

		int scaledWidth = 0;
		int scaledHeight = 0;

		if (bmpWidth / width < bmpHieght / height) {
			scaledWidth = convertPixelToDp((int) (bmpWidth * height / bmpHieght));
			scaledHeight = convertPixelToDp((int) height);
			scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
					scaledHeight, true);
		} else {
			scaledWidth = convertPixelToDp((int) width);
			scaledHeight = convertPixelToDp((int) (bmpHieght * width / bmpWidth));
			scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
					scaledHeight, true);
		}
		return scaledBitmap;
	}

	public static float rotationForImage(Context context, Uri uri) {
		try {
			if (context != null && uri.getScheme().equals("content")) {
				String[] projection = { Images.ImageColumns.ORIENTATION };
				Cursor c = context.getContentResolver().query(uri, projection,
						null, null, null);
				if (c.moveToFirst()) {
					return c.getInt(0);
				}
			} else if (uri.getScheme().equals("file")) {
				try {
					ExifInterface exif = new ExifInterface(uri.getPath());
					int rotation = (int) exifOrientationToDegrees(exif
							.getAttributeInt(ExifInterface.TAG_ORIENTATION,
									ExifInterface.ORIENTATION_NORMAL));
					return rotation;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0f;
	}

	public static int convertPixelToDp(int px) {
		return (int) (px * (160 / 160f));
	}

	private static float exifOrientationToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			return 90;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			return 180;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			return 270;
		}
		return 0;
	}

	public static String saveVerifySignature(Bitmap bitmap,String filepath)
	{
		File file = new File (filepath);
		if (file.exists ()) file.delete ();
		try
		{

			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return filepath;
	}
	public static void CopyStream(InputStream is, OutputStream os)
	{
		final int buffer_size=1024;
		try
		{
			byte[] bytes=new byte[buffer_size];
			for(;;)
			{
				if(Thread.currentThread().isInterrupted()) return;
				int count=is.read(bytes, 0, buffer_size);
				if(count==-1)
					break;
				os.write(bytes, 0, count);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static Bitmap decodeSampledBitmapFromResource(File f, int reqWidth,
														 int reqHeight) {
		try {
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(new FileInputStream(f), null, options);

			// Calculate inSampleSize
			options.inSampleSize = 1;

			// Decode bitmap with inSampleSize set

			options.inJustDecodeBounds = false;
			Bitmap tmpBitmap = BitmapFactory.decodeStream(
					new FileInputStream(f), null, options);
			tmpBitmap = getResizedBitmap(tmpBitmap, reqWidth, reqHeight);
			float rotation = rotationForImage(null, Uri.fromFile(f));
			if (rotation != 0f) {
				Matrix matrix = new Matrix();
				matrix.preRotate(rotation);
				tmpBitmap = Bitmap.createBitmap(tmpBitmap, 0, 0,
						tmpBitmap.getWidth(), tmpBitmap.getHeight(), matrix,
						true);
			}
			return tmpBitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
