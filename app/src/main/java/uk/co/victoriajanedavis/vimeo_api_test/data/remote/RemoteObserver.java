package uk.co.victoriajanedavis.vimeo_api_test.data.remote;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import retrofit2.Response;
import uk.co.victoriajanedavis.vimeo_api_test.util.VimeoApiServiceUtil;

public abstract class RemoteObserver<T> implements Observer<Response<T>> {

    @Override
    public final void onNext(@NonNull Response<T> response) {
        switch (VimeoApiServiceUtil.responseType(response)) {
            case VimeoApiServiceUtil.RESPONSE_OK:
                if (response.body() != null) {
                    onSuccess(response.body());
                }
                break;
            case VimeoApiServiceUtil.RESPONSE_UNAUTHORIZED:
                onUnauthorized(response);
                break;

            default:
                onError(new Throwable("Default " + response.code() + " " + response.message()));
        }
    }

    public abstract void onSuccess(T response);

    public abstract void onUnauthorized(@NonNull Response<T> response);

    public abstract void onError(Throwable throwable);

    @Override
    public void onComplete() {
    }
}

