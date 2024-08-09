# QG工作室移动组SDK

## 具体工具类以及方法

### QGExcptionHandler
**在你的项目中的自定义Application中使用**
```java
//为项目安装QGExcptionHandler
public static void install(Context context);

//开启防崩溃，可以选择性传递参数noToast，true开启，默认为false
public void avoidCrash(boolean noToast);  / avoidCrash();

//设置异常出现时的toast的提示文字，如果noToast为true，则不会有toast
public void setExceptionToast(String toastString);
```

### FPSCounter
```java
//初始化FPSCounter，通常在自定义Application使用
public static void initFPSCounter();

//获取当前FPS
public static int getFPS();
```

### MemoryInfoProvider
```java
//初始化，通常在自定义Application使用
public static void initMemoryInfoProvider(Context context);

//获取空闲内存、总内存和已使用内存
public static int getAvailMemory();
public static int getTotalMemory();
public static int getUsedMemory();
```

### UploadPresenter
上报异常和性能情况的工具类，通常会自动允许，首先需要在自定义Application中初始化
```java
public static void initUploadPresenter(Context context, String baseUrl, int projectId);
```

## 简易使用
**以下使用简单代码来演示如何使用**
```java
//首先，在你的com.example.yourproject文件夹下创建一个继承自Application的自定义类
public class QGApplication extend Application {
	//然后，重写onCreate()方法，在里面添加使用工具类的代码
	@Override
    public void onCreate() {
        super.onCreate();

		//获取QGExceptionHandler实例，并为其安装到项目环境中
        QGExceptionHandler handler = QGExceptionHandler.getInstance();
        handler.install(this);

		//如果有需要，可以使用防崩溃方法或设置toast提示词
		//如果使用handler.avoidCrash(true)；则出现异常时不会有toast
        handler.avoidCrash();
        handler.setExceptionToast("功能暂时不可用");

		//如果有需要，可以为你的项目安装FPS工具类和内存工具类
        FPSCounter.initFPSCounter();
        MemoryInfoProvider.initMemoryInfoProvider(this);

		//如果有需要，可以初始化上报工具类
		//它会自动上报到指定的baseUrl中
        UploadPresenter.initUploadPresenter(this, "http://10086/", 114);
    }
}

在你的activity或fragment中使用工具类的方法
public class MainActivity extend AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		/*...*/
		TextView FPSView = findViewById(R.id.FPS_view);
		TextView MemoryView = findViewById(R.id.memory_view);
		
		int FPS = FPSCounter.getFPS();
		int memory = MemoryInfoProvider.getTotalMemory()
		
		FPSView.setText(String.valueOf(FPS);
		MemoryView.setText(String.valueOf(memory);
	}
}
```