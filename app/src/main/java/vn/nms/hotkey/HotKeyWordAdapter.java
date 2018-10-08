package vn.nms.hotkey;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HotKeyWordAdapter extends RecyclerView.Adapter<HotKeyWordAdapter.ViewHolder> {
    private List<HotKeyWordModel> mData;

    public void setData(List<HotKeyWordModel> list) {
        mData = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(View.inflate(viewGroup.getContext(), R.layout.hotkey_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        HotKeyWordModel item = getItemAtPosition(position);
        if (null != item) {
            if(null != item.getKeyword()){
                viewHolder.textView.setText(item.getKeyword());
            }else{
                viewHolder.textView.setText("");
            }
            viewHolder.textView.getBackground().setColorFilter(item.getColor(),
                    PorterDuff.Mode.SRC_OVER);
        }

    }


    @Override
    public int getItemCount() {
        return null == mData || mData.isEmpty() ? 0 : mData.size();
    }

    private HotKeyWordModel getItemAtPosition(int position) {
        return null == mData
                || position < 0
                || mData.size() == 0
                || mData.size() < position
                ? null : mData.get(position);
    }
}
