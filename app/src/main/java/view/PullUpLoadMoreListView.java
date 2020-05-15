package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.myapplication.R;

/**
 * Created by Administrator on 2018/1/5 0005.
 */
public class PullUpLoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private boolean isLoading  = false;

    private View mFooterView;

    private int mFooterHeight;

    private OnLoadMoreListener mListener;
    private LayoutInflater inflater;

    public PullUpLoadMoreListView(Context context) {
        this(context, null);
    }

    public PullUpLoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpLoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initFooterView();
        setOnScrollListener(this);
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        mFooterView = inflater.inflate(R.layout.load_more, null);
        mFooterView.measure(0, 0);
        mFooterHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterHeight, 0, 0);
        this.addFooterView(mFooterView);
    }

    @Override public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
        if(this.getLastVisiblePosition() == this.getAdapter().getCount() - 1
                && !isLoading && (scrollstate == SCROLL_STATE_FLING || scrollstate == SCROLL_STATE_IDLE)){
            setLoadState(true);
            if(this.mListener != null){
                this.mListener.loadMore();
            }
        }
    }

    /**
     * 设置状态
     * @param b
     */
    public void setLoadState(boolean b) {
        this.isLoading = b;
        if(isLoading){
            mFooterView.setPadding(0,0,0,0);
            this.setSelection(this.getAdapter().getCount() + 1);
        }else {
            mFooterView.setPadding(0,-mFooterHeight,0,0);
        }
    }

    @Override public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mListener = listener;
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }

   /* 作者：meskal
    链接：https://www.jianshu.com/p/67ccc232759f
    來源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
}
