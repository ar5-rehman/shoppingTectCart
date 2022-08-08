package com.clothing.shoppingcarts.base.interceptors;

import android.content.Context;
import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.clothing.shoppingcarts.base.exception.NoInternetException;
import com.clothing.shoppingcarts.base.utils.NetworkUtil;

/**
 * Connectivity Interceptor for okhttp client to return a network status message
 */
public class ConnectivityInterceptor implements Interceptor {

    private final Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!NetworkUtil.getInstance().isInternetAvailable(mContext)) {
            throw new NoInternetException("No internet connection. Please check your connection and try again.");
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
