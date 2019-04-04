package zkch.com.framework.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import zkch.com.framework.R;
import zkch.com.framework.base.BaseFragment;
import zkch.com.framework.utils.FragmentUtils;

public class EventBusFragment extends BaseFragment {
    @BindView(R.id.eventbus_send)
    Button Send;
    @BindView(R.id.eventbus_sticky)
    Button Sticky;
    @BindView(R.id.eventbus_result)
    TextView Result;

    /**
     * protected  修饰指继承该类可执行方法
     */
    @Override
    protected int getLayoutId() {
        return R.layout.eventbus_fragment;
    }

    public static EventBusFragment newInstance() {

        return new EventBusFragment();
    }

    @Override
    protected void init() {
        //注册广播
        EventBus.getDefault().register(EventBusFragment.this);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  FragmentUtils.replace(EventBusFragment.newInstance(),EventBusSendFragment.newInstance());

            }
        });

        Sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送事件
                EventBus.getDefault().postSticky(new StickyEvent("粘性事件"));
                //
              //  FragmentUtils.replace(EventBusFragment.newInstance(),EventBusSendFragment.newInstance());
            }
        });
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent event){
        Result.setText(event.name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(EventBusFragment.this);
    }
}
