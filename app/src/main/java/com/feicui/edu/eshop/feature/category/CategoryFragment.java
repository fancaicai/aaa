package com.feicui.edu.eshop.feature.category;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.edu.eshop.R;
import com.feicui.edu.eshop.base.utils.BaseFragment;
import com.feicui.edu.eshop.network.EShopClient;
import com.feicui.edu.eshop.network.core.UICallback;
import com.feicui.edu.eshop.network.entity.CategoryPrimary;
import com.feicui.edu.eshop.network.entity.CategoryRsp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.standard_toolbar_title)
    TextView standardToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar standardToolbar;
    @BindView(R.id.list_category)
    ListView listCategory;
    @BindView(R.id.list_children)
    ListView listChildren;
    private List<CategoryPrimary> mData;
    private CategoryAdapter mCategoryAdapter;
    private ChildrenAdapter mChildrenAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        initToolbar();
        mCategoryAdapter = new CategoryAdapter();
        listCategory.setAdapter(mCategoryAdapter);

        mChildrenAdapter = new ChildrenAdapter();
        listChildren.setAdapter(mChildrenAdapter);
        // 拿到数据
        if (mData != null) {
// 可以直接更新UI
            updateCategory();
        } else {
// 去进行网络请求拿到数据
            Call call = EShopClient.getInstance().getCategory();
            call.enqueue(new UICallback() {
                //                请求失败的处理
                @Override
                protected void onFailureInUI(Call call, IOException e) {
                    Toast.makeText(getContext(), "请求失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                //请求成功的处理
                @Override
                protected void onResponseInUI(Call call, Response response) {
                    if (response.isSuccessful()) {
                        try {
                            CategoryRsp categoryRsp=new Gson().fromJson(response.body().string(),CategoryRsp.class);
                            if (categoryRsp.getStatus().isSucceed()) {
                                mData=categoryRsp.getData();
                                // 数据有了之后，数据给一级分类，默认选择第一条，二级分类才能展示
                                updateCategory();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }

    // 更新分类数据
    private void updateCategory() {
        mCategoryAdapter.reset(mData);
        // 切换展示二级分类
        chooseCategory(0);
    }

    // 用于根据一级分类的选项展示二级分类的内容
    private void chooseCategory(int position) {
        listCategory.setItemChecked(position, true);
        mChildrenAdapter.reset(mCategoryAdapter.getItem(position).getChildren());
    }

    // 点击一级分类：展示相应二级分类
    @OnItemClick(R.id.list_category)
    public void ItemClick(int postion) {
        chooseCategory(postion);
    }

    // 点击二级分类
    @OnItemClick(R.id.list_children)
    public void onChildrenClick(int postion) {
        // TODO: 2017/2/24 会完善到跳转页面的
        String name = mChildrenAdapter.getItem(postion).getName();
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        // Fragment显示选项菜单(效果)
        setHasOptionsMenu(true);
        // 处理toolbar（作用是什么）
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(standardToolbar);
        // 处理actionbar不展示默认的标题
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        // 设置左上方的返回箭头
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        standardToolbarTitle.setText(R.string.category_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        if (itemId == R.id.menu_search) {
            // TODO: 2017/2/24 后期会跳转到搜素页面上
            Toast.makeText(getContext(), "点击了搜索", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }


}
