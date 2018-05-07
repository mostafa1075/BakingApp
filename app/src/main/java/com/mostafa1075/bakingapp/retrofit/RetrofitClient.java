package com.mostafa1075.bakingapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * References: Retrofit documentation http://square.github.io/retrofit/
 * Singleton Pattern https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm
 * Adapted from this answer: https://stackoverflow.com/questions/36960627/android-retrofit-design-patterns
 */
public class RetrofitClient {

    private static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static RetrofitService mRetrofitService;

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitService getInstance(){
        if(mRetrofitService == null)
            new RetrofitClient();
        return mRetrofitService;
    }
}
