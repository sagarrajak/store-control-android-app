package com.example.sagar.myapplication.api;

import android.app.Dialog;

import com.example.sagar.myapplication.utill.Err;
import com.example.sagar.myapplication.utill.Token;
import com.example.sagar.myapplication.api.interfaces.ApiWorkProfileInterface;
import com.example.sagar.myapplication.modal.Data;
import com.example.sagar.myapplication.modal.WorkProfile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkProfileApi {

    private List<WorkProfile> mList;
    private static WorkProfileApi mWorkProfileApi;
    private ApiWorkProfileInterface mApiWorkProfileInterface;

    public WorkProfileApi() {

        mList = new ArrayList<>();
        mApiWorkProfileInterface = ApiClient.getClient().create(ApiWorkProfileInterface.class);


    }

    public void addWorkProfile(String work_profile, Integer hr_of_work, Integer salary, String right, final Dialog dialog) {

        mApiWorkProfileInterface
                .addWorkProfile(work_profile, hr_of_work, salary, right, Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.code() == 200)
                            listWorkProfile(dialog);
                        else {
                            dialog.dismiss();
                            Err.e("failed in  creating   work profile");
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        Err.e("failed in creating work profile stackTrace");
                    }
                });


    }

    public void deleteWorkProfile(String id, final Dialog dialog) {

        mApiWorkProfileInterface
                .deleteWorkProfile(id, Token.token)
                .enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {

                        if (response.code() == 200)
                            listWorkProfile(dialog);
                        else
                            Err.e("Deleting workprofile failed");
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        t.printStackTrace();
                        Err.e("Deleting work profile failed");
                    }
                });


    }

    public void modifyWorkProfile() {

        //todo api for modifying work profile api
    }


    public void listWorkProfile(final Dialog dialog) {


        mApiWorkProfileInterface.getWorkProfileList(
                Token.token
        ).enqueue(new Callback<List<WorkProfile>>() {
            @Override
            public void onResponse(Call<List<WorkProfile>> call, Response<List<WorkProfile>> response) {

                if (response.code() == 200) mList = response.body();
                else Err.e("Error in updating list of of workProfile");

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<WorkProfile>> call, Throwable t) {
                t.printStackTrace();
                Err.e("Error in listing work profile");
            }
        });


    }

    public static WorkProfileApi getmWorkProfileApi() {
        if (mWorkProfileApi == null)
            mWorkProfileApi = new WorkProfileApi();

        return mWorkProfileApi;
    }

}
