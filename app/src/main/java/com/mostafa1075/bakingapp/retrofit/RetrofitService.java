package com.mostafa1075.bakingapp.retrofit;

import com.mostafa1075.bakingapp.pojo.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    String FILE_PATH = "baking.json";

    @GET(FILE_PATH)
    Call<List<Recipe>> getRecipes();
}