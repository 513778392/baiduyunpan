package fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.GuiCuShiPinActivity;
import com.example.myapplication.R;
import com.example.myapplication.XinPianActivity;
import com.example.myapplication.ZhiFuBaoActivity;
import com.example.viewpager.view.CircleViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.WoDeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import data.SharedPreferencesUtils;
import entity.TanSuoEntity;
import entity.TuiChu;
import service.BaiTian;

/**
 * Created by Administrator on 2017/8/17 0017.
 * 个人中心
 */

public class WoDeFragment extends MyFragment {

    View view;
    @Bind(R.id.gridviiew)
    GridView gridviiew;
    WoDeAdapter adapter;
    TanSuoEntity entity = new TanSuoEntity();
    List<TanSuoEntity> list;
    @Bind(R.id.viewpager)
    CircleViewPager viewpager;
    List<String> lists = new ArrayList<>();
    String content = "【支付宝】年终红包再加10亿！12月24日还有机会获得圣诞惊喜红包！长按复制此消息，打开最新版支付宝就能领取！8olsOs015G";
    @Bind(R.id.wode_re)
    RelativeLayout wode_re;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.wode_layout,container,false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        lists.add("http://bmob-cdn-13761.b0.upaiyun.com/2018/01/15/4646a2da40467287807aef9e9a7c01be.png");
        viewpager.setUrlList(lists);
        list = entity.wode();
        adapter = new WoDeAdapter(list);
        gridviiew.setAdapter(adapter);
        gridviiew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case  0:
                       // startActivity(new Intent(getActivity(), GuiCuActivity.class));
                       // yunpa.getYunpan();
                        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        //    设置Title的图标
                        builder.setIcon(R.drawable.ic_launcher);
                        //    设置Title的内容
                        builder.setTitle("提示");
                        //    设置Content来显示一个信息
                        builder.setMessage("是否退出当前账号吗？");
                        //    设置一个PositiveButton
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                CookieManager.getInstance().removeAllCookie();
                                SharedPreferencesUtils.putBean(getContext(),"user",false);
                                yunpa.getYunpan();
                                EventBus.getDefault().post(new TuiChu());
                            }
                        });
                        //    设置一个NegativeButton
                        builder.setNegativeButton("取消",null);

                        //    显示出该对话框
                        builder.show();



                        break;
                    case  1:
                        /*startActivity(new Intent(getActivity(), ZhiFuBaoActivity.class));
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade);*/
                        startActivity(new Intent(getActivity(),XinPianActivity.class));
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade);
                        break;
                    case  2:
                        startActivity(new Intent(getActivity(),GuiCuShiPinActivity.class));
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade);
                       /* startActivity(new Intent(getActivity(),XinPianActivity.class));

                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade);*/
                        break;
                    case  3:
                        startActivity(new Intent(getActivity(),GuiCuShiPinActivity.class));
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade);
                        break;
                    case  4:
                        String s = "探索云盘搜索\n" +
                                "安卓APP下载地址\n" +
                                "tansuo233.com/app \n";
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, s);
                        intent.setType("text/plain");
                        //设置分享列表的标题，并且每次都显示分享列表
                        startActivity(Intent.createChooser(intent, "分享到"));
                        break;
                    case 5:
                        joinQQGroup("k90aGzCjucuiu_InKwSP5KbsDaONDFH8");
                       /* ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(content);

                        try {
                            PackageManager packageManager
                                    = getActivity().getApplicationContext().getPackageManager();
                            Intent intent1 = packageManager.
                                    getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                            startActivity(intent1);
                        }catch (Exception e) {
                            String url = "https://ds.alipay.com/?from=mobileweb";
                            Intent intent3 = new Intent(Intent.ACTION_VIEW);
                            intent3.setData(Uri.parse(url));
                            startActivity(intent3);
                        }*/
                        break;
                    default:
                        break;
                }
            }
        });

        setBaitian();
        return view;
    }
    void setBaitian(){
        if(isBaitian()){
            wode_re.setBackgroundResource(R.color.beiying);
            setViewBai(gridviiew);
            setViewBai(view1);
        }else {
            setViewHei(gridviiew);
            setViewHei(view1);
            setHei1(wode_re);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Bind(R.id.view)
    View view1;

    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D"
                + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            getActivity().startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }
   public interface BaiduYunpa {
        void getYunpan();
    }
    BaiduYunpa yunpa;
    public void setYunpa(BaiduYunpa yunpa){
        this.yunpa = yunpa;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaiTian event) {
        setBaitian();
       if(adapter != null){
           adapter.notifyDataSetChanged();
       }

    }
}
