package com.example.deliveryfoodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.deliveryfoodapp.Activity.ShowDetailActivity;
import com.example.deliveryfoodapp.Domain.CategoryDomain;
import com.example.deliveryfoodapp.Domain.VideoDomain;
import com.example.deliveryfoodapp.R;

import java.util.ArrayList;

public class GridVideoAdapter extends BaseAdapter {
    Context context;
    ArrayList<VideoDomain> videoList;

    LayoutInflater inflater;

    public GridVideoAdapter(Context context, ArrayList<VideoDomain> videoList) {
        this.context = context;
        this.videoList = videoList;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.viewholder_video, null);

        ImageView imageView = convertView.findViewById(R.id.videoPic);
        TextView text_title = convertView.findViewById(R.id.videoName);
        ConstraintLayout mainLayout = convertView.findViewById(R.id.videolayout);

        int picture = convertView.getContext().getResources().getIdentifier(videoList.get(position).getPic(), "drawable", convertView.getContext().getPackageName());


        Glide.with(convertView)
                .load(picture)
                .into(imageView);

        text_title.setText(videoList.get(position).getTitle());

        View finalConvertView = convertView;
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = videoList.get(position).getTitle();
                switch (title){
                    case "Pizza":{
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=jxVOPjOTKoo"));

                        Intent chooserIntent = Intent.createChooser(intent, "Open With");
                        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finalConvertView.getContext().startActivity(chooserIntent);
                        break;
                    }
                    case "Bento":{
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=UlKkQ9qnmzY"));

                        Intent chooserIntent = Intent.createChooser(intent, "Open With");
                        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finalConvertView.getContext().startActivity(chooserIntent);
                        break;
                    }
                    case "Cake":{
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=eS-QL5HAAr8"));

                        Intent chooserIntent = Intent.createChooser(intent, "Open With");
                        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finalConvertView.getContext().startActivity(chooserIntent);
                        break;
                    }
                    case "Drink":{
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=KDZ6CEtZ8KI"));

                        Intent chooserIntent = Intent.createChooser(intent, "Open With");
                        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finalConvertView.getContext().startActivity(chooserIntent);
                        break;
                    }

                }

            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }
}