package zkch.com.framework.common.mvp;

import java.util.List;

import zkch.com.framework.bean.MainModel;
import zkch.com.framework.bean.MovieSubject;
import zkch.com.framework.bean.Subject;

/**
 * Created by WuXiaolong on 2015/9/23.
 */
public interface MainView extends BaseView {
    void getDataSuccess(List<Subject> model);

    void getDataSuccess(MainModel model);

    void getDataFail(String msg);

    void getData(MovieSubject model);

}
