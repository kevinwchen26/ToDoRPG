package com.cs429.todorpg.revised;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class RewardsAdapter extends ArrayAdapter<Reward>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Reward> data = null;
    
    public RewardsAdapter(Context context, int layoutResourceId, ArrayList<Reward> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RewardHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new RewardHolder();
            holder.cost = (TextView)row.findViewById(R.id.reward_cost);
            holder.info = (TextView)row.findViewById(R.id.reward_title);
            holder.button = (Button)row.findViewById(R.id.purchase_reward);
            
            row.setTag(holder);
        }
        else
        {
            holder = (RewardHolder)row.getTag();
        }
        
        Reward reward = data.get(position);
        holder.info.setText(reward.getInfo());
        holder.cost.setText(Integer.toString(reward.getCost()));
        
        row.findViewById(R.id.purchase_reward).setTag(position);
        
        return row;
    }
    
    static class RewardHolder
    {
        TextView cost;
        TextView info;
        Button button;
    }
}