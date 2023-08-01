package com.deepcode.graduantestapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deepcode.graduantestapp.ApiClient;
import com.deepcode.graduantestapp.R;
import com.deepcode.graduantestapp.adapter.PostsAdapter;
import com.deepcode.graduantestapp.interfaces.ApiService;
import com.deepcode.graduantestapp.model.Post;
import com.deepcode.graduantestapp.util.TokenManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostCreateFragment extends Fragment {

    private RecyclerView rvPosts;
    private PostsAdapter adapter;
    private ApiService apiService;
    private EditText etPost;
    private Button btnCreatePost, btnViewPost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_create, container, false);

        apiService = ApiClient.getClient(getContext()).create(ApiService.class);

        rvPosts = view.findViewById(R.id.rvPosts);
        etPost = view.findViewById(R.id.etPost);
        btnCreatePost = view.findViewById(R.id.btnCreatePost);
        btnViewPost = view.findViewById(R.id.btnViewPost);

        btnCreatePost.setOnClickListener(v -> {
            if (!etPost.getText().toString().isEmpty()) {
                createPost(etPost.getText().toString());
            }

        });
        btnViewPost.setOnClickListener(v -> getAllPosts());

        return view;
    }


    private void createPost(String strPost) {
        Post post = new Post(strPost);
        Call<ResponseBody> call = apiService.createPost("Bearer " + TokenManager.getInstance(getContext()), post);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Successfully Create a Post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getAllPosts() {
        Call<List<Post>> call = apiService.getAllPosts("Bearer " + TokenManager.getInstance(getContext()));
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.body() != null) {
                    List<Post> postList = response.body();
                    rvPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    adapter = new PostsAdapter(postList);
                    rvPosts.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}