package example.com.try_mvvm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class RVRepoAdapter extends RecyclerView.Adapter {

    Context mCtx;
    List<Repo> repos;

    public RVRepoAdapter(List<Repo> repos) {
        this.repos=repos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCtx = parent.getContext();

        //Set our CardView here
        View view = LayoutInflater.from(mCtx).inflate(R.layout.card_repo,parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RepoViewHolder)holder).tvName.setText(repos.get(position).name);
        ((RepoViewHolder)holder).tvId.setText("Id: " +String.valueOf(repos.get(position).id));
        ((RepoViewHolder)holder).tvUrl.setText(repos.get(position).url);

        ((RepoViewHolder)holder).tvOwner.setText("Owner: " +repos.get(position).owner.id);
        ((RepoViewHolder)holder).tvOwnerUrl.setText(repos.get(position).owner.url);

        //Download image with Picasso
        Picasso.with(mCtx)
                .load(repos.get(position).owner.avatar_url)
                .resize(500, 500)
                .centerCrop()
                .into(((RepoViewHolder)holder).ivOwner);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName,tvUrl,tvId, tvOwner, tvOwnerUrl;
        public ImageView ivOwner;

        public RepoViewHolder(View view) {
            super(view);

            tvName = (TextView)view.findViewById(R.id.tvRepoName);
            tvId = (TextView)view.findViewById(R.id.tvRepoId);
            tvUrl = (TextView)view.findViewById(R.id.tvRepoUrl);
            tvOwner = (TextView)view.findViewById(R.id.tvRepoOwner);
            tvOwnerUrl = (TextView)view.findViewById(R.id.tvRepoOwnerUrl);
            ivOwner = (ImageView) view.findViewById(R.id.ivRepoOwner);
        }
    }
}
