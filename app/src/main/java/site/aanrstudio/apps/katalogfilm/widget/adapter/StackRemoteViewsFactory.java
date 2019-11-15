package site.aanrstudio.apps.katalogfilm.widget.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import site.aanrstudio.apps.katalogfilm.R;
import site.aanrstudio.apps.katalogfilm.repository.FavoriteRepository;
import site.aanrstudio.apps.katalogfilm.widget.FavoriteBannerWidget;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<String> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }
    private FavoriteRepository favoriteRepository = new FavoriteRepository();

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long indentityToken = Binder.clearCallingIdentity();
        mWidgetItems = favoriteRepository.getPhotos(mContext.getApplicationContext());
        Binder.restoreCallingIdentity(indentityToken);
        Log.d("Kampret", "Asu");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bmp = Glide.with(mContext.getApplicationContext())
                    .asBitmap()
                    .load(mWidgetItems.get(position))
                    .submit().get();
            rv.setImageViewBitmap(R.id.widget_img, bmp);
            Bundle extras = new Bundle();
            extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.widget_img, fillInIntent);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
