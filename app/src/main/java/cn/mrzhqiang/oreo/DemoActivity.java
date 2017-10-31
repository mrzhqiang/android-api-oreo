package cn.mrzhqiang.oreo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.mrzhqiang.oreo.Constant.*;

/**
 * 演示Android 8.0 Oreo的新特性和功能
 *
 * @author mrZQ
 */
public class DemoActivity extends AppCompatActivity {

  private final List<Map<String, String>> groupDataList = new ArrayList<>();
  private final List<List<Map<String, String>>> childDataList = new ArrayList<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_demo);

    loadData();

    initView();
  }

  private void loadData() {
    // 父级列表的数据生成
    for (String aGroup : GROUP_NAMES) {
      Map<String, String> groupItem = new HashMap<>(1);
      groupItem.put("group_name", aGroup);
      groupDataList.add(groupItem);
    }

    // 子级列表的数据生成
    childDataList.add(createChildData(USER_CHILD_NAME));
    childDataList.add(createChildData(SYSTEM_CHILD_NAME));
    childDataList.add(createChildData(MEDIA_CHILD_NAME));
    childDataList.add(createChildData(CONNECT_CHILD_NAME));
    childDataList.add(createChildData(SHARE_CHILD_NAME));
    childDataList.add(createChildData(ACCESSIBLE_CHILD_NAME));
    childDataList.add(createChildData(SAFE_CHILD_NAME));
    childDataList.add(createChildData(TEST_CHILD_NAME));
    childDataList.add(createChildData(RUN_CHILD_NAME));
    childDataList.add(createChildData(EE_CHILD_NAME));
  }

  private List<Map<String, String>> createChildData(String[] resource) {
    List<Map<String, String>> mapList = new ArrayList<>();
    for (String value : resource) {
      Map<String, String> map = new HashMap<>(1);
      map.put("child_name", value);
      mapList.add(map);
    }
    return mapList;
  }

  private void initView() {
    ExpandableListView mList = findViewById(android.R.id.list);

    mList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
      @Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
          int childPosition, long id) {
        String groupName = GROUP_NAMES_ACTION[groupPosition];
        Intent intent = new Intent(groupName + childPosition);
        startActivity(intent);
        return true;
      }
    });

    mList.setAdapter(new SimpleExpandableListAdapter(this, groupDataList,
        android.R.layout.simple_expandable_list_item_1, new String[] { "group_name" },
        new int[] { android.R.id.text1 }, childDataList, android.R.layout.simple_list_item_1,
        new String[] { "child_name" }, new int[] { android.R.id.text1 }));
  }
}
