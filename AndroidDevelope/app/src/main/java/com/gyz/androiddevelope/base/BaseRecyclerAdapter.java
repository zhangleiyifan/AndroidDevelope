package com.gyz.androiddevelope.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-11 17:22
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = -1;

    private List<T> mDatas = new ArrayList<>();
    private View mHeaderView, mFooterView;
    private OnItemClickListener mListener;
    private int pos;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyDataSetChanged();
    }

    public View getmFooterView() {
        return mFooterView;
    }

    public void addDatas(List<T> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearDatas() {
        if (mDatas != null)
            mDatas.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;


//        if (position == 0) {
//            if (mHeaderView != null) {
//                return TYPE_HEADER;
//            } else {
//                return TYPE_NORMAL;
//            }
//        } else if (position < mDatas.size()) {
//            return TYPE_NORMAL;
//        } else {
//            return TYPE_FOOTER;
//        }


//        if (mHeaderView != null) {
//        if (position == 0) {
//            L.e("HHHHHHHHHHHHHHHHHHHH"+position);
//            return TYPE_HEADER;
//        } else if (position < mDatas.size() + 1) {
//            L.e("N------------------------"+position);
//            return TYPE_NORMAL;
//        } else if (mFooterView != null) {
//            L.e("FFFFFFFFFFFFFFFFFFFFFFFFF11111"+position);
//            return TYPE_FOOTER;
//        }
//    } else {
//        if (position < mDatas.size()) {
//            L.e("N------------------------"+position);
//            return TYPE_NORMAL;
//        } else if (mFooterView != null) {
//            L.e("FFFFFFFFFFFFFFFFFFFFFFFFF22222"+position);
//            return TYPE_FOOTER;
//        }
//    }
//
//    L.e("FFFFFFFFFFFFFFFFFFFFFFFFF33333"+position);
//    return TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        if (mFooterView != null && viewType == TYPE_FOOTER) return new Holder(mFooterView);
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            //获取position信息
            pos = getRealPosition(viewHolder);
            final T data = mDatas.get(pos);
            onBind(viewHolder, pos, data);
            if (mListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View itemView) {
                        mListener.onItemClick(pos, data, itemView);
                    }
                });
            }
        } else {
            return;
        }
    }

    //    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if(manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return getItemViewType(position) == TYPE_HEADER
//                            ? gridManager.getSpanCount() : 1;
//                }
//            });
//        }
//    }
//    @Override
//    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//        if(lp != null
//                && lp instanceof StaggeredGridLayoutManager.LayoutParams
//                && holder.getLayoutPosition() == 0) {
//            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
//            p.setFullSpan(true);
//        }
//    }
//
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public int getPosition() {
        return pos;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);


    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data, View parent);
    }
}
