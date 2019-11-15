package site.aanrstudio.apps.katalogfilm.widget.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import site.aanrstudio.apps.katalogfilm.widget.adapter.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
