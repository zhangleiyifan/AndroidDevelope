package com.gyz.androiddevelope.activity.tngou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.base.BaseToolbarActivity;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.AlbumDetailListBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.ToastUtil;
import com.gyz.androiddevelope.view.cardslidepanel.CardDataItem;
import com.gyz.androiddevelope.view.cardslidepanel.CardSlidePanel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-04-22 16:52
 */
public class AlbumDetailActivity extends BaseToolbarActivity {
    private static final String TAG = "AlbumDetailActivity";
    @Bind(R.id.card_bottom_layout)
    LinearLayout cardBottomLayout;
    @Bind(R.id.image_slide_panel)
    CardSlidePanel slidePanel;

    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private List<CardDataItem> dataList = new ArrayList<>();
    public static final String ALBUM_ID = "album_id";

    private int albumId;

    public static void startActivity(int id) {
        Intent intent = new Intent(BaseApplication.getInstantce(), AlbumDetailActivity.class);
        intent.putExtra(ALBUM_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstantce().startActivity(intent);

    }

    @Override
    protected void initVariables() {
        albumId = getIntent().getIntExtra(ALBUM_ID, 1);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_layout);
        ButterKnife.bind(this);
        getAppBar().setVisibility(View.GONE);
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
            }

            @Override
            public void onCardVanish(int index, int type) {
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);

    }

    @Override
    protected void loadData() {


        RxUtil.subscribeAll(new Func1<String, Observable<AlbumDetailListBean>>() {
            @Override
            public Observable<AlbumDetailListBean> call(String s) {
                return ReUtil.getApiManager(AppContants.TNGOU_HTTP).getAlbumDetailList(albumId);
            }
        }, new MySubscriber<AlbumDetailListBean>() {
            @Override
            public void onNext(AlbumDetailListBean o) {
                if (o.getMsg() != null) {
                    ToastUtil.showLong(AlbumDetailActivity.this, o.getMsg());
                    return;
                }
                if (!o.getList().isEmpty())
                prepareCardView(o.getList());

            }
        });
    }

    @Override
    protected String currActivityName() {
        return "图片详情页";
    }

    private void prepareCardView(List<AlbumDetailListBean.Picture> list) {


        for (AlbumDetailListBean.Picture picture: list){

            dataList.add(new CardDataItem(  AppContants.TG_IMAGE_HEAD+picture.getSrc()));
        }
        slidePanel.fillData(dataList);
    }

}
