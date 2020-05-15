package okhttp;

import com.example.myapplication.App;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import data.AppToast;
import data.LogUtil;
import data.SharedPreferencesUtils;
import entity.Bibili;
import entity.Douban;
import entity.GuoLvEntity;
import entity.HitKeywork;
import entity.JsonResultUtil;
import entity.KeyWordList;
import entity.Keyword;
import entity.PageBean;
import entity.SearchJieguo;
import entity.SearchJson;
import entity.TuiSongXiaoXi;
import entity.TuisongChenggong;
import entity.XinPianEntity;
import entity.ZhongZiEntity;
import entity.Zhuan;
import entity.ZhuancunList;
import okhttp3.Call;


/**
 * Created by Administrator on 2017/9/1 0001.
 */
public class OkHttpData {
    /**
     * Post异步提交：登录
     *
     * @param account
     * @param password
     */
    public   static String bendi = "http://10.0.0.112";
    public  static String wang = "http://tansuo233.com";
   public static String wangye = wang;
    public static final String TOKEN = "token";
    //过滤接口
    public static final String GUOLV = wangye+"/api/filter";
    //搜索接口
    public static final String SOUSUO=wangye+"/api/search";
    //
    public static final String ZHONGZI = wangye+"/api/dirfiles";
    //预定新片接口
    public static final String YUDING = wangye+"/api/yuding";
    //用户注册
    public static final String REGISTER = wangye+"/api/registerUser";
    //推送接口
    public static final String USERYUDING = wangye+"/api/userYuding";
    //哔哩哔哩
    public static final String BIBILI = wangye+"/api/bibili";
    //转存路径排行榜
    public static final String ZHUANCUN = wangye+"/api/zhuancunList";
    //发送转出路径到服务器
    public static final String ZHUAN = wangye+"/api/zhuancun";
    //搜索索引
    public static final String WORD = wangye+"/api/keyword";
    //url豆瓣的href
    public static final String HREF = wangye+"/api/href";
    //热门搜索
    public static final String HIT = wangye+"/keyword/hit";
    //推送消息
    public static final String CHECK = wangye+"/api/check";
    //强力搜索
    public static final String QIANGLI = wangye+"/api/qSearch";
    public static void getQiangli(String search) {
        BaseOkHttpClient.newBuilder()
                .addParam("search", search)
                .get()
                .url(QIANGLI)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<SearchJson>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<SearchJson> fanHui) {
                        LogUtil.v("getQiangli"+fanHui.getData().size());
                        if(fanHui.getError() == 0) {

                                SearchJieguo jieguo = new SearchJieguo();
                                jieguo.setData(fanHui.getData());
                                EventBus.getDefault().post(jieguo);

                        }else {
                            AppToast.showToast(fanHui.getMsg());
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void getCheck() {
        BaseOkHttpClient.newBuilder()
                .get()
                .url(CHECK)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<Douban>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<Douban> fanHui) {
                        LogUtil.v("getCheck"+fanHui.toString());
                        if(fanHui.getError()==0){
                            List<Douban> list = fanHui.getData();
                            if(list != null&& list.size()>0) {
                                TuiSongXiaoXi keyWordList = new TuiSongXiaoXi();
                                keyWordList.setData(list);
                                EventBus.getDefault().post(keyWordList);
                            }
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void getHit() {
        BaseOkHttpClient.newBuilder()
                .get()
                .url(HIT)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<Keyword>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<Keyword> fanHui) {
                        LogUtil.v("getHit"+fanHui.toString());
                        if(fanHui.getError()==0){
                            List<Keyword> list = fanHui.getData();
                            HitKeywork keyWordList = new HitKeywork();
                            keyWordList.setData(list);
                            EventBus.getDefault().post(keyWordList);
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }


    public static void getHref(String href) {
        BaseOkHttpClient.newBuilder()
                .addParam("url", href   )
                .get()
                .url(HREF)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<SearchJson>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<SearchJson> fanHui) {
                        LogUtil.v("getHref"+fanHui.toString());
                        if(fanHui.getError()==0){
                            List<SearchJson> list = fanHui.getData();
                            SearchJieguo keyWordList = new SearchJieguo();
                            keyWordList.setData(list);
                            EventBus.getDefault().post(keyWordList);
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }

    public static void getKeyword(final String keyword) {
        BaseOkHttpClient.newBuilder()
                .addParam("keyword", keyword)
                .get()
                .url(WORD)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<Keyword>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<Keyword> fanHui) {
                        LogUtil.v("getUser"+fanHui.toString());
                        if(fanHui.getError()==0){
                            List<Keyword> list = fanHui.getData();
                            KeyWordList keyWordList = new KeyWordList();
                            keyWordList.setData(list);
                            EventBus.getDefault().post(keyWordList);
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }


    public static void postZhuan(int itemId) {
        BaseOkHttpClient.newBuilder()
                .addParam("itemId", itemId)
                .post()
                .url(ZHUAN)
                .build()
                .enqueue(new BaseCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean fanHui) {
                        LogUtil.v("getUser"+fanHui.toString());
                       if(fanHui){

                       }else {

                       }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void getZhuancunList() {
        BaseOkHttpClient.newBuilder()
                .get()
                .url(ZHUANCUN)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<ZhuancunList>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<ZhuancunList> fanHui) {
                        LogUtil.v(fanHui.toString());
                       if(fanHui.getError() == 0) {
                           if(fanHui.getData() != null&&fanHui.getData().size()>0) {
                               Zhuan zhuan = new Zhuan();
                               zhuan.setData(fanHui.getData());
                               EventBus.getDefault().post(zhuan);
                           }
                       }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }


    public static void getBibili(int pageNumber) {
        BaseOkHttpClient.newBuilder()
                .addParam("pageNumber", pageNumber)
                .get()
                .url(BIBILI)
                .build()
                .enqueue(new BaseCallBack<PageBean<Bibili>>() {
                    @Override
                    public void onSuccess(PageBean<Bibili> fanHui) {
                        LogUtil.v("getUser"+fanHui.toString());
                        EventBus.getDefault().post(fanHui);

                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }


    public static void postUserYuding(String phone, String versions, int id) {
        BaseOkHttpClient.newBuilder()
                .addParam("phone", phone)
                .addParam("versions", versions)
                .addParam("id", id)
                .post()
                .url(USERYUDING)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<String>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<String> fanHui) {
                        LogUtil.v("getUser"+fanHui.toString());
                        if(fanHui.getError() == 0) {
                            EventBus.getDefault().post(new TuisongChenggong());

                        }else if(fanHui.getError() == 1){
                            postUser();
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void postUser() {
        BaseOkHttpClient.newBuilder()
                .post()
                .url(REGISTER)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<String>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<String> fanHui) {
                        LogUtil.v("getUser"+fanHui.toString());
                        if(fanHui.getError() == 0) {
                          String token = fanHui.getMsg();
                          SharedPreferencesUtils.putBean(App.app,TOKEN,token);
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    //
    public static void getYuding(final int pageNumber) {
        BaseOkHttpClient.newBuilder()
                .addParam("pageNumber",pageNumber)
                .get()
                .url(YUDING)
                .build()
                .enqueue(new BaseCallBack<PageBean<Douban>>() {
                    @Override
                    public void onSuccess(PageBean<Douban> fanHui) {
                                if(fanHui!= null) {
                                    LogUtil.v("getYuding"+fanHui.getData().size());
                                    XinPianEntity entity = new XinPianEntity();
                                    entity.setData(fanHui);
                                    entity.setPageNumber(pageNumber);
                                    EventBus.getDefault().post(entity);
                                }else {
                                    postUser();
                                }

                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void getZhongzi(String id, final String type) {
        BaseOkHttpClient.newBuilder()
                .addParam("id", id)
                .get()
                .url(ZHONGZI)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<SearchJson>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<SearchJson> fanHui) {
                        LogUtil.v("getGuolv"+fanHui.getData().toString());
                        if(fanHui.getError() == 0) {
                            if(fanHui.getData() != null &&fanHui.getData().size()>0){
                                ZhongZiEntity jieguo = new ZhongZiEntity();
                                jieguo.setData(fanHui.getData());
                                jieguo.setType(type);
                                EventBus.getDefault().post(jieguo);
                            }else {
                                AppToast.showToast("文件夹是空的");
                            }
                        }else if(fanHui.getError() == 1){
                            postUser();
                        }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void getSousuo(String search) {
        BaseOkHttpClient.newBuilder()
                .addParam("search", search)
                .get()
                .url(SOUSUO)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<SearchJson>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<SearchJson> fanHui) {
                        LogUtil.v("postSubmit"+fanHui.getData().size());
                            if(fanHui.getError() == 0) {

                                    SearchJieguo jieguo = new SearchJieguo();
                                    jieguo.setData(fanHui.getData());
                                    EventBus.getDefault().post(jieguo);

                            }else {

                                AppToast.showToast(fanHui.getMsg());
                            }
                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }
    public static void getGuolv(String ids) {
        BaseOkHttpClient.newBuilder()
                .addParam("ids", ids)
                .get()
                .url(GUOLV)
                .build()
                .enqueue(new BaseCallBack<JsonResultUtil<Integer>>() {
                    @Override
                    public void onSuccess(JsonResultUtil<Integer> fanHui) {
                        LogUtil.v("getGuolv"+fanHui.getData().size());
                                GuoLvEntity jieguo = new GuoLvEntity();
                                jieguo.setList(fanHui.getData());
                                EventBus.getDefault().post(jieguo);


                    }
                    @Override
                    public void onError(int code) {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.v(e.getMessage());
                    }
                });
    }

}
