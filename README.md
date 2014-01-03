PullScrollView
===========

*注：本项目使用Android Studio开发*


## **本例包含ScrollView的两种实现：** ##
>1.仿照新浪微博Android客户端个人中心的ScrollView，下拉背景伸缩回弹效果。<br>
>2.ScrollView仿IOS回弹效果。<br>

**本项目采用 GPL 授权协议，欢迎大家在这个基础上进行改进，并与大家分享。**

## **使用示例** ##

```java
mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
mHeadImg = (ImageView) findViewById(R.id.background_img);
mScrollView.setOnTurnListener(this);
mScrollView.init(mHeadImg);
```

##Screenshots
![Screenshot 0](https://raw.github.com/MarkMjw/PullScrollView/master/Screenshots/0.png)

![Screenshot 1](https://raw.github.com/MarkMjw/PullScrollView/master/Screenshots/1.png)

![Screenshot 2](https://raw.github.com/MarkMjw/PullScrollView/master/Screenshots/2.png)
