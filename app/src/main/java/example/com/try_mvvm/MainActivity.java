package example.com.try_mvvm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    Context mCtx;

    RecyclerView rvRepo;
    RVRepoAdapter rvRepoAdapter;
    List<Repo> repos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCtx = this;

        //Initialise RecyclerView
        initViews();

        //Fetch results from GitHub API
        fetchGitHub();

    }

    private void fetchGitHub() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GithubService.ENDPOINT).addConverterFactory(GsonConverterFactory.create(gson)).build();

        GithubService githubServiceClient = retrofit.create(GithubService.class);

        Call<List<Repo>> call = githubServiceClient.reposForUser("square");

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
                    repos = response.body();
                    for (Repo repo:repos) {
                        Toast.makeText(mCtx, repo.name, Toast.LENGTH_SHORT).show();
                    }
                    rvRepoAdapter = new RVRepoAdapter(repos);
                    rvRepo.setAdapter(rvRepoAdapter);
                }else{
                    //http errors goes here
                    Log.d(TAG,response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        rvRepo = findViewById(R.id.rvRepos);
        rvRepo.setHasFixedSize(true);
        rvRepo.setLayoutManager(new LinearLayoutManager(mCtx));
    }
}
