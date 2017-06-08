package com.sh.shprojectdemo.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView

import com.jrfunclibrary.base.activity.BaseListViewActivity
import com.jruilibrary.widget.RefreshLayout
import com.jruilibrary.widget.SideslipListView
import com.sh.shprojectdemo.R
import com.sh.shprojectdemo.adapter.TestAdapter
import com.sh.shprojectdemo.model.User
import com.sh.shprojectdemo.presenter.UserListPresenter
import com.xinlan.dragindicator.DragIndicatorView

import java.util.ArrayList

import butterknife.ButterKnife
import butterknife.InjectView
import com.sh.shprojectdemo.model.City

class UserListActivity : BaseListViewActivity() {

    @InjectView(R.id.listview)
    internal var listview: SideslipListView? = null
    protected lateinit  var testAdapter: TestAdapter

    internal var list: MutableList<User> = ArrayList()
    @InjectView(R.id.refreshlayout)
    internal var refreshlayout: RefreshLayout? = null

    protected lateinit  var userListPresenter: UserListPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        ButterKnife.inject(this)
        userListPresenter = UserListPresenter(list, this)
        initView()
    }

    internal fun initView() {
        testAdapter = TestAdapter(list, this)
        testAdapter.setButtonCall(object : TestAdapter.ButtonCall {
            override fun delete(i: Int) {
                list.removeAt(i)
                testAdapter.notifyDataSetChanged()
                listview!!.turnToNormal()//归为
            }

            override fun read(count: DragIndicatorView, i: Int) {
                listview!!.turnToNormal()//归为
                count.dismissView()
            }
        })

        initListView(listview, refreshlayout, testAdapter)
        setColorSchemeColors(R.color.colorAccent)
    }

    //点击事件
    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

    }

    //下拉刷新
    override fun onRefresh() {
        userListPresenter.onRefresh()
    }

    //上拉加载
    override fun onLoad() {
        userListPresenter.onLoad()
    }

}
