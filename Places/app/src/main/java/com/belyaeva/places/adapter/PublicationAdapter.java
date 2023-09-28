package com.belyaeva.places.adapter;

import static com.belyaeva.places.rest.PlacesApiImpl.API_TEST;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belyaeva.places.R;
import com.belyaeva.places.db.Db;
import com.belyaeva.places.domain.Publication;
import com.bumptech.glide.Glide;

import java.util.List;

public class PublicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Publication> publicationList;

    public PublicationAdapter(Context context, List<Publication> publicationList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.publicationList = publicationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Publication publication = Db.PUBLICATION_LIST.get(position);
        ((MyHolder)holder).tv_user_name.setText(publication.getNameAuthor());
        ((MyHolder)holder).tv_info.setText(publication.getInfo());

        /*byte[] bytes = Base64.decode(publication.getIcon(),Base64.DEFAULT);
        Glide.with(context)
                .load(bytes)
                .placeholder(R.drawable.ic_cancel)
                .into(((MyHolder)holder).iv_image);*/
        ((MyHolder)holder).iv_image.setImageBitmap(publication.getImage());
    }

    @Override
    public int getItemCount() {
        return Db.PUBLICATION_LIST.size();
    }


    private class MyHolder extends RecyclerView.ViewHolder{

        private TextView tv_user_name, tv_info;
        private ImageView iv_image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_info = itemView.findViewById(R.id.tv_info);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }

}
