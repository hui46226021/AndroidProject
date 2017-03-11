package com.sh.shprojectdemo.presenter;

import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.sh.shprojectdemo.biz.UserOperationBiz;
import com.sh.shprojectdemo.view.HomeView;

/**
 * Created by zhush on 2017/1/25.
 * E-mail 405086805@qq.com
 * PS
 */
public class HomePresenter {

    HomeView homeView;
    UserOperationBiz userOperationBiz = new UserOperationBiz();
    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }
    //一般 这些数据 都在再网络上获取   这里应该是获取逻辑
    public void initViewData(){

        String[] urls = new String[]{"http://www.mobileui.cn/blog/uploads/2015/02/2202032219.jpeg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487578011676&di=1b49981c1b51d954d2a2f67e2b3e504f&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201407%2F31%2F000513mtxxldodtxtlbjoh.png",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487577385127&di=454683fabd40813585d640971cb8b261&imgtype=0&src=http%3A%2F%2Fp1.image.hiapk.com%2Fuploads%2Fallimg%2F141124%2F7730-141124100211.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488172303&di=8057d775e88fc75e2830d9433bd3ceca&imgtype=jpg&er=1&src=http%3A%2F%2Fi1.download.fd.pchome.net%2Ft_960x600%2Fg1%2FM00%2F0C%2F0F%2FoYYBAFRh0Z-IZt-rAAFTtOctLdEAACEuABjxbsAAVPM869.png"
        };
        homeView.getImages(urls);
    }

    /**
     * 测试掉线
     */
    public void dropTest(){
        userOperationBiz.dropTest(new RequestCall() {
            @Override
            public void success(Object dataResult) {

            }

            @Override
            public void failed(String message, int errorCode) {

            }
        });
    }
}
