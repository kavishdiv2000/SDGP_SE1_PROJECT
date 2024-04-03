package com.legocats.twinklebun.ui.leadership;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.legocats.twinklebun.LeaderboardAdapter;
import com.legocats.twinklebun.LeaderboardItem;
import com.legocats.twinklebun.Question;
import com.legocats.twinklebun.R;
import com.legocats.twinklebun.TokenManager;
import com.legocats.twinklebun.URIManager;
import com.legocats.twinklebun.databinding.FragmentLeadershipBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LeadershipFragment extends Fragment {
    private RecyclerView rvLeaderboard;
    private LeaderboardAdapter leaderboardAdapter;

    private FragmentLeadershipBinding binding;
    private ProgressDialog progressDialog;
    private TokenManager tokenManager;
    private List<LeaderboardItem> leaderboardItems;
    private int currentUserIndex;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LeadershipViewModel notificationsViewModel =
                new ViewModelProvider(this).get(LeadershipViewModel.class);

//        binding = FragmentLeadershipBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();


        View view = inflater.inflate(R.layout.fragment_leadership, container, false);

        rvLeaderboard = view.findViewById(R.id.rv_leaderboard);
        rvLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading leaderboard...");
        progressDialog.setCancelable(false);

        tokenManager = TokenManager.getInstance(getContext());

        // Sample data
//        List<LeaderboardItem> leaderboardItems = new ArrayList<>();
        fetchLeaderboardData();



//        leaderboardAdapter = new LeaderboardAdapter(leaderboardItems, currentUserIndex);
//        rvLeaderboard.setAdapter(leaderboardAdapter);

        return view;


    }



    private static class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

        private final List<LeaderboardItem> leaderboardItems;
        private final int currentUserIndex;

        public LeaderboardAdapter(List<LeaderboardItem> leaderboardItems, int currentUserIndex) {
            this.leaderboardItems = leaderboardItems;
            this.currentUserIndex = currentUserIndex;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_leaderboard, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LeaderboardItem item = leaderboardItems.get(position);
            holder.tvName.setText(item.getName());
            holder.tvScore.setText(String.valueOf(item.getOverallScore()));

            if (position == currentUserIndex) {
                holder.tvRank.setText((position + 1) + ".");
                holder.itemView.setBackgroundColor(Color.YELLOW);
            } else {
                holder.tvRank.setText("");
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        @Override
        public int getItemCount() {
            Log.d("size-get item count", String.valueOf(leaderboardItems));
            return leaderboardItems != null ? leaderboardItems.size() : 0;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvRank, tvName, tvScore;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvRank = itemView.findViewById(R.id.tv_rank);
                tvName = itemView.findViewById(R.id.tv_name);
                tvScore = itemView.findViewById(R.id.tv_score);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void fetchLeaderboardData() {
        progressDialog.show();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URIManager.BASE_URI_USER+"/leadership-rank")
                .addHeader("Authorization", "Bearer " + tokenManager.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                progressDialog.dismiss();
                leaderboardItems = new ArrayList<>();
                Log.d("error", "onFailure: ");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();
                String responseData = response.body().string();
                final int statusCode = response.code();
                Log.d("response", responseData);

                    if (statusCode==200) {


                        leaderboardItems = new ArrayList<>(updateLeaderboard(responseData));
                        Log.d("size", String.valueOf(leaderboardItems.size()));

                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                leaderboardAdapter = new LeadershipFragment.LeaderboardAdapter(leaderboardItems, currentUserIndex);
                                rvLeaderboard.setAdapter(leaderboardAdapter);
                            }
                        });



                    } else {
                        // Handle unsuccessful response
                    }


            }
        });
    }

    private List<LeaderboardItem> updateLeaderboard(String responseData){
        List<LeaderboardItem> leaderboardItemArr = new ArrayList<>();
        try {
            JSONObject mainObject = new JSONObject(responseData);
            JSONArray responseArray = mainObject.getJSONArray("leaders");
            currentUserIndex = mainObject.getInt("index");

            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject userObject = responseArray.getJSONObject(i);
                String name = userObject.getString("name");
                int overallScore = userObject.getInt("overallScore");
                String id = userObject.getString("id");

                Log.d("data response", currentUserIndex+" "+name+" "+id+" "+overallScore);


                LeaderboardItem q = new LeaderboardItem(name,overallScore,id);


                leaderboardItemArr.add(q);
            }
        } catch (JSONException e) {
            Log.e("JSON_ERROR", "Error parsing JSON response: " + e.getMessage());
//            throw new RuntimeException(e);
        }
        return leaderboardItemArr;
    }


}