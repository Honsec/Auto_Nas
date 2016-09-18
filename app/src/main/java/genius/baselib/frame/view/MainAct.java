package genius.baselib.frame.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.genius.hongsec.nasa.R;
import com.nextapps.naswall.NASWall;
import com.nextapps.naswall.NASWallAdInfo;
import com.sera.hongsec.volleyhelper.VolleyHelper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import genius.baselib.PreferenceUtil;
import genius.baselib.frame.auto.AutoAccount;
import genius.baselib.frame.base.BaseAct;
import genius.baselib.frame.center.CConfig;
import genius.baselib.frame.center.CStatic;
import genius.baselib.frame.inter.GoogleUserInfo;
import genius.baselib.frame.util.AutoSwipeListener;
import genius.baselib.frame.util.CTools;
import genius.baselib.inter.ClickFilter;
import genius.commonrecyclerviewadpater.CommonAdapter;
import genius.commonrecyclerviewadpater.base.ViewHolder;
import genius.commonrecyclerviewadpater.devider.DividerItemDecoration;
import genius.utils.UtilsLog;
import genius.utils.Utils_Alert;

/**
 * Created by Hongsec on 2016-09-07.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public class MainAct extends BaseAct {
    private AutoSwipeListener id_main_swipe;
    private RecyclerView id_main_recyclerview;
    private TextView id_main_point;
    private TextView id_main_nickname;
    private ProgressBar id_main_progress;
    private TextView id_main_account;
    private MyAdapter adapter;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.act_main;
    }

    @Override
    protected void viewLoadFinished() {

        //유저정보중 계정으로 나스 초기화
        NASWall.init(MainAct.this, CConfig.is_debug, PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_ACCOUNT,CTools.getIMEI(getApplicationContext())));

        getUerInfo(true);
//        NASWall.openCS();
    }

    Handler handler = new Handler();


    /**
     * 나스 초기화
     * @param refreshlist
     */
    private void getUerInfo(final boolean refreshlist) {
        id_main_progress.setVisibility(View.VISIBLE);
        //getUserInfo
        AutoAccount.getUserInfo(MainAct.this, new GoogleUserInfo() {
            @Override
            public void result(final String nickname, final String account) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //유저정보 세팅
                        if (!TextUtils.isEmpty(account)) {
                            id_main_account.setText(account);
                            PreferenceUtil.getInstance(getApplicationContext()).setValue(CStatic.SP_ACCOUNT, account);
                        }
                        if (!TextUtils.isEmpty(nickname)) {
                            id_main_nickname.setText(nickname);
                            PreferenceUtil.getInstance(getApplicationContext()).setValue(CStatic.SP_NICKNAME, nickname);
                        }


                        //유저정보중 계정으로 나스 초기화
                        NASWall.init(MainAct.this, CConfig.is_debug, PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_ACCOUNT, ""));
                        //나스 포인트 가져오기
                        NASWall.getUserPoint(MainAct.this, new NASWall.OnUserPointListener() {
                            @Override
                            public void OnSuccess(String account, int point, String eksdnk) {
                                id_main_progress.setVisibility(View.GONE);
                                id_main_point.setText(point + eksdnk);
                            }

                            @Override
                            public void OnError(String s, int i) {
                                UtilsLog.d("s:" + s + ",i:" + i);
                                id_main_progress.setVisibility(View.GONE);
                            }
                        });

                        if(!refreshlist)return;
                        //나스 리스트 업데이트
                        id_main_swipe.preformRefresh();
                        getNasList();
                    }
                });


            }
        });
    }


    @Override
    protected void initViews() {
        id_main_swipe = findViewBId(R.id.id_main_swipe);
        id_main_recyclerview = findViewBId(R.id.id_main_recyclerview);
        id_main_point = findViewBId(R.id.id_main_point);
        id_main_nickname = findViewBId(R.id.id_main_nickname);
        id_main_account = findViewBId(R.id.id_main_account);
        findViewBId(R.id.id_main_profile_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickFilter.isClicked())return;
                CTools.startAuto(MainAct.this);
            }
        });
        id_main_progress = findViewBId(R.id.id_main_progress);
        id_main_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        id_main_recyclerview.setHasFixedSize(true);
        id_main_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        id_main_recyclerview.setAdapter(adapter = new MyAdapter(this, R.layout.item_nasoffer, new ArrayList<NASWallAdInfo>()));
        id_main_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNasList();
            }
        });
    }


    /**
     * 나스 리스트 업데이트
     */
    public void getNasList() {
        NASWall.getAdList(MainAct.this, PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_ACCOUNT, ""), new NASWall.OnAdListListener() {
            @Override
            public void OnSuccess(final ArrayList<NASWallAdInfo> arrayList) {
                new AsyncTask<String, String, String>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        adapter.setmDatas(arrayList);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        adapter.notifyDataSetChanged();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                id_main_swipe.setRefreshing(false);
                            }
                        }, 200);

                    }
                }.execute();
            }

            @Override
            public void OnError(int i) {
                id_main_swipe.setRefreshing(false);
            }
        });

    }

    ClickFilter clickFilter = new ClickFilter();
    /**
     * 회원 정보 업데이트
     *
     * @param v
     */
    public void profile_refresh(View v) {
        if(clickFilter.isClicked())return;
        if (id_main_progress.getVisibility() != View.GONE) return;
        getUerInfo(false);
    }


    private class MyAdapter extends CommonAdapter<NASWallAdInfo> {

        public MyAdapter(Context context, int layoutId, List<NASWallAdInfo> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, final NASWallAdInfo nasWallAdInfo, int position) {
            //보너스 금액
            holder.setText(R.id.id_ns_item_reward, nasWallAdInfo.getRewardPrice() + nasWallAdInfo.getRewardUnit());
            //이름
            holder.setText(R.id.id_ns_item_title, nasWallAdInfo.getTitle());

            //설치유형 + 상세설명
//            String missionText= "<font color=\"#EE5141\">"+nasWallAdInfo.getMissionText()+"</font>";
//            String split = "<font color=\"#000000\">|</font>";
//            String description= "<font color=\"#000000\">"+nasWallAdInfo.getIntroText()+"</font>";
            holder.setText(R.id.id_ns_item_description, nasWallAdInfo.getMissionText());

            //아이콘
            ((NetworkImageView) holder.getView(R.id.id_nas_item_icon)).setImageUrl(nasWallAdInfo.getIconUrl(), VolleyHelper.getImageLoader(getApplicationContext()));


            //Join
            holder.getView(R.id.id_nas_item_bg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils_Alert.showAlertDialog(MainAct.this, nasWallAdInfo.getTitle(), R.string.nas_start_part, true, android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            showLoading();
                            NASWall.joinAd(MainAct.this, nasWallAdInfo, new NASWall.OnJoinAdListener() {
                                @Override
                                public void OnSuccess(NASWallAdInfo nasWallAdInfo, String url) {
                                    try {
                                        Intent intent = Intent.parseUri(url, 0);
                                        intent.setAction(Intent.ACTION_VIEW);
                                        if (intent != null) {
                                            startActivity(intent);
                                        }
                                        return;
                                    } catch (URISyntaxException e) {
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(MainAct.this, R.string.nas_start_mission_failed, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void OnError(NASWallAdInfo nasWallAdInfo, int i) {
                                    switch (i) {
                                        case -10001:
                                            Toast.makeText(MainAct.this, R.string.nas_already_end, Toast.LENGTH_SHORT).show();
                                            break;
                                        case -20001:
                                            Toast.makeText(MainAct.this, R.string.nas_already_complete, Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(MainAct.this, R.string.nas_failed_part, Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                }

                                @Override
                                public void OnComplete(NASWallAdInfo nasWallAdInfo) {
                                    cancleLoading();
                                }
                            });

                        }
                    }, android.R.string.cancel, null, null);

                }
            });
        }
    }


    /**
     * 앱끄기 필터
     */
    ClickFilter closeApp = new ClickFilter(2000L);

    @Override
    public void onBackPressed() {
        if (closeApp.isClicked()) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainAct.this, R.string.close_app, Toast.LENGTH_SHORT).show();
        }

    }

}
