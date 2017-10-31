package cn.mrzhqiang.oreo;

import java.util.Arrays;
import java.util.List;

/**
 * 常量
 *
 * @author mrZQ.
 */

public final class Constant {

  private Constant() {
  }

  public static int findIndexByArray(String[] array, String content) {
    List<String> stringList = Arrays.asList(array);
    return stringList.indexOf(content);
  }

  public static String[] findChildByGroup(String groupName) {
    List<String> stringList = Arrays.asList(GROUP_NAMES);
    int index = stringList.indexOf(groupName);
    switch (index) {
      case 0:
        return USER_CHILD_NAME;
      case 1:
        return SYSTEM_CHILD_NAME;
      case 2:
        return MEDIA_CHILD_NAME;
      case 3:
        return CONNECT_CHILD_NAME;
      case 4:
        return SHARE_CHILD_NAME;
      case 5:
        return ACCESSIBLE_CHILD_NAME;
      case 6:
        return SAFE_CHILD_NAME;
      case 7:
        return TEST_CHILD_NAME;
      case 8:
        return RUN_CHILD_NAME;
      case 9:
        return EE_CHILD_NAME;
      default:
        return null;
    }
  }

  public static final String[] GROUP_NAMES = {
      "用户体验", "系统", "媒体增强功能", "连接", "共享", "无障碍功能", "安全性与隐私", "测试", "运行时和工具", "Android 企业版"
  };

  public static final String[] GROUP_NAMES_ACTION = {
      "user", "system", "media", "connectivity", "shareing", "accessibility", "security", "testing", "runtime", "enterprise"
  };

  public static final String[] USER_CHILD_NAME = {
      "通知", "自动填充", "画中画", "可下载字体", "XML中的字体", "自动调整TextView大小", "自适应图标", "颜色管理", "WebView API",
      "固定快捷方式和小部件", "最大屏幕纵横比", "多显示器支持", "统一的布局外边距和内边距", "指针捕获", "应用类别", "Android TV 启动器",
      "AnimatorSet", "输入和导航"
  };

  public static final String[] SYSTEM_CHILD_NAME = {
      "新的 StrictMode 检测程序", "缓存数据", "内容提供程序分页", "内容刷新请求", "JobScheduler改进", "自定义数据存储",
      "findViewById()签名变更"
  };

  public static final String[] MEDIA_CHILD_NAME = {
      "VolumeShaper", "音频焦点增强功能", "媒体指标", "MediaPlayer", "音频录制器", "音频回放控制", "增强的媒体文件访问功能"
  };

  public static final String[] CONNECT_CHILD_NAME = {
      "WLAN感知", "蓝牙", "配套设备配对",
  };

  public static final String[] SHARE_CHILD_NAME = {
      "智能共享", "智能文本选择",
  };

  public static final String[] ACCESSIBLE_CHILD_NAME = {
      "无障碍功能按钮", "独立的音量调整", "指纹手势", "字词级突出显示", "标准化单端范围值", "提示文本", "连续的手势分派"
  };

  public static final String[] SAFE_CHILD_NAME = {
      "权限", "新的账号访问和Discovery API", "Google Safe Browsing API"
  };

  public static final String[] TEST_CHILD_NAME = {
      "仪器测试", "用于测试的模拟Intent"
  };

  public static final String[] RUN_CHILD_NAME = {
      "平台优化", "更新的java支持", "更新的ICU4J Android Framework API"
  };

  public static final String[] EE_CHILD_NAME = {
      "企业版"
  };
}
