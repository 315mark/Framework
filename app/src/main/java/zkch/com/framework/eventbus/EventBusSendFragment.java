package zkch.com.framework.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import zkch.com.framework.R;
import zkch.com.framework.common.base.BaseFragment;

public class EventBusSendFragment extends BaseFragment {
    @BindView(R.id.send_eventBus)
    Button sendEvent;
    @BindView(R.id.sticky_eventBus)
    Button stickyEvent;
    @BindView(R.id.send_result)
    TextView sendResult;

    boolean isFirstFlag =true;

    public static EventBusSendFragment newInstance() {
        EventBusSendFragment fragment = new EventBusSendFragment();
        return fragment;
    }

    /**
     * protected  修饰指继承该类可执行方法
     */
    @Override
    protected int getLayoutId() {
        return R.layout.eventbus_send_fragment;
    }

    @Override
    protected void init() {

        //主线程发送数据按钮点击事件处理
        sendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4.发送消息
                EventBus.getDefault().post(new MessageEvent("主线程发来数据"));
                getActivity().finish();
            }
        });

        stickyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstFlag){
                   isFirstFlag = false;
                   //4.注册
                    EventBus.getDefault().register(EventBusSendFragment.this);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void StickyEventBus(StickyEvent event){
        //显示接收数据
        sendResult.setText(event.msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //5.解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendFragment.this);
    }
}
