package kr.co.schoolholic.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import kr.co.schoolholic.core.AppContext;

/**
 * Created by kevin on 2015. 10. 21..
 */
public class ImageLoader {

    private LruCache memoryCache;
    private FileCache fileCache;
    private Map imageViews = Collections.synchronizedMap(new WeakHashMap());

    private Drawable mStubDrawable;

    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        init(context);
    }

    private void init(Context context) {
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int memClass = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
        // 1/8 of the available mem
        final int cacheSize = 1024 * 1024 * memClass / 8;
        memoryCache = new LruCache(cacheSize);

        mStubDrawable = context.getResources().getDrawable(android.R.drawable.gallery_thumb);
    }

    public void displayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = null;
        if (url != null && url.length() > 0)
            bitmap = (Bitmap) memoryCache.get(url);
        if (bitmap != null) {
            //the image is in the LRU Cache, we can use it directly
            imageView.setImageBitmap(bitmap);
        } else {
            //the image is not in the LRU Cache
            //set a default drawable a search the image
            imageView.setImageDrawable(mStubDrawable);
            if (url != null && url.length() > 0)
                queuePhoto(url, imageView);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        new LoadBitmapTask().execute(url, imageView);
    }

    /**
     * Search for the image in the device, then in the web
     * @param url
     * @return
     */
    private Bitmap getBitmap(String url) {
        Bitmap ret = null;
        //from SD cache
        File f = fileCache.getFile(url);
        if (f.exists()) {
            ret = BitmapUtil.createBitmapSample(f.getPath(), 4);
        }
        else {
            ret = BitmapUtil.createBitmapSample(url, 4);
        }

        if (ret != null)
            return ret;

        Trace.w("return null.");
        //from web
        /*
        try {
            //your requester will fetch the bitmap from the web and store it in the phone using the fileCache
            ret = MyRequester.getBitmapFromWebAndStoreItInThePhone(url);    // your own requester here
            return ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        */

        return null;
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        Bitmap ret = null;
        try {
            FileInputStream is = new FileInputStream(f);
            ret = BitmapFactory.decodeStream(is, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    private boolean imageViewReused(PhotoToLoad photoToLoad) {
        //tag used here
        String tag = (String)imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    class LoadBitmapTask extends AsyncTask {
        private PhotoToLoad mPhoto;

        @Override
        protected TransitionDrawable doInBackground(Object... params) {
            mPhoto = new PhotoToLoad((String) params[0], (ImageView) params[1]);

            if (imageViewReused(mPhoto))
                return null;
            Bitmap bmp = getBitmap(mPhoto.url);

            if (bmp == null)
                return null;

            memoryCache.put(mPhoto.url, bmp);

            // TransitionDrawable let you to make a crossfade animation between 2 drawables
            // It increase the sensation of smoothness
            TransitionDrawable td = null;
            if (bmp != null) {
                Drawable[] drawables = new Drawable[2];
                drawables[0] = mStubDrawable;
                drawables[1] = new BitmapDrawable(AppContext.instance.getApplicationContext().getResources(), bmp);
                td = new TransitionDrawable(drawables);
                td.setCrossFadeEnabled(true); //important if you have transparent bitmaps
            }

            return td;
        }

        @Override
        protected void onPostExecute(Object td) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) td;

            if (imageViewReused(mPhoto)) {
                //imageview reused, just return
                return;
            }

            if (td != null) {
                // bitmap found, display it !
                mPhoto.imageView.setImageDrawable(transitionDrawable.getDrawable(1));
                mPhoto.imageView.setVisibility(View.VISIBLE);

                //a little crossfade
                transitionDrawable.startTransition(200);
            } else {
                //bitmap not found, display the default drawable
                mPhoto.imageView.setImageDrawable(mStubDrawable);
            }
        }
    }
}