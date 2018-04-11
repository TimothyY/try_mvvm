package example.com.try_mvvm;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    String ENDPOINT = "https://api.github.com";

    @GET("/users/{user}/repos")
    Call<List<Repo>> reposForUser(@Path("user") String user);

}