package zkch.com.framework.okhttp.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import zkch.com.framework.R;
import zkch.com.framework.okhttp.bean.DataBean;

/**
 * BaseQuickAdapter<T,BaseViewHolder> T 是泛型 可以是实体类
 */
public class OkHttpListAdapter extends BaseQuickAdapter<DataBean.ItemData, BaseViewHolder> {

    /**
     * 增加一个构造方法，便于没有数据时候初始化适配器
     */
    public OkHttpListAdapter() {
        super(R.layout.item_okhttp_list_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean.ItemData data) {
        helper.setText(R.id.tv_name,data.getMovieName())
              .setText(R.id.tv_desc,data.getVideoTitle());
        Picasso.with(mContext)
                .load(data.getCoverImg())

                .resize(50, 50)
                .into((ImageView) helper.getView(R.id.img_icon));
    }

}
