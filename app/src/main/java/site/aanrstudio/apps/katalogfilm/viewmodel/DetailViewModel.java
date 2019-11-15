package site.aanrstudio.apps.katalogfilm.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import site.aanrstudio.apps.katalogfilm.BuildConfig;
import site.aanrstudio.apps.katalogfilm.Preferences;
import site.aanrstudio.apps.katalogfilm.model.Detail;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<Detail> liveData = new MutableLiveData<>();
    private Preferences preferences = new Preferences();

    public MutableLiveData<Detail> getLiveData() {
        return liveData;
    }

    public void setLiveData(final int id, final String lang, final String cat, final Context context) {
        AndroidNetworking.get(BuildConfig.TMDB_URL +cat+"/"+id+"?language="+lang)
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("overview").isEmpty()) {
                                setLiveData(id, "en", cat, context);
                            } else {
                                boolean is_fav = preferences.getFavorite(context, id);
                                Detail detail = new Detail(response, cat, is_fav);
                                liveData.postValue(detail);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }

                });
    }
}
